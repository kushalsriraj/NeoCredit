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
    String IS_USER_EXISTS = "isuserExits";
    String OTP_NUMBER = "otp";
    String SEGMENT = "segment";
    String REQUIRED_AMOUNT = "required_amount";
    String COMPANY_ID = "company_id";
    String CHECK_PAGE = "check_page";

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

    public void setIsUserExists(String IsUserExists)
    {
        sharedPreferences.edit().putString(IS_USER_EXISTS,IsUserExists).apply();
    }

    public String getIsUserExists()
    {
        return sharedPreferences.getString(IS_USER_EXISTS,"");
    }

    public void setOtp(String OTP)
    {
        sharedPreferences.edit().putString(OTP_NUMBER, OTP).apply();
    }

    public String getOtp()
    {
        return sharedPreferences.getString(OTP_NUMBER,"");
    }

    public void setSegment(String segment)
    {
        sharedPreferences.edit().putString(SEGMENT, segment).apply();
    }

    public String getSegment()
    {
        return sharedPreferences.getString(SEGMENT,"");
    }

    public void setREQUIRED_AMOUNT(String required_amount)
    {
        sharedPreferences.edit().putString(REQUIRED_AMOUNT, required_amount).apply();
    }

    public String getREQUIRED_AMOUNT()
    {
        return sharedPreferences.getString(REQUIRED_AMOUNT,"");
    }

    public void setCOMPANY_ID(String company_id)
    {
        sharedPreferences.edit().putString(COMPANY_ID, company_id).apply();
    }

    public String getCOMPANY_ID()
    {
        return sharedPreferences.getString(COMPANY_ID,"");
    }

    public void setCHECK_PAGE(String check_page)
    {
        sharedPreferences.edit().putString(CHECK_PAGE, check_page).apply();
    }

    public String getCHECK_PAGE()
    {
        return sharedPreferences.getString(CHECK_PAGE,"");
    }

}
