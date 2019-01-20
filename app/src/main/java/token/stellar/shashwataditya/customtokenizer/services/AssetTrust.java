package token.stellar.shashwataditya.customtokenizer.services;

import android.os.AsyncTask;
import android.util.Log;

import org.stellar.sdk.Asset;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Memo;

import token.stellar.shashwataditya.customtokenizer.helper.Constants;

public class AssetTrust {

    String limit;
    String assetInfo;

    public AssetTrust(String assetInfo) {
        this(assetInfo, Constants.MAX_ASSET_STRING_VALUE);
    }

    public AssetTrust(String assetInfo, String limit) {
        this.assetInfo = assetInfo;
        this.limit = limit;
    }

    public boolean createAsset(KeyPair issuingKeys, KeyPair receivingKeys) {

        Horizon horizon = new Horizon();
        boolean successFlag = false;
        Asset customToken = Asset.createNonNativeAsset(this.assetInfo, issuingKeys);
        // First, the receiving account must trust the asset
        //Task creation in progress
        if (horizon.getChangeTrust(false, receivingKeys, customToken, this.limit)) {
            Log.i(Constants.TAG, "Changed trust successfully !");
            successFlag = true;
        } else {
            Log.i(Constants.TAG, "Trust not Changed!");
        }


        //TODO: Add MemoText (for future ref)
        //Send Issued tokens from Issuer Account to Distribution Account
        Memo memo = Memo.text("--");
        if (horizon.getSendToken(customToken, this.limit, memo, issuingKeys, receivingKeys)) {
            Log.i(Constants.TAG, "Token Sent Successfully for Distribution !");
        } else {
            Log.i(Constants.TAG, "Token could not be sent !");
            successFlag = false;
        }

        return successFlag;

    }
}
