package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.activities.privatelimited.PrivateLimitedDirectorSecondDetailsActivity;
import rutherfordit.com.instasalary.activities.sp.DatePickerFragment;
import rutherfordit.com.instasalary.extras.Constants;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.Urls;
import rutherfordit.com.instasalary.extras.VolleyRequest;
import rutherfordit.com.instasalary.myinterfaces.ResponseHandler;

public class PromoterDetails extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, ResponseHandler {

    String getid;
    RelativeLayout dir_next_button;
    ImageView purplebackarrow;
    TextInputEditText dir_fullName, dir_dob, dir_address, dir_pan, dir_adhar, dir_addressproof, dir_email, dir_mobile;
    Spinner dir_addressProofSpinner;
    boolean click = false;
    VolleyRequest volleyRequest;
    SharedPrefsManager sharedPrefsManager;
    TextView invalidDirEmail, invalidDirPan, detailsTextView;

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
        purplebackarrow = findViewById(R.id.purplebackarrow);
        dir_email = findViewById(R.id.dir_email);
        dir_mobile = findViewById(R.id.dir_mobile);
        detailsTextView = findViewById(R.id.detailsTextView);

        invalidDirEmail = findViewById(R.id.invalidDirPan);
        invalidDirPan = findViewById(R.id.invalidDirPan);

        volleyRequest = new VolleyRequest();
        sharedPrefsManager = new SharedPrefsManager(getApplicationContext());


        if(sharedPrefsManager.getSegment().equals("1")){

            detailsTextView.setText("Proprietor Details");
        }
        else {
            detailsTextView.setText("Promoter Details");
        }
        onClicks();

    }

    private void onClicks() {

        dir_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
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
                        && (dir_mobile.getText().toString().length() == 10)) {
                    dir_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;

                    invalidDirEmail.setVisibility(View.VISIBLE);
                } else {
                    dir_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
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
                        && (dir_mobile.getText().toString().length() == 10)) {
                    dir_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;

                    invalidDirEmail.setVisibility(View.VISIBLE);
                } else {
                    dir_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
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
                            && (dir_mobile.getText().toString().length() == 10)) {
                        dir_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                        click = true;

                    } else {
                        dir_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                        click = false;
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
                        && (dir_mobile.getText().toString().length() == 10)) {
                    dir_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;

                    invalidDirEmail.setVisibility(View.VISIBLE);
                } else {
                    dir_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
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
                                && (dir_mobile.getText().toString().length() == 10)) {
                            dir_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                            click = true;

                            invalidDirEmail.setVisibility(View.VISIBLE);
                        } else {
                            dir_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                            click = false;
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
                                && (dir_mobile.getText().toString().length() == 10)) {
                            dir_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                            click = true;

                            invalidDirEmail.setVisibility(View.VISIBLE);
                        } else {
                            dir_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                            click = false;
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
                        Log.e("onClick", "onClick: " +  dir_fullName.getText().toString());

                        jsonObjectBody.put("pan_card", dir_pan.getText().toString());
                        Log.e("onClick", "onClick: " +  dir_pan.getText().toString());

                        jsonObjectBody.put("aadhar", dir_adhar.getText().toString());
                        Log.e("onClick", "onClick: " +  dir_adhar.getText().toString());

                        jsonObjectBody.put("dob", dir_dob.getText().toString());
                        Log.e("onClick", "onClick: " +  dir_dob.getText().toString());

                        jsonObjectBody.put("email", dir_email.getText().toString());
                        Log.e("onClick", "onClick: " +  dir_email.getText().toString());

                        jsonObjectBody.put("current_address", dir_address.getText().toString());
                        Log.e("onClick", "onClick: " +  dir_address.getText().toString());

                        jsonObjectBody.put("current_address_proof", dir_addressproof.getText().toString());
                        Log.e("onClick", "onClick: " +  dir_addressproof.getText().toString());

                        jsonObjectBody.put("mobile_number", dir_mobile.getText().toString());
                        Log.e("onClick", "onClick: " +  dir_mobile.getText().toString());

                        jsonObjectBody.put("user_type", sharedPrefsManager.getSegment());

                        jsonObjectBody.put("dirct_part_type", getid);

//                        jsonObjectBody.put("user_type", sharedPrefsManager.getSegment());

                        jsonObjectBody.put("company_id", "1");

//                        jsonObjectBody.put("name", "nikhil");
//                        jsonObjectBody.put("pan_card", "PPPPH1234F");
//                        jsonObjectBody.put("aadhar", "123456789098");
//                        jsonObjectBody.put("dob", "1999-12-23");
//                        jsonObjectBody.put("email", "PPPPH1234F@fsdf.com");
//                        jsonObjectBody.put("current_address", "PPPPH1234F");
//                        jsonObjectBody.put("current_address_proof", "PPPPH1234F");
//                        jsonObjectBody.put("mobile_number", "9090909090");
//                        jsonObjectBody.put("user_type", "1");
//                        jsonObjectBody.put("dirct_part_type", "2");
//                        jsonObjectBody.put("company_id", "2");

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
                && (dir_mobile.getText().toString().length() == 10)) {
            dir_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
            click = true;

            invalidDirEmail.setVisibility(View.VISIBLE);
        } else {
            dir_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
            click = false;
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
                if(!getid.equals("")){
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result",getid);
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
                /*else if (getid.equals("3") || getid.equals("4")){
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("result",getid);
                    setResult(Activity.RESULT_CANCELED,returnIntent);
                    finish();
                }*/

            }
        }
    }
}