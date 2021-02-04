package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.chaos.view.PinView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.extras.MySingleton;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.Urls;

public class EnterOTPActivity extends AppCompatActivity {

    RelativeLayout submitadharotp;
    ImageView purplebackarrow;
    SharedPrefsManager sharedPrefsManager;
    //CardView loader_login;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_o_t_p);

        sharedPrefsManager = new SharedPrefsManager(getApplicationContext());
        submitadharotp = findViewById(R.id.submitadharotp);
        purplebackarrow = findViewById(R.id.purplebackarrow);

        final PinView pinView = findViewById(R.id.pinView);
        //  pinView.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, getTheme()));
        //  pinView.setTextColor(ResourcesCompat.getColorStateList(getResources(), R.color.black, getTheme()));
        //  pinView.setLineColor(ResourcesCompat.getColor(getResources(), R.color.black, getTheme()));
        pinView.setLineColor(ResourcesCompat.getColorStateList(getResources(), R.color.neopurple, getTheme()));
        pinView.setItemCount(4);
        pinView.setAnimationEnable(true);// start animation when adding text
        pinView.setCursorVisible(true);
        //   pinView.setCursorColor(ResourcesCompat.getColor(getResources(), R.color.black, getTheme()));
        pinView.setCursorWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_cursor_width));
        pinView.setCursorColor(Color.BLACK);

        submitadharotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("onClick: ", "onClick: " + sharedPrefsManager.getOtp()+ "onClick: "+ pinView.getText().toString() );

                if (pinView.getText().toString().length()!=4)
                {

                    ObjectAnimator
                            .ofFloat(pinView, "translationX", 0, 25, -25, 25, -25,15, -15, 6, -6, 0)
                            .setDuration(1000)
                            .start();

                }
                else {
                    if(pinView.getText().toString().equals(sharedPrefsManager.getOtp()))
                    {
                        
                        if (sharedPrefsManager.getIsUserExists().equalsIgnoreCase("true"))
                        {
                            generateToken();
                        }
                        else {

                            signUp();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "OTP Doesn't match", Toast.LENGTH_LONG).show();
                        ObjectAnimator
                                .ofFloat(pinView, "translationX", 0, 25, -25, 25, -25,15, -15, 6, -6, 0)
                                .setDuration(1000)
                                .start();
                    }
                }
            }
        });

        purplebackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
    }

    private void signUp() {
        JSONObject jsonObject1 = new JSONObject();

        try {
            jsonObject1.put("phone_number", sharedPrefsManager.getPhoneNumber());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.SIGN_UP, jsonObject1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONObject dataJsonObject = response.getJSONObject("data");

                    JSONObject tokenJsonObject = dataJsonObject.getJSONObject("token");

                    String access_token_signUp = tokenJsonObject.getString("access_token");

                    Log.e("access_token_signUp", "access_token_signUp: " + access_token_signUp );

                    sharedPrefsManager.setAccessToken(access_token_signUp);
                    Log.e("access_token_signUp", "access_token_signUp: " + sharedPrefsManager.getAccessToken());

                    Intent intent = new Intent(getApplicationContext(), SegmentActivity.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", "onErrorResponse: " + error.getLocalizedMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        MySingleton.getInstance(EnterOTPActivity.this).addToRequestQueue(jsonObjectRequest);

    }

    private void generateToken() {
        JSONObject jsonObject1 = new JSONObject();

        try {
            jsonObject1.put("grant_type", "password");
            jsonObject1.put("client_id", "2");
            jsonObject1.put("client_secret", Urls.CLIENT_SECRET_KEY);
            jsonObject1.put("username", sharedPrefsManager.getPhoneNumber());
            jsonObject1.put("password", sharedPrefsManager.getOtp());
            jsonObject1.put("scope", "*");
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.GENERATE_TOKEN, jsonObject1, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response", "response: " + response);

                try {
                    String access_token = response.getString("access_token");

                    Log.e("access_token", "access_token: " + access_token);

                    //loader_login.setVisibility(View.GONE);
                    sharedPrefsManager.setAccessToken(access_token);
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", "onErrorResponse: " + error.getLocalizedMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        MySingleton.getInstance(EnterOTPActivity.this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}