package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.extras.Constants;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.Urls;
import rutherfordit.com.instasalary.extras.VolleyRequest;
import rutherfordit.com.instasalary.myinterfaces.ResponseHandler;

public class BankDetailsActivity extends AppCompatActivity implements ResponseHandler {

    ImageView purplebackarrow;
    TextInputEditText sp_bankname,sp_bankbranch,sp_accno,sp_bankifsc;
    CardView sp_submitBankDetails, loader_bank_details;
    boolean click = false;
    VolleyRequest volleyRequest;
    SharedPrefsManager sharedPrefsManager;
    private boolean blurred;

   /* private void blurall() {


        if (blurred) {
            Blurry.delete((ViewGroup) findViewById(R.id.content));
        } else {
            long startMs = System.currentTimeMillis();
            Blurry.with(BankDetailsActivity.this)
                    .radius(25)
                    .sampling(2)
                    .async()
                    .animate(500)
                    .onto((ViewGroup) findViewById(R.id.content));
            Log.d(getString(R.string.app_name),
                    "TIME " + String.valueOf(System.currentTimeMillis() - startMs) + "ms");
        }

        blurred = !blurred;


    }*/

    @Override
    public void onBackPressed() {
        Toasty.error(getApplicationContext(),"Action Denied. Please proceed forward.",Toasty.LENGTH_SHORT).show();
        // super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);

        init();
        onclicks();
        textChangeListeners();
    }

    private void init()
    {
        purplebackarrow = findViewById(R.id.purplebackarrow);
        sp_bankname = findViewById(R.id.sp_bankname);
        sp_bankbranch = findViewById(R.id.sp_bankbranch);
        sp_accno = findViewById(R.id.sp_accno);
        sp_bankifsc = findViewById(R.id.sp_bankifsc);
        sp_submitBankDetails = findViewById(R.id.sp_submitBankDetails);

        volleyRequest = new VolleyRequest();
        sharedPrefsManager = new SharedPrefsManager(getApplicationContext());

        loader_bank_details = findViewById(R.id.loader_bank_details);

        sharedPrefsManager.setCHECK_PAGE("7");

    }

    private void request() {

        JSONObject jsonObject = new JSONObject();

        String acc_no = sp_accno.getText().toString();
        String ifsc_code = sp_bankifsc.getText().toString();

        try {
            jsonObject.put("beneficiary_account_no", acc_no);
            jsonObject.put("beneficiary_ifsc",ifsc_code);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("TAG", "request" + jsonObject);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.PENNY_DROP_BANK_DETAILS, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.e("responseBankAuth", "responseBankAuth" + response );
                try {
                    String verified = response.getString("verified");

                    if(verified.equals("true")){
                        bankApi();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("TAG", "error" + error.getLocalizedMessage());

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Basic QUlZODc1QjFZQjhFVTQxWTFaWlE3MUdSMUhQSjVEWDc6V05SVEQ4VVVTSkQ5UDgzS1RRWEkxU1g3NU1NQzNWVzY=");
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(jsonObjectRequest);

    }

    private void bankApi() {

        JSONObject jsonObjectBody = new JSONObject();

        try {

            jsonObjectBody.put("bank_name", sp_bankname.getText().toString());
            jsonObjectBody.put("bank_branch", sp_bankbranch.getText().toString());
            jsonObjectBody.put("ac_number", sp_accno.getText().toString());
            jsonObjectBody.put("bank_ifcs", sp_bankifsc.getText().toString());
            jsonObjectBody.put("company_id", sharedPrefsManager.getCOMPANY_ID());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("json", "bankApi: " + jsonObjectBody );

        volleyRequest.JsonObjRequestAuthorization(BankDetailsActivity.this,jsonObjectBody, Urls.SAVE_BANK_DETAILS, Constants.bank_details,sharedPrefsManager.getAccessToken());

       // Toasty.success(getApplicationContext(), "Saved Successfully..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void responseHandler(Object obj, int i) {

        if(i == Constants.bank_details) {

            JSONObject response = (JSONObject) obj;
            Log.e("response", "responseHandlerBankDetails: " + response);

            if  (response!=null)
            {

                Intent intent = new Intent(getApplicationContext(), UploadInvoice.class);
                startActivity(intent);
               // blurall();
                loader_bank_details.setVisibility(View.GONE);

            }
        }
    }

    private void textChangeListeners()
    {

        sp_bankname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!sp_bankname.getText().toString().equals("") && !sp_bankbranch.getText().toString().equals("")
                        && !sp_accno.getText().toString().equals("") && sp_bankifsc.getText().toString().length() == 11 )
                {
                    //sp_submitBankDetails.setBackgroundColor(getResources().getColor(R.color.neopurple));
                    sp_submitBankDetails.setBackground(getDrawable(R.drawable.gradient_neocredit));

                    click = true;
                }
                else
                {
                    sp_submitBankDetails.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        sp_bankbranch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!sp_bankname.getText().toString().equals("") && !sp_bankbranch.getText().toString().equals("")
                        && !sp_accno.getText().toString().equals("") && sp_bankifsc.getText().toString().length() == 11 )
                {
                    sp_submitBankDetails.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                }
                else
                {
                    sp_submitBankDetails.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        sp_accno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!sp_bankname.getText().toString().equals("") && !sp_bankbranch.getText().toString().equals("")
                        && !sp_accno.getText().toString().equals("") && sp_bankifsc.getText().toString().length() == 11 )
                {
                    sp_submitBankDetails.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                }
                else
                {
                    sp_submitBankDetails.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        sp_bankifsc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!sp_bankname.getText().toString().equals("") && !sp_bankbranch.getText().toString().equals("")
                        && !sp_accno.getText().toString().equals("") && sp_bankifsc.getText().toString().length() == 11 )
                {
                    sp_submitBankDetails.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                }
                else
                {
                    sp_submitBankDetails.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

    }

    private void onclicks() {

        purplebackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sp_submitBankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click)
                {
                  //  blurall();
                    loader_bank_details.setVisibility(View.VISIBLE);

                    bankApi();
                   // request();
                }
                else
                {
                    Toasty.warning(getApplicationContext(), "Please Fill The Fields..", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}