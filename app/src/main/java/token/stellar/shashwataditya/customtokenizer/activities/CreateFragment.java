package token.stellar.shashwataditya.customtokenizer.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.stellar.sdk.Asset;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Memo;

import token.stellar.shashwataditya.customtokenizer.R;
import token.stellar.shashwataditya.customtokenizer.services.Horizon;

public class CreateFragment extends Fragment implements View.OnClickListener {

    View rootView;
    Button btnCreate;
    Horizon horizon;
    Asset customToken;
    String amount;
    Memo memo;
    KeyPair sender;
    KeyPair receiver;

    @Override
    public void onClick(View v) {
        horizon = new Horizon();
        //TODO: Create KeyPairs for issuing account and distribution account: Map them to user's info
        //TODO: Create Asset and send to distribution account
        //TODO: Prevent further token creation based on choice selected


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.create_fragment, container, false);
        btnCreate = (Button) rootView.findViewById(R.id.btn_create);
        return rootView;
    }
}