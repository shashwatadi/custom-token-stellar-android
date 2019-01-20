package token.stellar.shashwataditya.customtokenizer.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.stellar.sdk.Asset;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Memo;

import token.stellar.shashwataditya.customtokenizer.R;
import token.stellar.shashwataditya.customtokenizer.StellarApplication;
import token.stellar.shashwataditya.customtokenizer.helper.Constants;
import token.stellar.shashwataditya.customtokenizer.services.BalanceAsyncTask;
import token.stellar.shashwataditya.customtokenizer.services.Horizon;

/*
Issuing Account
Public Key	GBSFZZPW6T2EJI5D7WLCA4ZOB3AH6GVIBJ6RN64GH5ZIXHPNXHRSTTSF
Secret Key	SCXRXBWLVH4M3BT3D6ECTOXLNKQSWUKM25FQFEGBI7A3JZAROQAQ3OJ5

Dist Account
Public Key	GBUTGOJTLHZSMIG3FZ37OMULUCGNNDMH5MI5A5JCGV4QOAUHEU7GVYKM
Secret Key	SA2TWV3SAHEQRY3BVVGWFSHSWQD5XOVGKCGHNHNNCWVOEXQHJG3G4BID
 */

public class CreateFragment extends Fragment implements View.OnClickListener {

    View rootView;
    Button btnCreate;
    Horizon horizon;
    Asset customToken;
    String amount;
    Memo memo;
    KeyPair sender;
    KeyPair receiver;
    EditText etAssetInfo;
    EditText etCount;
    String assetInfo;
    String count;
    int profileNumber;
    KeyPair current;
    Asset asset;

    @Override
    public void onClick(View v) {
        horizon = new Horizon();

        KeyPair issuing = KeyPair.fromSecretSeed(Constants.StellarIssuingAccountKeys[0]);
        KeyPair distributing = KeyPair.fromSecretSeed(Constants.StellarDistAccountKeys[0]);

        this.count = etCount.getText().toString();
        this.assetInfo = etAssetInfo.getText().toString();

        //TODO: Validate all the entries, store description
        horizon.createDistributionAccount(issuing, distributing, this.assetInfo, this.count);
        asset = horizon.getAsset(this.assetInfo, issuing);
        horizon.getChangeTrust(false, current, asset, this.count);
        horizon.getSendToken(asset, this.count, Memo.text("SendTest"), distributing, current);
        //TODO: Manage KeyPairs for issuing account and distribution account: Map them to user's info
        //TODO: Create Asset and send to distribution account
        //TODO: Prevent further token creation based on choice selected
        Toast.makeText(getActivity().getApplicationContext(), this.assetInfo + " Token Created", Toast.LENGTH_SHORT);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.create_fragment, container, false);
        etCount = (EditText) rootView.findViewById(R.id.et_count);
        etAssetInfo = (EditText) rootView.findViewById(R.id.et_asset_code);

        btnCreate = (Button) rootView.findViewById(R.id.btn_create);

        profileNumber = ((StellarApplication) this.getActivity().getApplication()).getProfileNumber();
        current = KeyPair.fromSecretSeed(Constants.StellarPrivateKeys[profileNumber]);
        btnCreate.setOnClickListener(this);
        return rootView;
    }

}