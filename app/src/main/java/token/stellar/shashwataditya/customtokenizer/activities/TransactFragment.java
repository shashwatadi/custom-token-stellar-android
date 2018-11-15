package token.stellar.shashwataditya.customtokenizer.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import token.stellar.shashwataditya.customtokenizer.services.Horizon;

public class TransactFragment extends Fragment implements View.OnClickListener {

    Button btnTransact;
    View rootView;
    Horizon horizon;
    Asset customToken;
    String amount;
    Memo memo;
    KeyPair sender;
    KeyPair receiver;
    EditText tvAmount;
    EditText tvAddress;

    @Override
    public void onClick(View v) {
        horizon = new Horizon();
        amount = tvAmount.getText().toString();
        //ToDo(A): validate input fields and create token
        //ToDo(B): map to public address here
        //ToDo(C): Create KeyPairs and call getSendToken
      //  sender = KeyPair.fromSecretSeed();
      //  receiver = KeyPair.fromAccountId();
        //horizon.getSendToken(customToken, amount, memo, sender, receiver);

        Toast.makeText(getActivity().getApplicationContext(), "Entered Transact Mode", Toast.LENGTH_LONG).show();
    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            rootView =  inflater.inflate(R.layout.transact_fragment, container, false);
            btnTransact = (Button) rootView.findViewById(R.id.btn_transact);
            tvAddress = (EditText) rootView.findViewById(R.id.tv_address);
            tvAmount = (EditText) rootView.findViewById(R.id.tv_amount);

            btnTransact.setOnClickListener(this);
            return rootView;
        }
}
