package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.activities.partnership.PartnershipCompanyDetailsActivity;
import rutherfordit.com.instasalary.activities.privatelimited.PrivateLimitedCompanyDetailsActivity;
import rutherfordit.com.instasalary.activities.sp.SoleProprietorshipDetailsActivity;
import rutherfordit.com.instasalary.extras.MySingleton;
import rutherfordit.com.instasalary.extras.Urls;

public class SegmentActivity extends AppCompatActivity {

    RelativeLayout SegmentsubmitButton;
    RadioGroup rg;
    AppCompatRadioButton soleProprietorship;
    AppCompatRadioButton privateLimited;
    AppCompatRadioButton partnershipForm;
    ImageView purplebackarrow;
    String segment;

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

        rg = findViewById(R.id.radio_group_transfer);
        soleProprietorship = findViewById(R.id.soleProprietorship);
        privateLimited = findViewById(R.id.privateLimited);
        partnershipForm = findViewById(R.id.partnershipForm);
        SegmentsubmitButton = findViewById(R.id.SegmentsubmitButton);
        purplebackarrow = findViewById(R.id.purplebackarrow);

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
                        segment = "1";
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
                        segment = "2";

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
                        segment = "3";

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

                segmentApi();

                if (soleProprietorship.isChecked())
                {
                    Intent intent = new Intent(getApplicationContext(), SoleProprietorshipDetailsActivity.class);
                    startActivity(intent);
                }
                else if (privateLimited.isChecked())
                {
                    Intent intent = new Intent(getApplicationContext(), PrivateLimitedCompanyDetailsActivity.class);
                    startActivity(intent);
                }
                else if (partnershipForm.isChecked())
                {
                    Intent intent = new Intent(getApplicationContext(), PartnershipCompanyDetailsActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void segmentApi() {

        JSONObject segmentJsonObject = new JSONObject();

        try {
            segmentJsonObject.put("segment", segment);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.SEGMENT_URL, segmentJsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("jsonObjectRequest", "jsonObjectRequest: " + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", "error: " + error.getLocalizedMessage() );
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIyIiwianRpIjoiZTFjMWM3MDZhZTk1MTM5YWY5NmRiM2JjYTFmMjIyNmI2MTA2ZTc1ZjZmMmU3NjUzYzVjMDY2YWZkMWFmOTBjMWFmMzhmYzljYzVkMGM1YmQiLCJpYXQiOjE2MTIyNjU0NDEsIm5iZiI6MTYxMjI2NTQ0MSwiZXhwIjoxNjQzODAxNDQwLCJzdWIiOiIyIiwic2NvcGVzIjpbIioiXX0.l1jpm70O8DeuL1_yyeMpdhfdrjAYGPTuqAaoeVyn5y4lxOBBTfuRocY-IKjGiQJ-NvvgZf6HA4QGqC4IW56Kef7Z-LLf9v9GAnLTJAGitnDdG1fWoZf6lox7ty0yQML3Qkm4LlQ-5P0_xTwYfmOnnB7gB-pfK8YybGmt8bhWMJex-2ZH-sTzqawnNm5j9yURQBM-cI5HSBoub-rg5CBVqytF2EVPR_ssh9MSzSdvIh1C6QAn8OvLEghOMod_DhEJMBKBAWNsyH6llmShu1rEsbxicvmosXORovYE1ABfEXsgO_YNZTgI7jj3AAmddj3wYD9VCYAT2XooMRs95dJfUAeQl08ceLAf9PVG94k0ebWOAScG0dr9LujDhSs1MQn3rUCipyQF2193YxnIwKR445yFIPufrUQF1D8qkSNF_aKY4ituF_HAHP4JA7vFLk75z_Ae3Kx_D9qn4f0-AZqN_tfXZY3x2-Oa6ZGFBkYVs6QLQWbiAswAvjTOIRpnijVJ3ULVOaZM2WJ-NQywDu6npmT8w14ki88S3Ygu6_8wG-I8AlkDisZLzWSerWnsljXCIHXdN9YW8xvbw5_KnZACw-XV0aPeFXN8a3QNsBnVEAn2yqqkR_IAVVxdpkr1jAQV1SABP0rnRb6vbEz7rrt8brafiM5260VfjpdweIh6os4");
                return headers;
            }
        };

        MySingleton.getInstance(SegmentActivity.this).addToRequestQueue(jsonObjectRequest);
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
}