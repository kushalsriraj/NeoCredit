package rutherfordit.com.instasalary.activities.privatelimited;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.activities.partnership.PartnershipCompanyDetailsActivity;
import rutherfordit.com.instasalary.activities.sp.SPDocumentUploadActivity;
import rutherfordit.com.instasalary.extras.Constants;
import rutherfordit.com.instasalary.extras.ResponseHandler;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.Urls;
import rutherfordit.com.instasalary.extras.VolleyRequest;

public class PrivateLimitedCompanyDetailsActivity extends AppCompatActivity implements ResponseHandler {

    RelativeLayout pl_next_button;
    ImageView purplebackarrow;
    TextInputEditText pl_company_name,pl_pancardnumber,pl_typeOfService,pl_addressProof,pl_businessRegNumber,pl_businessLandline,pl_businessPhoneNumber;
    Spinner pl_Spinner_businessAddressProof, pl_Spinner_typeOfService;
    boolean click = false;
    VolleyRequest volleyRequest;
    SharedPrefsManager sharedPrefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_limited_company_details);

        init();
        textWatchers();
        SpinnersSelected();
        onClicks();
    }

    private void init()
    {
        pl_next_button = findViewById(R.id.pl_next_button);

        pl_company_name = findViewById(R.id.pl_company_name);
        pl_pancardnumber = findViewById(R.id.pl_pancardnumber);
        pl_typeOfService = findViewById(R.id.pl_typeOfService);
        pl_addressProof = findViewById(R.id.pl_addressProof);
        pl_businessRegNumber = findViewById(R.id.pl_businessRegNumber);
        pl_businessLandline = findViewById(R.id.pl_businessLandline);
        pl_businessPhoneNumber = findViewById(R.id.pl_businessPhoneNumber);

        pl_Spinner_businessAddressProof = findViewById(R.id.pl_Spinner_businessAddressProof);
        pl_Spinner_typeOfService = findViewById(R.id.pl_Spinner_typeOfService);

        purplebackarrow = findViewById(R.id.purplebackarrow);

        volleyRequest = new VolleyRequest();
        sharedPrefsManager = new SharedPrefsManager(getApplicationContext());
    }

    private void textWatchers() {
        pl_company_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!pl_company_name.getText().toString().equals("") && !pl_company_name.getText().toString().equals("") &&
                        !pl_typeOfService.getText().toString().equals("-- Select Type of Service --") &&
                        !pl_pancardnumber.getText().toString().equals("[A-Z]{5}[0-9]{4}[A-Z]{1}") &&
                        !pl_businessRegNumber.getText().toString().equals("") &&
                        !pl_businessLandline.getText().toString().equals("-- Select Annual Turnover --") &&
                        !pl_addressProof.getText().toString().equals("-- Select Address Proof --") &&
                        !pl_businessPhoneNumber.getText().toString().equals("")) {
                    //submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.neopurple));
                    pl_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));

                    click = true;
                } else {
                    pl_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        pl_company_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!pl_company_name.getText().toString().equals("") && !pl_company_name.getText().toString().equals("") &&
                        !pl_typeOfService.getText().toString().equals("-- Select Type of Service --") &&
                        !pl_pancardnumber.getText().toString().equals("[A-Z]{5}[0-9]{4}[A-Z]{1}") &&
                        !pl_businessRegNumber.getText().toString().equals("") &&
                        !pl_businessLandline.getText().toString().equals("-- Select Annual Turnover --") &&
                        !pl_addressProof.getText().toString().equals("-- Select Address Proof --") &&
                        !pl_businessPhoneNumber.getText().toString().equals("")) {
                    //submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.neopurple));
                    pl_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));

                    click = true;
                }
                else
                {
                    pl_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void SpinnersSelected()
    {

        pl_Spinner_typeOfService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                // String addressProof = parent.getItemAtPosition(pos).toString();

                pl_typeOfService.setText(parent.getItemAtPosition(pos).toString());

                if (!pl_company_name.getText().toString().equals("") && !pl_company_name.getText().toString().equals("") &&
                        !pl_typeOfService.getText().toString().equals("-- Select Type of Service --") &&
                        !pl_pancardnumber.getText().toString().equals("[A-Z]{5}[0-9]{4}[A-Z]{1}") &&
                        !pl_businessRegNumber.getText().toString().equals("") &&
                        !pl_businessLandline.getText().toString().equals("-- Select Annual Turnover --") &&
                        !pl_addressProof.getText().toString().equals("-- Select Address Proof --") &&
                        !pl_businessPhoneNumber.getText().toString().equals("")) {
                    //submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.neopurple));
                    pl_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));

                    click = true;
                }
                else
                {
                    pl_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }
            public void onNothingSelected(AdapterView<?> parent) { }
        });


        pl_Spinner_businessAddressProof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                // String addressProof = parent.getItemAtPosition(pos).toString();

                pl_addressProof.setText(parent.getItemAtPosition(pos).toString());

                if (!pl_company_name.getText().toString().equals("") && !pl_company_name.getText().toString().equals("") &&
                        !pl_typeOfService.getText().toString().equals("-- Select Type of Service --") &&
                        !pl_pancardnumber.getText().toString().equals("[A-Z]{5}[0-9]{4}[A-Z]{1}") &&
                        !pl_businessRegNumber.getText().toString().equals("") &&
                        !pl_businessLandline.getText().toString().equals("-- Select Annual Turnover --") &&
                        !pl_addressProof.getText().toString().equals("-- Select Address Proof --") &&
                        !pl_businessPhoneNumber.getText().toString().equals("")) {
                    //submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.neopurple));
                    pl_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));

                    click = true;
                }
                else
                {
                    pl_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void onClicks()
    {
        pl_typeOfService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pl_Spinner_typeOfService.performClick();
            }
        });

        pl_addressProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pl_Spinner_businessAddressProof.performClick();
            }
        });

        purplebackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        pl_next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (click)
                {
                    JSONObject jsonObjectBody = new JSONObject();

                    try {

//                        jsonObjectBody.put("name", pl_company_name.getText().toString());
//                        jsonObjectBody.put("pan_card", pl_pancardnumber.getText().toString());
//                        jsonObjectBody.put("type_of_services", pl_typeOfService.getText().toString());
//                        jsonObjectBody.put("business_reg_num", pl_addressProof.getText().toString());
//                        jsonObjectBody.put("business_addr_proof", pl_addressProof.getText().toString());
//                        jsonObjectBody.put("landline_number", pl_businessLandline.getText().toString());
//                        jsonObjectBody.put("mobile_number", pl_businessPhoneNumber.getText().toString());

                        jsonObjectBody.put("name","564592362312");
                        jsonObjectBody.put("pan_card","PPPPH1234F");
                        jsonObjectBody.put("type_of_services","123456789098");
                        jsonObjectBody.put("business_reg_num","1999-12-23");
                        jsonObjectBody.put("business_addr_proof","PPPPH1234F");
                        jsonObjectBody.put("landline_number","PPPPH1234F");
                        jsonObjectBody.put("pincode","123456667");
                        jsonObjectBody.put("mobile_number","1224567888");

                        volleyRequest.JsonObjRequestAuthorization(PrivateLimitedCompanyDetailsActivity.this,jsonObjectBody, Urls.COMPANY_DETAILS, Constants.company_details,sharedPrefsManager.getAccessToken());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Intent intent = new Intent(getApplicationContext(), PrivateLimitedDirectorFirstDetailsActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please Check All The Fields.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void responseHandler(Object obj, int i) {

        if(i == Constants.company_details) {

            JSONObject response = (JSONObject) obj;

            Log.e("response", "responseHandler: " + response);

        }
    }
}
