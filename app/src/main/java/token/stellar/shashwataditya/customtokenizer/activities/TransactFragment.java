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
/*
Send To:
Public Key	GCUX62YGJ4BKGWGTKZLJ7GD4BFA3EAPIVMTYIOS5V2ODRMUATI63WSPL
Secret Key	SB2PONE5GEDJJV6FNWMU53PTPLALXKBY5O77QDTBV2CTLGWVR7NL7O32

From: Dist Account
Public Key	GBUTGOJTLHZSMIG3FZ37OMULUCGNNDMH5MI5A5JCGV4QOAUHEU7GVYKM
Secret Key	SA2TWV3SAHEQRY3BVVGWFSHSWQD5XOVGKCGHNHNNCWVOEXQHJG3G4BID

Issuing Account
Public Key	GBSFZZPW6T2EJI5D7WLCA4ZOB3AH6GVIBJ6RN64GH5ZIXHPNXHRSTTSF
Secret Key	SCXRXBWLVH4M3BT3D6ECTOXLNKQSWUKM25FQFEGBI7A3JZAROQAQ3OJ5
 */

public class TransactFragment extends Fragment implements View.OnClickListener {

    Button btnTransact;
    View rootView;
    Horizon horizon;
    Asset customToken;
    String amount;
    Memo memo;
    KeyPair sender;
    KeyPair receiver;
    KeyPair toAcc;
    KeyPair fromAcc;
    EditText etAmount;
    EditText etAddress;
    TextView tvBalance;
    Asset asset;
    @Override
    public void onClick(View v) {

        amount = etAmount.getText().toString();
        //ToDo(A): validate input fields and create token
        //ToDo(B): map to public address here
        //ToDo(C): Create KeyPairs and call getSendToken
        //TODO(D): Check whether trustline exists, and token info
      //  sender = KeyPair.fromSecretSeed();
      //  receiver = KeyPair.fromAccountId();
        //horizon.getSendToken(customToken, amount, memo, sender, receiver);
        etAddress.setText("SB2PONE5GEDJJV6FNWMU53PTPLALXKBY5O77QDTBV2CTLGWVR7NL7O32");
        asset = horizon.getAsset("abcd");
        horizon.getChangeTrust(false, toAcc, asset, "100");
        horizon.getSendToken(asset, amount, Memo.text("SendTest"), fromAcc, toAcc);
        updateBalance();
        Toast.makeText(getActivity().getApplicationContext(), "Transaction Complete", Toast.LENGTH_LONG).show();
    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            rootView =  inflater.inflate(R.layout.transact_fragment, container, false);
            btnTransact = (Button) rootView.findViewById(R.id.btn_transact);
            etAddress = (EditText) rootView.findViewById(R.id.et_address);
            etAmount = (EditText) rootView.findViewById(R.id.et_amount);
            tvBalance = (TextView) rootView.findViewById(R.id.tv_balance);
            horizon = new Horizon();
            toAcc = KeyPair.fromSecretSeed("SB2PONE5GEDJJV6FNWMU53PTPLALXKBY5O77QDTBV2CTLGWVR7NL7O32");
            fromAcc = KeyPair.fromSecretSeed("SA2TWV3SAHEQRY3BVVGWFSHSWQD5XOVGKCGHNHNNCWVOEXQHJG3G4BID");

            updateBalance();
            btnTransact.setOnClickListener(this);
            return rootView;
        }

        void updateBalance(){
            String strBalance = horizon.updateAccountBalance(fromAcc);
            tvBalance.setText(strBalance);
        }


}
