package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.activities.partnership.PartnershipDocUploadActivity;
import rutherfordit.com.instasalary.activities.privatelimited.PrivateLimitedCompanyDetailsActivity;
import rutherfordit.com.instasalary.activities.privatelimited.PrivateLimitedDirectorFirstDetailsActivity;
import rutherfordit.com.instasalary.activities.privatelimited.PrivateLimitedDocUploadActivity;
import rutherfordit.com.instasalary.activities.sp.SPDocumentUploadActivity;
import rutherfordit.com.instasalary.extras.Constants;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.Urls;
import rutherfordit.com.instasalary.extras.VolleyRequest;
import rutherfordit.com.instasalary.myinterfaces.ResponseHandler;

public class CompanyDetails extends AppCompatActivity implements ResponseHandler {

    TextView invalidPan, invalidEmail;
    SharedPrefsManager sharedPrefsManager;
    VolleyRequest volleyRequest;
    RelativeLayout submitCompanyInfo;
    ImageView purplebackarrow;
    TextInputEditText businessLandline, company_name, typeOfService, howOldIsTheCompany, annualTurnover, company_address, addressProof, company_email, pancardnumber;
    Spinner Spinner_typeOfService, Spinner_howOldIsTheCompany, Spinner_annualTurnover, Spinner_businessAddressProof;
    boolean click = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);

        init();

    }

    private void init()
    {

        invalidPan = findViewById(R.id.invalidPan);
        invalidEmail = findViewById(R.id.invalidEmail);
        invalidPan.setVisibility(View.GONE);
        businessLandline = findViewById(R.id.sp_businessLandline);
        company_email = findViewById(R.id.company_email);
        pancardnumber = findViewById(R.id.par_pancardnumber);
        company_name = findViewById(R.id.sp_company_name);
        typeOfService = findViewById(R.id.sp_typeOfService);
        howOldIsTheCompany = findViewById(R.id.sp_howOldIsTheCompany);
        annualTurnover = findViewById(R.id.sp_annualTurnover);
        company_address = findViewById(R.id.sp_company_address);
        addressProof = findViewById(R.id.sp_addressProof);
        Spinner_typeOfService = findViewById(R.id.Spinner_typeOfService);
        Spinner_howOldIsTheCompany = findViewById(R.id.Spinner_howOldIsTheCompany);
        Spinner_annualTurnover = findViewById(R.id.Spinner_annualTurnover);
        Spinner_businessAddressProof = findViewById(R.id.Spinner_businessAddressProof);

        submitCompanyInfo = findViewById(R.id.submitCompanyInfo);
        purplebackarrow = findViewById(R.id.purplebackarrow);

        onClicks();
        SpinnersSelected();
        textWatchers();
    }

    private void textWatchers()
    {

        company_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!company_name.getText().toString().equals("")
                        &&!company_email.getText().toString().equals("")
                        && !pancardnumber.getText().toString().equals("")
                        &&!company_address.getText().toString().equals("")
                        &&!typeOfService.getText().toString().equals("-- Select Type of Service --")
                        &&!howOldIsTheCompany.getText().toString().equals("-- Select Tenuraty the Company --")
                        && !annualTurnover.getText().toString().equals("-- Select Annual Turnover --")&&
                        !addressProof.getText().toString().equals("-- Select Address Proof --"))
                {
                    submitCompanyInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click=true;
                }
                else {
                    submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click=false;
                }

            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        company_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                ;

                if (Patterns.EMAIL_ADDRESS.matcher(company_email.getText().toString()).matches())
                {
                    invalidEmail.setVisibility(View.GONE);

                    if (!company_name.getText().toString().equals("")
                            && !company_email.getText().toString().equals("")
                            && !pancardnumber.getText().toString().equals("")
                            &&!company_address.getText().toString().equals("")
                            &&!typeOfService.getText().toString().equals("-- Select Type of Service --")
                            &&!howOldIsTheCompany.getText().toString().equals("-- Select Tenuraty the Company --")
                            && !annualTurnover.getText().toString().equals("-- Select Annual Turnover --")&&
                            !addressProof.getText().toString().equals("-- Select Address Proof --"))
                    {
                        submitCompanyInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));
                        click=true;
                    }
                    else {
                        submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
                        click=false;
                    }

                }
                else
                {
                    invalidPan.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        pancardnumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                Matcher matcher = pattern.matcher(pancardnumber.getText().toString());

                if (matcher.matches())
                {
                    invalidPan.setVisibility(View.GONE);

                    if (!company_name.getText().toString().equals("")
                            && !pancardnumber.getText().toString().equals("")
                            &&!company_address.getText().toString().equals("")
                            &&!typeOfService.getText().toString().equals("-- Select Type of Service --")
                            &&!howOldIsTheCompany.getText().toString().equals("-- Select Tenuraty the Company --")
                            && !annualTurnover.getText().toString().equals("-- Select Annual Turnover --")&&
                            !addressProof.getText().toString().equals("-- Select Address Proof --"))
                    {
                        submitCompanyInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));
                        click=true;
                    }
                    else {
                        submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
                        click=false;
                    }

                }
                else
                {
                    invalidPan.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        company_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!company_name.getText().toString().equals("")
                        &&!company_email.getText().toString().equals("")
                        && !pancardnumber.getText().toString().equals("")
                        &&!company_address.getText().toString().equals("")
                        &&!typeOfService.getText().toString().equals("-- Select Type of Service --")
                        &&!howOldIsTheCompany.getText().toString().equals("-- Select Tenuraty the Company --")
                        && !annualTurnover.getText().toString().equals("-- Select Annual Turnover --")&&
                        !addressProof.getText().toString().equals("-- Select Address Proof --"))
                {
                    submitCompanyInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click=true;
                }
                else {
                    submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click=false;
                }


            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
        businessLandline.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!company_name.getText().toString().equals("")
                        &&!company_email.getText().toString().equals("")
                        && !pancardnumber.getText().toString().equals("")
                        &&!company_address.getText().toString().equals("")
                        &&!typeOfService.getText().toString().equals("-- Select Type of Service --")
                        &&!howOldIsTheCompany.getText().toString().equals("-- Select Tenuraty the Company --")
                        && !annualTurnover.getText().toString().equals("-- Select Annual Turnover --")&&
                        !addressProof.getText().toString().equals("-- Select Address Proof --"))
                {
                    submitCompanyInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click=true;
                }
                else {
                    submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click=false;
                }

            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

    }

    //Done till here

    private void SpinnersSelected()
    {

        Spinner_typeOfService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                typeOfService.setText(parent.getItemAtPosition(pos).toString());

                if (!company_name.getText().toString().equals("")
                        &&!company_email.getText().toString().equals("")
                        && !pancardnumber.getText().toString().equals("")
                        &&!company_address.getText().toString().equals("")
                        &&!typeOfService.getText().toString().equals("-- Select Type of Service --")
                        &&!howOldIsTheCompany.getText().toString().equals("-- Select Tenuraty the Company --")
                        && !annualTurnover.getText().toString().equals("-- Select Annual Turnover --")&&
                        !addressProof.getText().toString().equals("-- Select Address Proof --"))
                {
                    submitCompanyInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click=true;
                }
                else {
                    submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click=false;
                }

            }
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        Spinner_howOldIsTheCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                howOldIsTheCompany.setText(parent.getItemAtPosition(pos).toString());

                if (!company_name.getText().toString().equals("")
                        &&!company_email.getText().toString().equals("")
                        && !pancardnumber.getText().toString().equals("")
                        &&!company_address.getText().toString().equals("")
                        &&!typeOfService.getText().toString().equals("-- Select Type of Service --")
                        &&!howOldIsTheCompany.getText().toString().equals("-- Select Tenuraty the Company --")
                        && !annualTurnover.getText().toString().equals("-- Select Annual Turnover --")&&
                        !addressProof.getText().toString().equals("-- Select Address Proof --"))
                {
                    submitCompanyInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click=true;
                }
                else {
                    submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click=false;
                }


            }
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        Spinner_annualTurnover.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                annualTurnover.setText(parent.getItemAtPosition(pos).toString());

                if (!company_name.getText().toString().equals("")
                        &&!company_email.getText().toString().equals("")
                        && !pancardnumber.getText().toString().equals("")
                        &&!company_address.getText().toString().equals("")
                        &&!typeOfService.getText().toString().equals("-- Select Type of Service --")
                        &&!howOldIsTheCompany.getText().toString().equals("-- Select Tenuraty the Company --")
                        && !annualTurnover.getText().toString().equals("-- Select Annual Turnover --")&&
                        !addressProof.getText().toString().equals("-- Select Address Proof --"))
                {
                    submitCompanyInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click=true;
                }
                else {
                    submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click=false;
                }


            }
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        Spinner_businessAddressProof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                addressProof.setText(parent.getItemAtPosition(pos).toString());

                if (!company_name.getText().toString().equals("")
                        &&!company_email.getText().toString().equals("")
                        && !pancardnumber.getText().toString().equals("")
                        &&!company_address.getText().toString().equals("")
                        &&!typeOfService.getText().toString().equals("-- Select Type of Service --")
                        &&!howOldIsTheCompany.getText().toString().equals("-- Select Tenuraty the Company --")
                        && !annualTurnover.getText().toString().equals("-- Select Annual Turnover --")&&
                        !addressProof.getText().toString().equals("-- Select Address Proof --"))
                {
                    submitCompanyInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click=true;
                }
                else {
                    submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click=false;
                }


            }
            public void onNothingSelected(AdapterView<?> parent) { }
        });

    }

    private void onClicks()
    {

        typeOfService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner_typeOfService.performClick();
            }
        });
        howOldIsTheCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner_howOldIsTheCompany.performClick();
            }
        });
        annualTurnover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner_annualTurnover.performClick();
            }
        });
        addressProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner_businessAddressProof.performClick();
            }
        });

        purplebackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        submitCompanyInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (click)
                {
                    comapanyAPI();

//                    if(sharedPrefsManager.getSegment() == "1"){
//
//                        Intent intent = new Intent(getApplicationContext(), SPDocumentUploadActivity.class);
//                        startActivity(intent);
//                    }
//                    else if(sharedPrefsManager.getSegment() == "2"){
//
//                        Intent intent = new Intent(getApplicationContext(), PrivateLimitedDocUploadActivity.class);
//                        startActivity(intent);
//                    }
//
//                    else if(sharedPrefsManager.getSegment() == "3"){
//
//                        Intent intent = new Intent(getApplicationContext(), PartnershipDocUploadActivity.class);
//                        startActivity(intent);
//                    }

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please Check All The Fields.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void comapanyAPI() {
        JSONObject jsonObjectBody = new JSONObject();

//        {
//            "name":"564592362312",
//                "pan_card":"PPPPH1234F",
//                "type_of_services":"123456789098",
//                "business_reg_num":"1999-12-23",
//                "business_addr_proof":"PPPPH1234F",
//                "landline_number":"PPPPH1234F",
//                "mobile_number":"1224567888",
//                "orgize_age":"1",
//                "annual_turnover":"1"
//                 "email":"jadssf@gmail.com",
//                "company_address":"qwertyqwerty"
//        }

        try {

            jsonObjectBody.put("name", company_name.getText().toString());
            jsonObjectBody.put("email", company_email.getText().toString());
            jsonObjectBody.put("pan_card", pancardnumber.getText().toString());
            jsonObjectBody.put("type_of_services", typeOfService.getText().toString());
            jsonObjectBody.put("company_address", company_address.getText().toString());
            jsonObjectBody.put("business_addr_proof", addressProof.getText().toString());
            jsonObjectBody.put("landline_number", businessLandline.getText().toString());
            jsonObjectBody.put("orgize_age", howOldIsTheCompany.getText().toString());
            jsonObjectBody.put("annual_turnover", annualTurnover.getText().toString());

            Log.e("click", "onClick: " + jsonObjectBody );


            volleyRequest.JsonObjRequestAuthorization(CompanyDetails.this,jsonObjectBody, Urls.COMPANY_DETAILS, Constants.company_details,sharedPrefsManager.getAccessToken());

        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void responseHandler(Object obj, int i) {

        if(i == Constants.company_details) {

            JSONObject response = (JSONObject) obj;
            Log.e("response", "responseHandlerCompany: " + response);
            if  (response!=null)
            {
                Intent intent = new Intent(getApplicationContext(), PrivateLimitedDirectorFirstDetailsActivity.class);
                startActivity(intent);
            }

        }
    }
}