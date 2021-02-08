package rutherfordit.com.instasalary.activities.partnership;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import rutherfordit.com.instasalary.activities.EnterOTPActivity;
import rutherfordit.com.instasalary.activities.privatelimited.PrivateLimitedCompanyDetailsActivity;
import rutherfordit.com.instasalary.activities.privatelimited.PrivateLimitedDirectorFirstDetailsActivity;
import rutherfordit.com.instasalary.extras.Constants;
import rutherfordit.com.instasalary.myinterfaces.ResponseHandler;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.Urls;
import rutherfordit.com.instasalary.extras.VolleyRequest;

public class PartnershipCompanyDetailsActivity extends AppCompatActivity implements ResponseHandler{

    RelativeLayout par_next_button;
    ImageView purplebackarrow;
    TextInputEditText par_company_name, par_pancardnumber, par_typeOfService, par_addressProof, par_businessRegNumber, par_businessLandline, par_businessPhoneNumber, par_pincode;
    Spinner par_Spinner_businessAddressProof, par_Spinner_typeOfService;
    boolean click = false;
    SharedPrefsManager sharedPrefsManager;
    VolleyRequest volleyRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnership_company_details);


        init();
        textWatchers();
        SpinnersSelected();
        onClicks();
    }

    private void init()
    {
        par_next_button = findViewById(R.id.par_next_button);

        par_company_name = findViewById(R.id.par_company_name);
        par_pancardnumber = findViewById(R.id.par_pancardnumber);
        par_typeOfService = findViewById(R.id.par_typeOfService);
        par_addressProof = findViewById(R.id.par_addressProof);
        par_businessRegNumber = findViewById(R.id.par_businessRegNumber);
        par_businessLandline = findViewById(R.id.par_businessLandline);
        par_businessPhoneNumber = findViewById(R.id.par_businessPhoneNumber);

        par_Spinner_businessAddressProof = findViewById(R.id.par_Spinner_businessAddressProof);
        par_Spinner_typeOfService = findViewById(R.id.par_Spinner_typeOfService);

        purplebackarrow = findViewById(R.id.purplebackarrow);

        sharedPrefsManager = new SharedPrefsManager(getApplicationContext());
        volleyRequest = new VolleyRequest();

    }

    private void textWatchers() {
        par_company_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!par_company_name.getText().toString().equals("") && !par_company_name.getText().toString().equals("") &&
                        !par_typeOfService.getText().toString().equals("-- Select Type of Service --") &&
                        !par_pancardnumber.getText().toString().equals("[A-Z]{5}[0-9]{4}[A-Z]{1}") &&
                        !par_businessRegNumber.getText().toString().equals("") &&
                        !par_businessLandline.getText().toString().equals("-- Select Annual Turnover --") &&
                        !par_addressProof.getText().toString().equals("-- Select Address Proof --") &&
                        !par_businessPhoneNumber.getText().toString().equals("")) {
                    //submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.neopurple));
                    par_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));

                    click = true;
                } else {
                    par_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        par_company_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!par_company_name.getText().toString().equals("") && !par_company_name.getText().toString().equals("") &&
                        !par_typeOfService.getText().toString().equals("-- Select Type of Service --") &&
                        !par_pancardnumber.getText().toString().equals("[A-Z]{5}[0-9]{4}[A-Z]{1}") &&
                        !par_businessRegNumber.getText().toString().equals("") &&
                        !par_businessLandline.getText().toString().equals("-- Select Annual Turnover --") &&
                        !par_addressProof.getText().toString().equals("-- Select Address Proof --") &&
                        !par_businessPhoneNumber.getText().toString().equals("")) {
                    //submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.neopurple));
                    par_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));

                    click = true;
                }
                else
                {
                    par_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
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

        par_Spinner_typeOfService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                // String addressProof = parent.getItemAtPosition(pos).toString();

                par_typeOfService.setText(parent.getItemAtPosition(pos).toString());

                if (!par_company_name.getText().toString().equals("") && !par_company_name.getText().toString().equals("") &&
                        !par_typeOfService.getText().toString().equals("-- Select Type of Service --") &&
                        !par_pancardnumber.getText().toString().equals("[A-Z]{5}[0-9]{4}[A-Z]{1}") &&
                        !par_businessRegNumber.getText().toString().equals("") &&
                        !par_businessLandline.getText().toString().equals("-- Select Annual Turnover --") &&
                        !par_addressProof.getText().toString().equals("-- Select Address Proof --") &&
                        !par_businessPhoneNumber.getText().toString().equals("")) {
                    //submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.neopurple));
                    par_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));

                    click = true;
                }
                else
                {
                    par_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }
            public void onNothingSelected(AdapterView<?> parent) { }
        });


        par_Spinner_businessAddressProof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                // String addressProof = parent.getItemAtPosition(pos).toString();

                par_addressProof.setText(parent.getItemAtPosition(pos).toString());

                if (!par_company_name.getText().toString().equals("") && !par_company_name.getText().toString().equals("") &&
                        !par_typeOfService.getText().toString().equals("-- Select Type of Service --") &&
                        !par_pancardnumber.getText().toString().equals("[A-Z]{5}[0-9]{4}[A-Z]{1}") &&
                        !par_businessRegNumber.getText().toString().equals("") &&
                        !par_businessLandline.getText().toString().equals("-- Select Annual Turnover --") &&
                        !par_addressProof.getText().toString().equals("-- Select Address Proof --") &&
                        !par_businessPhoneNumber.getText().toString().equals("")) {
                    //submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.neopurple));
                    par_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));

                    click = true;
                }
                else
                {
                    par_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void onClicks()
    {
        par_typeOfService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                par_Spinner_typeOfService.performClick();
            }
        });

        par_addressProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                par_Spinner_businessAddressProof.performClick();
            }
        });

        purplebackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        par_next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (click)
                {
                    JSONObject jsonObjectBody = new JSONObject();

                    try {

                        jsonObjectBody.put("name", par_company_name.getText().toString());
                        jsonObjectBody.put("pan_card",  par_pancardnumber.getText().toString());
                        jsonObjectBody.put("type_of_services",  par_typeOfService.getText().toString());
                        jsonObjectBody.put("business_reg_num",  par_businessRegNumber.getText().toString());
                        jsonObjectBody.put("business_addr_proof", par_addressProof.getText().toString());
                        jsonObjectBody.put("landline_number",  par_businessLandline.getText().toString());
                        jsonObjectBody.put("mobile_number",  par_businessPhoneNumber.getText().toString());

                        volleyRequest.JsonObjRequestAuthorization(PartnershipCompanyDetailsActivity.this,jsonObjectBody, Urls.COMPANY_DETAILS, Constants.company_details1,sharedPrefsManager.getAccessToken());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Intent intent = new Intent(getApplicationContext(), PartnershipPartnerFirstDetailsActivity.class);
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

        if(i == Constants.company_details1) {

            JSONObject response = (JSONObject) obj;

            Log.e("response", "responseHandlerCompany: " + response);

        }
    }
}