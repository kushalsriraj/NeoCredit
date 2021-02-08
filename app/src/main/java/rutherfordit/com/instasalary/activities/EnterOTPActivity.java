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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chaos.view.PinView;

import org.json.JSONException;
import org.json.JSONObject;

import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.extras.Constants;
import rutherfordit.com.instasalary.extras.ResponseHandler;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.Urls;
import rutherfordit.com.instasalary.extras.VolleyRequest;

public class EnterOTPActivity extends AppCompatActivity implements ResponseHandler {

    RelativeLayout submitadharotp;
    ImageView purplebackarrow;
    SharedPrefsManager sharedPrefsManager;
    VolleyRequest volleyRequest;
    CardView loader_otp;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_o_t_p);

        volleyRequest = new VolleyRequest();
        sharedPrefsManager = new SharedPrefsManager(getApplicationContext());
        submitadharotp = findViewById(R.id.submitadharotp);
        purplebackarrow = findViewById(R.id.purplebackarrow);
        loader_otp = findViewById(R.id.loader_otp);
        loader_otp.setVisibility(View.GONE);
        final PinView pinView = findViewById(R.id.pinView);
        pinView.setLineColor(ResourcesCompat.getColorStateList(getResources(), R.color.neopurple, getTheme()));
        pinView.setItemCount(4);
        pinView.setAnimationEnable(true);
        pinView.setCursorVisible(true);
        pinView.setCursorWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_cursor_width));
        pinView.setCursorColor(Color.BLACK);

        submitadharotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("onClick: ", "onClick: " + sharedPrefsManager.getOtp()+ "onClick: "+ pinView.getText().toString() );

                if (pinView.getText().toString().length()!=4)
                {
                    ObjectAnimator.ofFloat(pinView, "translationX", 0, 25, -25, 25, -25,15, -15, 6, -6, 0)
                            .setDuration(1000)
                            .start();
                }
                else
                {
                    if(pinView.getText().toString().equals(sharedPrefsManager.getOtp()))
                    {
                        
                        if (sharedPrefsManager.getIsUserExists().equalsIgnoreCase("true"))
                        {
                            loader_otp.setVisibility(View.VISIBLE);
                            generateToken();
                        }
                        else
                        {
                            loader_otp.setVisibility(View.VISIBLE);
                            signUp();
                        }
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "OTP Doesn't match", Toast.LENGTH_LONG).show();
                        ObjectAnimator.ofFloat(pinView, "translationX", 0, 25, -25, 25, -25,15, -15, 6, -6, 0)
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

    private void signUp()
    {
        JSONObject jsonObject1 = new JSONObject();

        try {
            jsonObject1.put("phone_number", sharedPrefsManager.getPhoneNumber());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        volleyRequest.JsonObjRequest(EnterOTPActivity.this,jsonObject1,Urls.SIGN_UP, Constants.sign_up);

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

        volleyRequest.JsonObjRequest(EnterOTPActivity.this,jsonObject1,Urls.GENERATE_TOKEN,Constants.token);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void responseHandler(Object obj, int i) {

        JSONObject response = (JSONObject) obj;

        if  (i== Constants.sign_up)
        {
            try
            {
                JSONObject dataJsonObject = response.getJSONObject("data");
                JSONObject tokenJsonObject = dataJsonObject.getJSONObject("token");
                String access_token_signUp = tokenJsonObject.getString("access_token");
                sharedPrefsManager.setAccessToken("Bearer " +access_token_signUp);
                loader_otp.setVisibility(View.GONE);
                Intent intent = new Intent(getApplicationContext(), SegmentActivity.class);
                startActivity(intent);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
        else if ( i == Constants.token)
        {
            try
            {
                String access_token = response.getString("access_token");
                sharedPrefsManager.setAccessToken("Bearer " + access_token);
                loader_otp.setVisibility(View.GONE);
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }

    }
}