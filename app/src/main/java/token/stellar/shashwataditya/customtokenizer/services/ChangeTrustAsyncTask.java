package token.stellar.shashwataditya.customtokenizer.services;

import android.os.AsyncTask;
import android.util.Log;

import org.stellar.sdk.Asset;
import org.stellar.sdk.ChangeTrustOperation;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Network;
import org.stellar.sdk.Server;
import org.stellar.sdk.Transaction;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.SubmitTransactionResponse;

import token.stellar.shashwataditya.customtokenizer.helper.Constants;

public class ChangeTrustAsyncTask extends AsyncTask<Void, Void, Void> {
    private boolean removeTrust;
    private KeyPair receivingKeys;


    private Asset customToken;
    private String limit;


    public ChangeTrustAsyncTask(boolean removeTrust, KeyPair receivingKeys, Asset customToken, String limit) {
        this.removeTrust = removeTrust;
        this.receivingKeys = receivingKeys;
        this.customToken = customToken;
        this.limit = limit;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        String updatedLimit = (removeTrust) ? "0.0000000" : limit;
        Network.useTestNetwork();
        Server server = Constants.getServer();
        try {
            AccountResponse receiving = server.accounts().account(receivingKeys);

            Transaction allowCustomToken = new Transaction.Builder(receiving)
                    .addOperation(
                            // The `ChangeTrust` operation creates (or alters) a trustline
                            // The second parameter limits the amount the account can hold
                            new ChangeTrustOperation.Builder(customToken, updatedLimit).build())
                    .build();
            allowCustomToken.sign(receivingKeys);
            SubmitTransactionResponse response = server.submitTransaction(allowCustomToken);
            if (response.isSuccess()) {
               //TOdo: success
            } else {
                //todo: failure
            }
        } catch (Exception e) {
            Log.i(Constants.TAG, e.getLocalizedMessage());
        }
        return null;
    }
}
