package token.stellar.shashwataditya.customtokenizer;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class StellarApplication extends Application {

    private int profileNumber;
    private final String PREF_NAME = "token.stellar.shashwataditya.customtokenizer.PREFERENCE";
    public int getProfileNumber(){
        return profileNumber;
    }

    public void setProfileNumber(int profileNumber){
        this.profileNumber = profileNumber;
    }

    public SharedPreferences storeData(){
        Context context = getApplicationContext();
        SharedPreferences sharedpreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedpreferences;
    }

}
