package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import in.digio.sdk.kyc.DigioEnvironment;
import in.digio.sdk.kyc.DigioKycConfig;
import in.digio.sdk.kyc.DigioSession;
import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.activities.partnership.PartnershipPartnerFirstDetailsActivity;
import rutherfordit.com.instasalary.activities.privatelimited.PrivateLimitedDirectorFirstDetailsActivity;
import rutherfordit.com.instasalary.activities.sp.SoleProprietorshipDetailsActivity;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.Urls;

public class AdharAuthenticationActivity extends AppCompatActivity {

    TextInputEditText adhaar_phone,adhaar_name;
    RelativeLayout authenticate_adhar_button;
    String id;
    boolean click = false;
   // ImageView adharImg;
    ProgressDialog progressDialog;
    SharedPrefsManager sharedPrefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adhar_authentication);

        sharedPrefsManager = new SharedPrefsManager(AdharAuthenticationActivity.this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setCancelable(false);

        authenticate_adhar_button = findViewById(R.id.authenticate_adhar_button);
        adhaar_name = findViewById(R.id.adhaar_name);
        adhaar_phone = findViewById(R.id.adhaar_phone);
       // adharImg = findViewById(R.id.adharImg);

        adhaar_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!adhaar_name.getText().toString().equals("") && adhaar_phone.getText().toString().length() == 10)
                {
                    //submitSpInfo.setBackgroundColor(getResources().getColor(R.color.neopurple));
                    authenticate_adhar_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                }
                else
                {
                    authenticate_adhar_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adhaar_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!adhaar_name.getText().toString().equals("") && adhaar_phone.getText().toString().length() == 10)
                {
                    //submitSpInfo.setBackgroundColor(getResources().getColor(R.color.neopurple));
                    authenticate_adhar_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                }
                else
                {
                    authenticate_adhar_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        authenticate_adhar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (click)
                {
                    progressDialog.show();
                    requestForWorkflow(adhaar_name.getText().toString(),adhaar_phone.getText().toString());
                }
                else {
                    Toasty.warning(getApplicationContext(),"Please Fill The Fields..",Toasty.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void requestForWorkflow(String Name, String Identifier) {

        JSONObject jsonObject = new JSONObject();
        try {
            //Phone or Email of the customer
            jsonObject.put("customer_identifier", Identifier);
            jsonObject.put("customer_name", Name);
            jsonObject.put("reference_id", "008");
            jsonObject.put("template_name", "ADHARVALIDATION");
            jsonObject.put("notify_customer", "false");
            jsonObject.put("transaction_id", "008");
            jsonObject.put("generate_access_token", "true");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("myRequestObj", "requestForWorkflow: " + jsonObject );

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.REQUEST_TEMPLATE, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.e("myResponseObj", "onResponse: " + response );

                try {
                    id = response.getString("id");
                    String customer_identifier = response.getString("customer_identifier");

                    JSONObject accessToken = response.getJSONObject("access_token");

                    String tokenId = accessToken.getString("id");

                    try {
                        DigioKycConfig config = new DigioKycConfig();
                        config.setEnvironment(DigioEnvironment.SANDBOX);
                        DigioSession digioSession = new DigioSession();
                        digioSession.init(AdharAuthenticationActivity.this, config);
                        digioSession.startSession(id, customer_identifier,tokenId);

                    } catch(Exception e) { }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("volleyError", "onErrorResponse: " + error.getLocalizedMessage() );

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", Urls.DIGIO_AUTHENTICATOR);
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(AdharAuthenticationActivity.this);
        requestQueue.add(jsonObjectRequest);

    }

    public void onDigioKycSuccess(String documentId, String message) {
        progressDialog.cancel();
        Log.e("success", "onDigioKycSuccess:  " + message );
        getKycData();
    }

    private void getKycData()
    {

        String getDataURL = "https://ext.digio.in:444/client/kyc/v2/"+id+"/response";

        Log.e("dataURL", "getKycData:  " + getDataURL );

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, getDataURL, null , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.e("DataResponse", "DataResponse: " + response );

                try {
                    JSONArray actions = response.getJSONArray("actions");

                    for (int i = 0 ; i < actions.length() ; i++ )
                    {

                        JSONObject actionsObject = actions.getJSONObject(0);

                        JSONObject detailsObj = actionsObject.getJSONObject("details");

                        JSONObject adharObj = detailsObj.getJSONObject("aadhaar");

                        String image = adharObj.getString("image");

                        Bitmap bm = StringToBitMap(image);

                       // adharImg.setImageBitmap(bm);

                        Log.e("adhar", "onResponse: " + adharObj.getString("id_proof_type") );

                        Log.e("adhar", "onResponse: " + image );

                        if (sharedPrefsManager.getSegment().equals("1"))
                        {
                            Intent intent = new Intent(getApplicationContext(), SoleProprietorshipDetailsActivity.class);
                            startActivity(intent);
                        }
                        else if (sharedPrefsManager.getSegment().equals("2")){
                            Intent intent = new Intent(getApplicationContext(), PrivateLimitedDirectorFirstDetailsActivity.class);
                            startActivity(intent);
                        }
                        else if(sharedPrefsManager.getSegment().equals("3")){
                            Intent intent = new Intent(getApplicationContext(), PartnershipPartnerFirstDetailsActivity.class);
                            startActivity(intent);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("volleyError", "onErrorResponse: " + error.getLocalizedMessage() );

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Basic QUlZODc1QjFZQjhFVTQxWTFaWlE3MUdSMUhQSjVEWDc6V05SVEQ4VVVTSkQ5UDgzS1RRWEkxU1g3NU1NQzNWVzY=");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(AdharAuthenticationActivity.this);
        requestQueue.add(jsonObjectRequest);

    }

    public void onDigioKycFailure(String documentId, String message) {
        Toast.makeText(getApplicationContext(), "onDigioKycFailures:  " + message, Toast.LENGTH_SHORT ).show();
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}