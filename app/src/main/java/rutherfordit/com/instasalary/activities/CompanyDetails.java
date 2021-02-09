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
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
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
    TextInputEditText businessLandline, company_name, typeOfService, howOldIsTheCompany, annualTurnover, company_address, addressProof, company_email, pancardnumber;
    Spinner Spinner_typeOfService, Spinner_howOldIsTheCompany, Spinner_annualTurnover, Spinner_businessAddressProof;
    boolean click = false;
    private static int AUTOCOMPLETE_REQUEST_CODE = 1;

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

        Places.initialize(getApplicationContext(), apikey);
        placesClient = Places.createClient(this);
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

        company_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.PHONE_NUMBER,Place.Field.ADDRESS);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).build(getApplicationContext());
                startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            }
        });

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