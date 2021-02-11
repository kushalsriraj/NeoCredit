package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import in.digio.sdk.kyc.DigioEnvironment;
import in.digio.sdk.kyc.DigioKycConfig;
import in.digio.sdk.kyc.DigioSession;
import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.activities.partnership.PartnershipPartnerFirstDetailsActivity;
import rutherfordit.com.instasalary.activities.privatelimited.PrivateLimitedDirectorFirstDetailsActivity;
import rutherfordit.com.instasalary.activities.privatelimited.PrivateLimitedDirectorSecondDetailsActivity;
import rutherfordit.com.instasalary.activities.sp.DatePickerFragment;
import rutherfordit.com.instasalary.activities.sp.SPDocumentUploadActivity;
import rutherfordit.com.instasalary.activities.sp.SoleProprietorshipDetailsActivity;
import rutherfordit.com.instasalary.extras.Constants;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.Urls;
import rutherfordit.com.instasalary.extras.VolleyRequest;
import rutherfordit.com.instasalary.myinterfaces.ResponseHandler;

public class PromoterDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, ResponseHandler {

    LayoutInflater inflater;
    View dialogView;
    AlertDialog dialogBuilder;
    String getid,id;

    RelativeLayout dir_next_button;
    ImageView purplebackarrow;
    TextInputEditText dir_fullName, dir_dob, dir_address, dir_pan, dir_adhar, dir_addressproof, dir_email, dir_mobile;
    Spinner dir_addressProofSpinner;
    boolean click = false;
    VolleyRequest volleyRequest;
    SharedPrefsManager sharedPrefsManager;
    TextView invalidDirEmail, invalidDirPan, detailsTextView;
    Button AdharAuth,submit,cancel;
    EditText adharno_auth,adharphone_auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promoter_details);

        init();
    }

    private void init() {

        getid = getIntent().getStringExtra("id");

        Log.e("getid", "init: " + getid );

        dir_fullName = findViewById(R.id.dir_fullName);
        dir_dob = findViewById(R.id.dir_dob);
        dir_address = findViewById(R.id.dir_address);
        dir_pan = findViewById(R.id.dir_pan);
        dir_adhar = findViewById(R.id.dir_adhar);
        dir_addressproof = findViewById(R.id.dir_addressproof);
        dir_addressProofSpinner = findViewById(R.id.dir_addressProofSpinner);
        dir_next_button = findViewById(R.id.dir_next_button);
        dir_next_button.setVisibility(View.GONE);
        purplebackarrow = findViewById(R.id.purplebackarrow);
        dir_email = findViewById(R.id.dir_email);
        dir_mobile = findViewById(R.id.dir_mobile);
        detailsTextView = findViewById(R.id.detailsTextView);
        AdharAuth = findViewById(R.id.AdharAuth);
        invalidDirEmail = findViewById(R.id.invalidDirEmail);
        invalidDirEmail.setVisibility(View.GONE);
        invalidDirPan = findViewById(R.id.invalidDirPan);
        invalidDirPan.setVisibility(View.GONE);

        volleyRequest = new VolleyRequest();
        sharedPrefsManager = new SharedPrefsManager(getApplicationContext());

        dialogBuilder = new AlertDialog.Builder(PromoterDetails.this).create();
        inflater = this.getLayoutInflater();
        dialogView = inflater.inflate(R.layout.custom_dialog_adhar, null);
        dialogBuilder.setView(dialogView);

        submit = dialogView.findViewById(R.id.submit);
        cancel = dialogView.findViewById(R.id.cancel);
        adharphone_auth = dialogView.findViewById(R.id.adharphone_auth);
        adharno_auth = dialogView.findViewById(R.id.adharno_auth);

        if(sharedPrefsManager.getSegment().equals("1")){

            detailsTextView.setText("Proprietor Details");
        }
        else {
            detailsTextView.setText("Promoter Details");
        }

        AdharAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.show();
                adharno_auth.setText(dir_fullName.getText().toString());
                adharphone_auth.setText(dir_mobile.getText().toString());
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestForWorkflow(adharno_auth.getText().toString(),adharphone_auth.getText().toString());
            }
        });

        onClicks();

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
                        digioSession.init(PromoterDetails.this, config);
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

        RequestQueue requestQueue = Volley.newRequestQueue(PromoterDetails.this);
        requestQueue.add(jsonObjectRequest);

    }

    private void getKycData() {

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

                        dialogBuilder.dismiss();

                        if (!dir_fullName.getText().toString().equals("")
                                && !dir_dob.getText().toString().equals("")
                                && !dir_email.getText().toString().equals("")
                                && !dir_pan.getText().toString().equals("")
                                && dir_adhar.getText().toString().length() == 12
                                &&!dir_address.getText().toString().equals("")
                                && !dir_addressproof.getText().toString().equals("-- Select Address Proof --")
                                && (dir_mobile.getText().toString().length() == 10))
                        {
                            AdharAuth.setVisibility(View.GONE);
                            dir_next_button.setVisibility(View.VISIBLE);
                            dir_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                            click = true;
                        } else {
                            AdharAuth.setVisibility(View.VISIBLE);
                            dir_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                            click = false;
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

        RequestQueue requestQueue = Volley.newRequestQueue(PromoterDetails.this);
        requestQueue.add(jsonObjectRequest);

    }

    public void onDigioKycFailure(String documentId, String message) {
        Toast.makeText(getApplicationContext(), "onDigioKycFailures:  " + message, Toast.LENGTH_SHORT ).show();
    }

    public void onDigioKycSuccess(String documentId, String message) {
        // progressDialog.cancel();
        Log.e("success", "onDigioKycSuccess:  " + message );
        getKycData();
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

    private void onClicks() {

        dir_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        dir_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!dir_fullName.getText().toString().equals("")
                        && !dir_dob.getText().toString().equals("")
                        && !dir_email.getText().toString().equals("")
                        && !dir_pan.getText().toString().equals("")
                        && dir_adhar.getText().toString().length() == 12
                        &&!dir_address.getText().toString().equals("")
                        && !dir_addressproof.getText().toString().equals("-- Select Address Proof --")
                        && (dir_mobile.getText().toString().length() == 10))
                {
                    AdharAuth.setVisibility(View.VISIBLE);
                    /*dir_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;*/
                } else {
                    AdharAuth.setVisibility(View.GONE);
                    /*dir_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;*/
                }

            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        dir_fullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!dir_fullName.getText().toString().equals("")
                        && !dir_dob.getText().toString().equals("")
                        && !dir_email.getText().toString().equals("")
                        && !dir_pan.getText().toString().equals("")
                        && dir_adhar.getText().toString().length() == 12
                        &&!dir_address.getText().toString().equals("")
                        && !dir_addressproof.getText().toString().equals("-- Select Address Proof --")
                        && (dir_mobile.getText().toString().length() == 10))
                {
                    AdharAuth.setVisibility(View.VISIBLE);
                    /*dir_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;*/
                } else {
                    AdharAuth.setVisibility(View.GONE);
                    /*dir_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;*/
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dir_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!dir_fullName.getText().toString().equals("")
                        && !dir_dob.getText().toString().equals("")
                        && !dir_email.getText().toString().equals("")
                        && !dir_pan.getText().toString().equals("")
                        && dir_adhar.getText().toString().length() == 12
                        &&!dir_address.getText().toString().equals("")
                        && !dir_addressproof.getText().toString().equals("-- Select Address Proof --")
                        && (dir_mobile.getText().toString().length() == 10))
                {
                    AdharAuth.setVisibility(View.VISIBLE);
                    /*dir_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;*/
                } else {
                    AdharAuth.setVisibility(View.GONE);
                    /*dir_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;*/
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dir_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (Patterns.EMAIL_ADDRESS.matcher(dir_email.getText().toString()).matches())
                {
                    invalidDirEmail.setVisibility(View.GONE);

                    if (!dir_fullName.getText().toString().equals("")
                            && !dir_dob.getText().toString().equals("")
                            && !dir_email.getText().toString().equals("")
                            && !dir_pan.getText().toString().equals("")
                            && dir_adhar.getText().toString().length() == 12
                            && !dir_address.getText().toString().equals("")
                            && !dir_addressproof.getText().toString().equals("-- Select Address Proof --")
                            && (dir_mobile.getText().toString().length() == 10))
                    {
                        AdharAuth.setVisibility(View.VISIBLE);
                    /*dir_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;*/
                    } else {
                        AdharAuth.setVisibility(View.GONE);
                    /*dir_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;*/
                    }

                }
                else
                {
                    invalidDirEmail.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        dir_pan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                Matcher matcher = pattern.matcher(dir_pan.getText().toString());

                if (matcher.matches())
                {
                    invalidDirPan.setVisibility(View.GONE);

                    if (!dir_fullName.getText().toString().equals("")
                            && !dir_dob.getText().toString().equals("")
                            && !dir_email.getText().toString().equals("")
                            && !dir_pan.getText().toString().equals("")
                            && dir_adhar.getText().toString().length() == 12
                            && !dir_address.getText().toString().equals("")
                            && !dir_addressproof.getText().toString().equals("-- Select Address Proof --")
                            && (dir_mobile.getText().toString().length() == 10))
                    {
                        AdharAuth.setVisibility(View.VISIBLE);
                    /*dir_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;*/
                    } else {
                        AdharAuth.setVisibility(View.GONE);
                    /*dir_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;*/
                    }
                }
                else
                {
                    invalidDirPan.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dir_adhar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!dir_fullName.getText().toString().equals("")
                        && !dir_dob.getText().toString().equals("")
                        && !dir_email.getText().toString().equals("")
                        && !dir_pan.getText().toString().equals("")
                        && dir_adhar.getText().toString().length() == 12
                        &&!dir_address.getText().toString().equals("")
                        && !dir_addressproof.getText().toString().equals("-- Select Address Proof --")
                        && (dir_mobile.getText().toString().length() == 10))
                {
                    AdharAuth.setVisibility(View.VISIBLE);
                    /*dir_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;*/
                } else {
                    AdharAuth.setVisibility(View.GONE);
                    /*dir_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;*/
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        dir_addressproof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dir_addressProofSpinner.performClick();
            }
        });

        dir_addressProofSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                        Matcher matcher = pattern.matcher(dir_pan.getText().toString());
                        // String addressProof = parent.getItemAtPosition(pos).toString();

                        dir_addressproof.setText(parent.getItemAtPosition(pos).toString());

                        if (!dir_fullName.getText().toString().equals("")
                                && !dir_dob.getText().toString().equals("")
                                && !dir_email.getText().toString().equals("")
                                && !dir_pan.getText().toString().equals("")
                                && dir_adhar.getText().toString().length() == 12
                                &&!dir_address.getText().toString().equals("")
                                && !dir_addressproof.getText().toString().equals("-- Select Address Proof --")
                                && (dir_mobile.getText().toString().length() == 10))
                        {
                            AdharAuth.setVisibility(View.VISIBLE);
                    /*dir_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;*/
                        } else {
                            AdharAuth.setVisibility(View.GONE);
                    /*dir_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;*/
                        }

                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                        if (!dir_fullName.getText().toString().equals("")
                                && !dir_dob.getText().toString().equals("")
                                && !dir_email.getText().toString().equals("")
                                && !dir_pan.getText().toString().equals("")
                                && dir_adhar.getText().toString().length() == 12
                                &&!dir_address.getText().toString().equals("")
                                && !dir_addressproof.getText().toString().equals("-- Select Address Proof --")
                                && (dir_mobile.getText().toString().length() == 10))
                        {
                            AdharAuth.setVisibility(View.VISIBLE);
                    /*dir_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;*/
                        } else {
                            AdharAuth.setVisibility(View.GONE);
                    /*dir_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;*/
                        }
                    }
                });

        purplebackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        dir_next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click) {

                    JSONObject jsonObjectBody = new JSONObject();

                    try {
                        jsonObjectBody.put("name", dir_fullName.getText().toString());
                        jsonObjectBody.put("pan_card", dir_pan.getText().toString());
                        jsonObjectBody.put("aadhar", dir_adhar.getText().toString());
                        jsonObjectBody.put("dob", dir_dob.getText().toString());
                        jsonObjectBody.put("email", dir_email.getText().toString());
                        jsonObjectBody.put("current_address", dir_address.getText().toString());
                        jsonObjectBody.put("current_address_proof", dir_addressproof.getText().toString());
                        jsonObjectBody.put("mobile_number", dir_mobile.getText().toString());
                        jsonObjectBody.put("user_type", sharedPrefsManager.getSegment());
                        jsonObjectBody.put("dirct_part_type", getid);
                        jsonObjectBody.put("company_id", "1");

                        volleyRequest.JsonObjRequestAuthorization(PromoterDetails.this,jsonObjectBody, Urls.DIRECTOR_DETAILS, Constants.director_details,sharedPrefsManager.getAccessToken());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Please Check All The Fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //here

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_CANCELED,returnIntent);
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        //  String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        String newMonth, newDay;

        if(month < 9){

            newMonth = "0" + (month+1);
        }
        else
        {
            newMonth = String.valueOf(month+1);
        }

        if(dayOfMonth < 10){

            newDay  = "0" + dayOfMonth ;
        }
        else
        {
            newDay = String.valueOf(dayOfMonth);
        }

        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
        Matcher matcher = pattern.matcher(dir_pan.getText().toString());

        dir_dob.setText( year + "-" + (newMonth) + "-" + newDay );

        if (!dir_fullName.getText().toString().equals("")
                && !dir_dob.getText().toString().equals("")
                && !dir_email.getText().toString().equals("")
                && !dir_pan.getText().toString().equals("")
                && dir_adhar.getText().toString().length() == 12
                &&!dir_address.getText().toString().equals("")
                && !dir_addressproof.getText().toString().equals("-- Select Address Proof --")
                && (dir_mobile.getText().toString().length() == 10))
        {
            AdharAuth.setVisibility(View.VISIBLE);
                    /*dir_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;*/
        } else {
            AdharAuth.setVisibility(View.GONE);
                    /*dir_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;*/
        }
    }

    @Override
    public void responseHandler(Object obj, int i) {

        if (i == Constants.director_details)
        {
            JSONObject response = (JSONObject) obj;
            Log.e("response", "responseHandlerDirector: " + response);

            if  (response!=null)
            {
                if (sharedPrefsManager.getSegment().equals("1"))
                {
                    Intent intent = new Intent(getApplicationContext(), SPDocumentUploadActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result",getid);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            }
        }
    }
}