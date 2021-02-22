package rutherfordit.com.instasalary.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.extras.Constants;
import rutherfordit.com.instasalary.myinterfaces.ResponseHandler;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.Urls;
import rutherfordit.com.instasalary.extras.VolleyRequest;

public class SegmentActivity extends AppCompatActivity implements ResponseHandler {

    RelativeLayout SegmentsubmitButton;
    RadioGroup rg;
    AppCompatRadioButton soleProprietorship;
    AppCompatRadioButton privateLimited;
    AppCompatRadioButton partnershipForm;
    ImageView purplebackarrow;
    String segment;
    VolleyRequest volleyRequest;
    SharedPrefsManager sharedPrefsManager;
    CardView loader_login;
    @Override
    protected void onRestart() {
        super.onRestart();
       // SegmentsubmitButton.setBackgroundColor(getResources().getColor(R.color.colorash));
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segment);

        sharedPrefsManager = new SharedPrefsManager(SegmentActivity.this);
        volleyRequest = new VolleyRequest();
        rg = findViewById(R.id.radio_group_transfer);
        soleProprietorship = findViewById(R.id.soleProprietorship);
        privateLimited = findViewById(R.id.privateLimited);
        partnershipForm = findViewById(R.id.partnershipForm);
        SegmentsubmitButton = findViewById(R.id.SegmentsubmitButton);
        purplebackarrow = findViewById(R.id.purplebackarrow);
        loader_login = findViewById(R.id.loader_login);

        sharedPrefsManager.setCHECK_PAGE("2");

        /*FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.e("EnterOTP", "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        String token = task.getResult();
                        Log.e("FCM TOKEN", token);
                       // sendToken(token);
                    }
                });*/

        purplebackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(Build.VERSION.SDK_INT>=21) {
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_checked},
                            new int[]{android.R.attr.state_checked}
                    },
                    new int[]{

                            Color.rgb(23, 203, 133)
                            ,Color.rgb(255, 255, 255),
                    }
            );

            soleProprietorship.setSupportButtonTintList(colorStateList);
            privateLimited.setSupportButtonTintList(colorStateList);
            partnershipForm.setSupportButtonTintList(colorStateList);

            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    if (soleProprietorship.isChecked())
                    {
                        sharedPrefsManager.setSegment("1");
                        //SegmentsubmitButton.setBackgroundColor(getResources().getColor(R.color.neopurple));
                        SegmentsubmitButton.setBackground(getDrawable(R.drawable.gradient_neocredit));


                        soleProprietorship.setTextColor(Color.WHITE);
                        soleProprietorship.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackgroundcolor));

                        privateLimited.setTextColor(Color.BLACK);
                        privateLimited.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackground));

                        partnershipForm.setTextColor(Color.BLACK);
                        partnershipForm.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackground));
                    }
                    else if (privateLimited.isChecked())
                    {
                        sharedPrefsManager.setSegment("2");

                        SegmentsubmitButton.setBackground(getDrawable(R.drawable.gradient_neocredit));

                        privateLimited.setTextColor(Color.WHITE);
                        privateLimited.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackgroundcolor));

                        partnershipForm.setTextColor(Color.BLACK);
                        partnershipForm.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackground));

                        soleProprietorship.setTextColor(Color.BLACK);
                        soleProprietorship.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackground));
                    }

                    else if (partnershipForm.isChecked())
                    {
                        sharedPrefsManager.setSegment("3");

                        SegmentsubmitButton.setBackground(getDrawable(R.drawable.gradient_neocredit));

                        partnershipForm.setTextColor(Color.WHITE);
                        partnershipForm.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackgroundcolor));

                        privateLimited.setTextColor(Color.BLACK);
                        privateLimited.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackground));

                        soleProprietorship.setTextColor(Color.BLACK);
                        soleProprietorship.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackground));
                    }
                }
            });
        }

        SegmentsubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loader_login.setVisibility(View.VISIBLE);
                segmentApi();
            }
        });

    }

    private void sendToken(String token) {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("androidfcm_id", token);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        volleyRequest.JsonObjRequest(SegmentActivity.this,jsonObject,Urls.SEND_FCM_TOKEN, Constants.fcm_token);

    }

    private void segmentApi() {

        JSONObject segmentJsonObject = new JSONObject();

        try {
            segmentJsonObject.put("segment", sharedPrefsManager.getSegment());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        volleyRequest.JsonObjRequestAuthorization(SegmentActivity.this,segmentJsonObject,Urls.SEGMENT_URL,Constants.segment,sharedPrefsManager.getAccessToken());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void responseHandler(Object obj, int i) {

        JSONObject response = (JSONObject) obj;

        if (i == Constants.segment)
        {
            Log.e("resp", "responseHandler: " + response );

            if (response.has("message"))
            {
                Intent intent = new Intent(getApplicationContext(), CompanyDetails.class);
                startActivity(intent);
                loader_login.setVisibility(View.GONE);
            }
        }
        /*else if ( i == Constants.fcm_token)
        {
            try
            {
                String message = response.getString("message");
                if (message.equals("success"))
                {
                    Log.e("EnterOTP", "SuccessresponseHandler: "+ message );
                }
                else
                {
                    Log.e("EnterOTP", "errorresponseHandler: "+ message );
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }*/
    }
}