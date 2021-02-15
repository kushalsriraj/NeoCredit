package rutherfordit.com.instasalary.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
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

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;
import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.extras.Constants;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.Urls;
import rutherfordit.com.instasalary.extras.VolleyRequest;
import rutherfordit.com.instasalary.myinterfaces.ResponseHandler;

public class CompanyDetails extends AppCompatActivity implements ResponseHandler {

    List<Address> addresses;
    Geocoder geocoder;
    private static final String TAG = "company";
    String apikey = "AIzaSyAd8dm_HBq2pzHhVvzzD9dqBwR3K88jCEk";
    PlacesClient placesClient;
    TextView invalidPan, invalidEmail;
    SharedPrefsManager sharedPrefsManager;
    VolleyRequest volleyRequest;
    RelativeLayout submitCompanyInfo;
    ImageView purplebackarrow;
    TextInputEditText businessLandline, company_name, typeOfService, howOldIsTheCompany, annualTurnover, company_address, addressProof, company_email, pancardnumber, mobile_number;
    Spinner Spinner_typeOfService, Spinner_howOldIsTheCompany, Spinner_annualTurnover, Spinner_businessAddressProof;
    boolean click = false;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;
    private String addressProofNumber;
    private String annualTurnOverNumber;
    private String howOldIsTheCompanyNumber;
    TextInputLayout panCardLayput, mobile_number_company;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK)
            {
                Place place = null;
                if (data != null)
                {
                    place = Autocomplete.getPlaceFromIntent(data);
                }
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + place.getLatLng() + place.getAddress());
                /*Select_Place.setVisibility(View.GONE);
                tip_search.setVisibility(View.GONE);
                re_search.setVisibility(View.VISIBLE);*/
                // Select_Place.setText("Name : " + place.getName() + "\n" + "LatLng" + place.getLatLng() +"\n" + "Address" + place.getAddress()+ "Phone" + place.getPhoneNumber());

                /*entercompanyname.setText(place.getName());
                entercompanystreet.setText(place.getAddress());*/

                String latlng = String.valueOf(place.getLatLng());

                latlng = latlng.replace("(", "");
                latlng = latlng.replace(")", "");
                latlng = latlng.replace("lat/lng: ", "");

                String[] namesList = latlng.split(",");

                String lat = namesList[0];
                String longi = namesList[1];

                Log.e(TAG, "lat " + lat + " long " + longi);


                double la=Double.parseDouble(lat.toString());
                double lo=Double.parseDouble(longi.toString());

                geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocation(la, lo, 1);

                    String postalCode = addresses.get(0).getPostalCode();
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();

                    /*entercompanypincode.setText(postalCode);
                    entercompanystate.setText(state);
                    entercompanycity.setText(city);*/

                    Log.e(TAG, "onActivityResult: " + postalCode + " " + city+ " " + state );
                } catch (IOException e) {
                    e.printStackTrace();
                }

               // company_Data_Layout.setVisibility(View.VISIBLE);


            }
            else if (resultCode == AutocompleteActivity.RESULT_ERROR) {

                Status status = Autocomplete.getStatusFromIntent(data);

                if (status.getStatusMessage() != null)
                {
                    Log.e(TAG, status.getStatusMessage());
                }
            } else if (resultCode == RESULT_CANCELED)
            {
                Log.e(TAG, "cancelled");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_details);

        init();

    }

    private void init()
    {

        volleyRequest = new VolleyRequest();
        Places.initialize(getApplicationContext(), apikey);
        placesClient = Places.createClient(this);
        invalidPan = findViewById(R.id.invalidPan);
        invalidEmail = findViewById(R.id.invalidEmail);
        invalidPan.setVisibility(View.GONE);
        invalidEmail.setVisibility(View.GONE);
        businessLandline = findViewById(R.id.sp_businessLandline);
        company_email = findViewById(R.id.profile1_company_email);
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
        mobile_number = findViewById(R.id.mobile_number);

        panCardLayput = findViewById(R.id.panCardLayput);
        mobile_number_company = findViewById(R.id.mobile_number_company);

        submitCompanyInfo = findViewById(R.id.submitCompanyInfo);
        purplebackarrow = findViewById(R.id.purplebackarrow);

        sharedPrefsManager = new SharedPrefsManager(getApplicationContext());

        /*company_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.PHONE_NUMBER,Place.Field.ADDRESS);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(getApplicationContext());
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });*/


        if (sharedPrefsManager.getSegment() == "1"){

            panCardLayput.setVisibility(View.GONE);
            mobile_number_company.setVisibility(View.GONE);
        }


        onClicks();
        SpinnersSelected();
        textWatchers();
    }

    private void textWatchers()
    {

        mobile_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!company_name.getText().toString().equals("")
                        &&!company_email.getText().toString().equals("")
//                        && (mobile_number.getText().toString().length() == 10)
//                        && !pancardnumber.getText().toString().equals("")
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

        company_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!company_name.getText().toString().equals("")
                        &&!company_email.getText().toString().equals("")
//                        && (mobile_number.getText().toString().length() == 10)
//                        && !pancardnumber.getText().toString().equals("")
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

                if (Patterns.EMAIL_ADDRESS.matcher(company_email.getText().toString()).matches())
                {
                    invalidEmail.setVisibility(View.GONE);

                    if (!company_name.getText().toString().equals("")
                            && !company_email.getText().toString().equals("")
//                            && (mobile_number.getText().toString().length() == 10)
//                            && !pancardnumber.getText().toString().equals("")
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
                    invalidEmail.setVisibility(View.VISIBLE);
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

//                    if (!company_name.getText().toString().equals("")
//                            && !company_email.getText().toString().equals("")
//                            && !pancardnumber.getText().toString().equals("")
//                            && (mobile_number.getText().toString().length() == 10)
//                            &&!company_address.getText().toString().equals("")
//                            &&!typeOfService.getText().toString().equals("-- Select Type of Service --")
//                            &&!howOldIsTheCompany.getText().toString().equals("-- Select Tenuraty the Company --")
//                            && !annualTurnover.getText().toString().equals("-- Select Annual Turnover --")&&
//                            !addressProof.getText().toString().equals("-- Select Address Proof --"))
//                    {
//                        submitCompanyInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));
//                        click=true;
//                    }
//                    else {
//                        submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
//                        click=false;
//                    }

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
//                        && (mobile_number.getText().toString().length() == 10)
//                        && !pancardnumber.getText().toString().equals("")
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
//                        && (mobile_number.getText().toString().length() == 10)
//                        && !pancardnumber.getText().toString().equals("")
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
//                        && (mobile_number.getText().toString().length() == 10)
//                        && !pancardnumber.getText().toString().equals("")
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

                if (howOldIsTheCompany.getText().toString().equals("Less than 1 year"))
                {
                    howOldIsTheCompanyNumber = "1";
                }
                else if (howOldIsTheCompany.getText().toString().equals("1 -3 years"))
                {
                    howOldIsTheCompanyNumber = "2";
                }
                else if (howOldIsTheCompany.getText().toString().equals("More than 3 years"))
                {
                    howOldIsTheCompanyNumber = "3";
                }

                if (!company_name.getText().toString().equals("")
                        &&!company_email.getText().toString().equals("")
//                        && (mobile_number.getText().toString().length() == 10)
//                        && !pancardnumber.getText().toString().equals("")
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

                if (annualTurnover.getText().toString().equals("1lakhs - 50 lakhs"))
                {
                    annualTurnOverNumber = "1";
                }
                else if (annualTurnover.getText().toString().equals("50lakhs  - 1 crore"))
                {
                    annualTurnOverNumber = "2";
                }
                else if (annualTurnover.getText().toString().equals("More than 50 crores"))
                {
                    annualTurnOverNumber = "3";
                }

                if (!company_name.getText().toString().equals("")
                        &&!company_email.getText().toString().equals("")
//                        && (mobile_number.getText().toString().length() == 10)
//                        && !pancardnumber.getText().toString().equals("")
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

                if (addressProof.getText().toString().equals("Current Bill"))
                {
                    addressProofNumber = "1";
                }
                else if (addressProof.getText().toString().equals("Landline Bill"))
                {
                    addressProofNumber = "2";
                }
                else if (addressProof.getText().toString().equals("Utility Bill"))
                {
                    addressProofNumber = "3";
                }

                if (!company_name.getText().toString().equals("")
                        &&!company_email.getText().toString().equals("")
//                        && (mobile_number.getText().toString().length() == 10)
//                        && !pancardnumber.getText().toString().equals("")
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

    private void onClicks() {

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
                    if (sharedPrefsManager.getSegment().equals("1")) {

                        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                        Matcher matcher = pattern.matcher(pancardnumber.getText().toString());

                        if (matcher.matches())
                        {
                            invalidPan.setVisibility(View.GONE);
                            companyAPI();
                        } else
                            {
                            companyAPI();
                            Toasty.warning(getApplicationContext(), "Pan format invalid", Toasty.LENGTH_LONG).show();
                        }
                    }
                } else
                    {
                    Toasty.error(getApplicationContext(), "Please Check the fields.", Toasty.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void companyAPI() {
        String pan_dummy;
        JSONObject jsonObjectBody = new JSONObject();

        if (sharedPrefsManager.getSegment() == "1") {

            pan_dummy = "ABCDE1234F";
        }
        else {
            pan_dummy = pancardnumber.getText().toString();
        }
            try {

                jsonObjectBody.put("name", company_name.getText().toString());
                jsonObjectBody.put("pan_card", pan_dummy);
                jsonObjectBody.put("type_of_services", typeOfService.getText().toString());
                jsonObjectBody.put("business_reg_num", "1999-12-23");
                jsonObjectBody.put("business_addr_proof", addressProofNumber);
                jsonObjectBody.put("landline_number", businessLandline.getText().toString());
                jsonObjectBody.put("mobile_number", sharedPrefsManager.getPhoneNumber());
                jsonObjectBody.put("orgize_age", howOldIsTheCompanyNumber);
                jsonObjectBody.put("email", company_email.getText().toString());
                jsonObjectBody.put("company_address", company_address.getText().toString());
                jsonObjectBody.put("annual_turnover", annualTurnOverNumber);

                Log.e("comapanyAPI", "comapanyAPI: " + jsonObjectBody);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        volleyRequest.JsonObjRequestAuthorization(CompanyDetails.this,jsonObjectBody, Urls.COMPANY_DETAILS, Constants.company_details,sharedPrefsManager.getAccessToken());

    }

    @Override
    public void responseHandler(Object obj, int i) {

        if(i == Constants.company_details) {

            JSONObject response = (JSONObject) obj;
//            if  (response!=null)
//            {

                try {
                    JSONObject jsonObjectData = response.getJSONObject("data");

                    JSONObject jsonObjectCompanyDetails = jsonObjectData.getJSONObject("companydetails");

                    JSONArray arrayData = jsonObjectCompanyDetails.getJSONArray("data");
                    for (int j = 0; j < arrayData.length(); j ++){

                        JSONObject idObj = arrayData.getJSONObject(j);

                        String id = idObj.getString("id");

                        sharedPrefsManager.setCOMPANY_ID(id);
                        Log.e("COMPANY_ID", "COMPANY_ID: " + sharedPrefsManager.getCOMPANY_ID() );
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (sharedPrefsManager.getSegment().equals("1"))
                    {
                        Intent intent = new Intent(getApplicationContext(), PromoterDetails.class);
                        intent.putExtra("id","1");
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(getApplicationContext(), DirectorDetails.class);
                        startActivity(intent);
                    }
            //}
        }
    }
}