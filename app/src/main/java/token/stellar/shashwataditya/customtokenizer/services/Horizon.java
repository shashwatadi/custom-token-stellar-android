package token.stellar.shashwataditya.customtokenizer.services;

import android.os.AsyncTask;
import android.util.Log;

import org.stellar.sdk.ChangeTrustOperation;
import org.stellar.sdk.CreateAccountOperation;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Memo;
import org.stellar.sdk.Network;
import org.stellar.sdk.PaymentOperation;
import org.stellar.sdk.Server;
import org.stellar.sdk.Asset;
import org.stellar.sdk.Transaction;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.SubmitTransactionResponse;

import javax.validation.constraints.NotNull;

//All the functions in the class are to be used on different threads

public class Horizon{
    private static final String MAX_ASSET_STRING_VALUE = "922337203685.4775807";
    private static final String PROD_SERVER_STRING = "https://horizon.stellar.org";
    private static final String TEST_SERVER_STRING = "https://horizon-testnet.stellar.org";
    public static final String TAG = "Stellar";



//    @Override
    public boolean createUserAccount() {
        return false;
    }

//    @Override
    public boolean testFundUserAccount(String toAccount) {
        return false;
    }

    public String updateAccountBalance(KeyPair pair) {

//        System.out.println("Seed Length: ");
        //KeyPair pair = KeyPair.fromSecretSeed();
        try {
            return new AccountBalance(pair).execute().get();
        }catch(Exception e){
            Log.i(TAG, "Exception thrown: " + e.getMessage());

        }
        return null;
        //return false;
    }

    public void getChangeTrust(boolean removeTrust, KeyPair receivingKeys, Asset customToken, String limit) {

            new ChangeTrust(removeTrust, receivingKeys, customToken, limit).execute();
    }

    public void getSendToken(Asset customToken, String amount, Memo memo, KeyPair senderKeys, KeyPair receiverKeys) {

        new SendToken(customToken, amount, memo, senderKeys, receiverKeys).execute();

    }

    public Asset getAsset(String assetInfo) {

        KeyPair issuing = KeyPair.fromSecretSeed("SCXRXBWLVH4M3BT3D6ECTOXLNKQSWUKM25FQFEGBI7A3JZAROQAQ3OJ5");
        return Asset.createNonNativeAsset(assetInfo, issuing);

    }
//    @Override
    /*public boolean sendCustomToken(Asset customToken, String amount, Memo memo, KeyPair senderKeys, KeyPair receiverKeys) {

        Network.useTestNetwork();
        Server server = getServer();

        //
        try {
            AccountResponse issuing = server.accounts().account(senderKeys);

            Transaction sendCustomToken = new Transaction.Builder(issuing)
                    .addOperation(
                            new PaymentOperation.Builder(receiverKeys, customToken, amount).build())
                                .addMemo(memo)
                                    .build();
            sendCustomToken.sign(senderKeys);

            SubmitTransactionResponse response = server.submitTransaction(sendCustomToken);
            if(response.isSuccess()){
                Log.i(TAG, "Successfully sent: "+amount+" "+ customToken.toString());
                return true;
            }
            else{
                Log.i(TAG, "Could not send "+ customToken.toString() +". Try Again!");
                return false;
            }
        }
        catch(Exception e) {
            Log.i(TAG, e.getLocalizedMessage());
        }
        return false;
    }*/

//    @Override
   /* public boolean changeTrust(boolean removeTrust, KeyPair receivingKeys, Asset customToken, String limit) {
        //If the trust is removed, in case of account deletion
        String updatedLimit = (removeTrust)?"0.0000000":limit;
        Network.useTestNetwork();
        Server server = getServer();
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
            if(response.isSuccess()){
                return true;
            }
            else{
                return false;
            }
        }
        catch(Exception e){
            Log.i(TAG, e.getLocalizedMessage());
        }
        return false;
    }*/

//    @Override
    public boolean createDistributionAccount(KeyPair issuingAccount,KeyPair receivingAccount,String assetInfo,String amount) {

        new createToken(issuingAccount, receivingAccount, assetInfo, amount).execute();
        this.updateAccountBalance(receivingAccount);
        return false;
    }

//    @Override
    /*public boolean createAsset(String issuingAccountSeed, String receivingAccountSeed, String assetInfo, String amount) {
        String limit = MAX_ASSET_STRING_VALUE;
        boolean successFlag = false;
        KeyPair issuingKeys = KeyPair.fromSecretSeed(issuingAccountSeed);
        KeyPair receivingKeys = KeyPair.fromSecretSeed(receivingAccountSeed);
//AssetInfo is same as AssetCode
        Asset customToken = Asset.createNonNativeAsset(assetInfo, issuingKeys);

    // First, the receiving account must trust the asset
       if(changeTrust(false, receivingKeys, customToken, limit)){
           Log.i(TAG, "Changed trust successfully !");
           successFlag = true;
       }
       else{
           Log.i(TAG, "Trust not Changed!");
       }

    // Second, the issuing account actually sends a payment using the asset
 *//*       AccountResponse issuing = server.accounts().account(issuingKeys);
        Transaction sendAstroDollars = new Transaction.Builder(issuing)
                .addOperation(
                        new PaymentOperation.Builder(receivingKeys, astroDollar, "10").build())
                .build();
        sendAstroDollars.sign(issuingKeys);
        server.submitTransaction(sendAstroDollars);*//*

 //TODO: Add MemoText (for future ref)
        Memo memo = Memo.text("--");
        if(sendCustomToken(customToken, amount, memo, issuingKeys, receivingKeys)){
            Log.i(TAG, "Token Sent Successfully for Distribution !");
        }
        else{
            Log.i(TAG, "Token could not be sent !");
            successFlag = false;
        }

        return successFlag;

    }
*/
    private static Server getServer() {
        return new Server(TEST_SERVER_STRING);
    }

    private class AccountBalance extends AsyncTask<String, String, String>{

        private String ofAccountSeed;
        private KeyPair pair;
        public String strBalance;

        public AccountBalance(KeyPair pair) {
            this.pair = pair;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {
            Network.useTestNetwork();
            Server server = getServer();
            strBalance = "";
            AccountResponse account = null;
            try {
                account = server.accounts().account(pair);
                Log.i(TAG, "Balances for account " + pair.getAccountId());
                //ToDo: Add update Logic here
                for (AccountResponse.Balance balance : account.getBalances()) {
                    strBalance += String.format(
                            "Type: %s, Code: %s, Balance: %s\n",
                            balance.getAssetType(),
                            balance.getAssetCode(),
                            balance.getBalance());

                }
                Log.i(TAG, strBalance);
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
            }
            return strBalance;
        }
    }

    private class createToken extends AsyncTask<String, String, String> {

        KeyPair issuingAccount, receivingAccount;
        String assetInfo;
        String amount;

        createToken(KeyPair issuingAccount, KeyPair receivingAccount, String assetInfo, String amount) {
            this.issuingAccount = issuingAccount;
            this.receivingAccount = receivingAccount;
            this.assetInfo = assetInfo;
            this.amount = amount;
        }


        @Override
        protected String doInBackground(String... strings) {

            createAsset(issuingAccount, receivingAccount, assetInfo, amount);
            return null;
        }

        public boolean createAsset(KeyPair issuingKeys, KeyPair receivingKeys, String assetInfo, String amount) {
            String limit = MAX_ASSET_STRING_VALUE;
            boolean successFlag = false;
//            KeyPair issuingKeys = KeyPair.fromSecretSeed(issuingAccountSeed);
//            KeyPair receivingKeys = KeyPair.fromSecretSeed(receivingAccountSeed);
////AssetInfo is same as AssetCode
            Asset customToken = Asset.createNonNativeAsset(assetInfo, issuingKeys);

            // First, the receiving account must trust the asset
            if (changeTrust(false, receivingKeys, customToken, limit)) {
                Log.i(TAG, "Changed trust successfully !");
                successFlag = true;
            } else {
                Log.i(TAG, "Trust not Changed!");
            }

            // Second, the issuing account actually sends a payment using the asset
 /*       AccountResponse issuing = server.accounts().account(issuingKeys);
        Transaction sendAstroDollars = new Transaction.Builder(issuing)
                .addOperation(
                        new PaymentOperation.Builder(receivingKeys, astroDollar, "10").build())
                .build();
        sendAstroDollars.sign(issuingKeys);
        server.submitTransaction(sendAstroDollars);*/

            //TODO: Add MemoText (for future ref)
            Memo memo = Memo.text("--");
            if (sendCustomToken(customToken, amount, memo, issuingKeys, receivingKeys)) {
                Log.i(TAG, "Token Sent Successfully for Distribution !");
            } else {
                Log.i(TAG, "Token could not be sent !");
                successFlag = false;
            }

            return successFlag;

        }

        public boolean changeTrust(boolean removeTrust, KeyPair receivingKeys, Asset customToken, String limit) {

            new ChangeTrust(removeTrust, receivingKeys, customToken, limit).execute();
            //If the trust is removed, in case of account deletion
            /*String updatedLimit = (removeTrust) ? "0.0000000" : limit;
            Network.useTestNetwork();
            Server server = getServer();
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
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                Log.i(TAG, e.getLocalizedMessage());
            }*/
            return false;
        }

        public boolean sendCustomToken(Asset customToken, String amount, Memo memo, KeyPair senderKeys, KeyPair receiverKeys) {

            new SendToken(customToken, amount, memo, senderKeys, receiverKeys).execute();
            return true;
            /*Network.useTestNetwork();
            Server server = getServer();

            //
            try {
                AccountResponse issuing = server.accounts().account(senderKeys);

                Transaction sendCustomToken = new Transaction.Builder(issuing)
                        .addOperation(
                                new PaymentOperation.Builder(receiverKeys, customToken, amount).build())
                        .addMemo(memo)
                        .build();
                sendCustomToken.sign(senderKeys);

                SubmitTransactionResponse response = server.submitTransaction(sendCustomToken);
                if (response.isSuccess()) {
                    Log.i(TAG, "Successfully sent: " + amount + " " + customToken.toString());
                    return true;
                } else {
                    Log.i(TAG, "Could not send " + customToken.toString() + ". Try Again!");
                    return false;
                }
            } catch (Exception e) {
                Log.i(TAG, e.getLocalizedMessage());
            }
            return false;*/
        }
    }

        private class SendToken extends AsyncTask<String, String, String>{

        private Asset customToken;
        private String amount;
        Memo memo;
        private KeyPair senderKeys, receiverKeys;

            public SendToken(Asset customToken, String amount, Memo memo, KeyPair senderKeys, KeyPair receiverKeys) {
                this.customToken = customToken;
                this.amount = amount;
                this.memo = memo;
                this.senderKeys = senderKeys;
                this.receiverKeys = receiverKeys;
            }

            @Override
            protected String doInBackground(String... strings) {

                Network.useTestNetwork();
                Server server = getServer();

                //
                try {
                    AccountResponse issuing = server.accounts().account(senderKeys);

                    Transaction sendCustomToken = new Transaction.Builder(issuing)
                            .addOperation(
                                    new PaymentOperation.Builder(receiverKeys, customToken, amount).build())
                            .addMemo(memo)
                            .build();
                    sendCustomToken.sign(senderKeys);

                    SubmitTransactionResponse response = server.submitTransaction(sendCustomToken);
                    if (response.isSuccess()) {
                        Log.i(TAG, "Successfully sent: " + amount + " " + customToken.toString());
                        return null;
                    } else {
                        Log.i(TAG, "Could not send " + customToken.toString() + ". Try Again!");
                        return null;
                    }
                } catch (Exception e) {
                    Log.i(TAG, e.getLocalizedMessage());
                }
                return null;

            }
        }


        private class ChangeTrust extends AsyncTask<String, String, String>{
            private boolean removeTrust;
            private KeyPair receivingKeys;
            private Asset customToken;
            private String limit;


            public ChangeTrust(boolean removeTrust, KeyPair receivingKeys, Asset customToken, String limit) {
                this.removeTrust = removeTrust;
                this.receivingKeys = receivingKeys;
                this.customToken = customToken;
                this.limit = limit;
            }

            @Override
            protected String doInBackground(String... strings) {

                String updatedLimit = (removeTrust) ? "0.0000000" : limit;
                Network.useTestNetwork();
                Server server = getServer();
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
                        return null;
                    } else {
                        return null;
                    }
                } catch (Exception e) {
                    Log.i(TAG, e.getLocalizedMessage());
                }

                return null;
            }
        }

}


