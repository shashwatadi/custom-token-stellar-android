package token.stellar.shashwataditya.customtokenizer.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.stellar.sdk.Asset;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Memo;

import token.stellar.shashwataditya.customtokenizer.R;
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

    @Override
    public void onClick(View v) {
        horizon = new Horizon();

        KeyPair issuing = KeyPair.fromSecretSeed("SCXRXBWLVH4M3BT3D6ECTOXLNKQSWUKM25FQFEGBI7A3JZAROQAQ3OJ5");
        KeyPair distributing = KeyPair.fromSecretSeed("SA2TWV3SAHEQRY3BVVGWFSHSWQD5XOVGKCGHNHNNCWVOEXQHJG3G4BID");
        this.count = etCount.getText().toString();
        this.assetInfo = etAssetInfo.getText().toString();

        //TODO: Validate all the entries, store description
        horizon.createDistributionAccount(issuing, distributing, this.assetInfo, this.count);
        //TODO: Manage KeyPairs for issuing account and distribution account: Map them to user's info
        //TODO: Create Asset and send to distribution account
        //TODO: Prevent further token creation based on choice selected


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.create_fragment, container, false);
        etCount = (EditText) rootView.findViewById(R.id.et_count);
        etAssetInfo = (EditText) rootView.findViewById(R.id.et_asset_code);
        btnCreate = (Button) rootView.findViewById(R.id.btn_create);
        btnCreate.setOnClickListener(this);


        return rootView;
    }
}