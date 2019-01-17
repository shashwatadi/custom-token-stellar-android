package token.stellar.shashwataditya.customtokenizer.services;

import android.os.AsyncTask;
import android.util.Log;

import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Network;
import org.stellar.sdk.Server;
import org.stellar.sdk.responses.AccountResponse;

import token.stellar.shashwataditya.customtokenizer.helper.Constants;

public class BalanceAsyncTask extends AsyncTask<KeyPair, Void, String> {

    KeyPair accountPair;
    @Override
    protected String doInBackground(KeyPair... keyPairs) {

        accountPair = keyPairs[0];
        Network.useTestNetwork();
        Server server = Constants.getServer();
        String strBalance = "";
        AccountResponse account = null;
        try {
            account = server.accounts().account(accountPair);
                Log.i(Constants.TAG, "Balances for account " + accountPair.getAccountId());
                //ToDo: Add update Logic here
                for (AccountResponse.Balance balance : account.getBalances()) {
                    if(balance.getAssetType().equalsIgnoreCase("native")){
                        strBalance += balance.getBalance() + " XLM\n";
                    }
                    else {
                        strBalance += balance.getBalance() + " " + balance.getAssetCode().toUpperCase() +"\n";
                    }

                }
                Log.i(Constants.TAG, strBalance);
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
            }
            return strBalance;
    }

    @Override
    protected void onPostExecute(String s) {
        //TODO: Update something on UI thread, or some value somewhere :P
        super.onPostExecute(s);
    }

    @Override
    protected void onPreExecute() {
        //TODO: Add loader
        //ProgressDialog progress = new ProgressDialog(this);
        super.onPreExecute();
    }
}