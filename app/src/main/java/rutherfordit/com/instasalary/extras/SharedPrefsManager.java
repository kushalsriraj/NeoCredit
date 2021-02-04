package rutherfordit.com.instasalary.extras;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsManager {

    SharedPreferences sharedPreferences;
    Context context;

    String PREFS_NAME = "Neocredit";
    String KEY_IS_LOGGED_IN ="is_logged_in";
    String ACCESS_TOKEN ="access_token";
    String PHONE_NUMBER ="phone_number";

    public SharedPrefsManager(Context context)
    {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
    }

    @SuppressLint("CommitPrefEdits")
    public void deletePref()
    {
        sharedPreferences.edit().clear();
        sharedPreferences.edit().apply();
    }
    public boolean isLoggedIn()
    {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN,false);
    }

    public void setLoggedIn(boolean value)
    {
        sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN,value).apply();
    }

    public void setPhoneNumber(String phoneNumber)
    {
        sharedPreferences.edit().putString(PHONE_NUMBER,phoneNumber).apply();
    }

    public String getPhoneNumber()
    {
        return sharedPreferences.getString(PHONE_NUMBER,"");
    }

    public void setAccessToken(String accessToken)
    {
        sharedPreferences.edit().putString(ACCESS_TOKEN,accessToken).apply();
    }

    public String getAccessToken()
    {
        return sharedPreferences.getString(ACCESS_TOKEN,"");
    }

}
