package token.stellar.shashwataditya.customtokenizer;

import android.app.Application;

public class StellarApplication extends Application {

    private int profileNumber;

    public int getProfileNumber(){
        return profileNumber;
    }

    public void setProfileNumber(int profileNumber){
        this.profileNumber = profileNumber;
    }
}
