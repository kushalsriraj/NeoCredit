package rutherfordit.com.instasalary.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.activities.sp.SoleProprietorshipDetailsActivity;
import rutherfordit.com.instasalary.extras.MySingleton;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout loginbottombutton;
    EditText enterphoneno_login;
    boolean click = false;
    String otp, isUserExits;
    CardView loader_login;
    SharedPrefsManager sharedPrefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.neopurple));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.neopurple));
        }

        sharedPrefsManager = new SharedPrefsManager(getApplicationContext());
        loginbottombutton = findViewById(R.id.loginbottombutton);
        enterphoneno_login = findViewById(R.id.enterphoneno_login);
        enterphoneno_login.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(enterphoneno_login, InputMethodManager.SHOW_FORCED);
        loader_login = findViewById(R.id.loader_login);

        enterphoneno_login.addTextChangedListener(new TextWatcher() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (enterphoneno_login.getText().toString().length() == 10)
                {
                    loginbottombutton.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click=true;
                }
                else
                {
                    loginbottombutton.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loginbottombutton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (click)
                {
                    loader_login.setVisibility(View.VISIBLE);
                    sendOtp();

                }
            }
        });
    }

    private void sendOtp() {
        JSONObject numberJsonObject = new JSONObject();

        try {
            numberJsonObject.put("phone_number", enterphoneno_login.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://console.grantlending.in/api/sendotp", numberJsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response", "response: " + response );

                try {
                    isUserExits = response.getString("isuserExits");
                    otp = response.getString("otp");

                    sharedPrefsManager.setPhoneNumber(enterphoneno_login.getText().toString());
                    if (isUserExits.equalsIgnoreCase("true")){
                        sharedPrefsManager.setIsUserExists("true");
                        sharedPrefsManager.setOtp(otp);
                    }

                    else {
                        sharedPrefsManager.setIsUserExists("false");
                        sharedPrefsManager.setOtp(otp);
                    }

                    Toast.makeText(getApplicationContext(), otp, Toast.LENGTH_LONG).show();
                    loader_login.setVisibility(View.GONE);
                    Intent intent = new Intent(getApplicationContext(), EnterOTPActivity.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "onErrorResponse: " + error.getLocalizedMessage() );
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        MySingleton.getInstance(LoginActivity.this).addToRequestQueue(jsonObjectRequest);

    }

    @Override
    public void onBackPressed()
    {
        finishAffinity();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}