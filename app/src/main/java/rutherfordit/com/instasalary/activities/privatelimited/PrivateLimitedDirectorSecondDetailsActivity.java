package rutherfordit.com.instasalary.activities.privatelimited;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.activities.sp.DatePickerFragment;
import rutherfordit.com.instasalary.extras.Constants;
import rutherfordit.com.instasalary.myinterfaces.ResponseHandler;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.Urls;
import rutherfordit.com.instasalary.extras.VolleyRequest;

public class PrivateLimitedDirectorSecondDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, ResponseHandler {

    RelativeLayout next2_button;
    ImageView purplebackarrow;
    TextInputEditText pl_dir2_fullName, pl_2_dob, pl_dir2_address, pl_dir2_pan, pl_dir2_adhar, pl_dir2_addressproof, pl_DIN2_Number, pl_dir2_mobile;
    Spinner pl_dir2_addressProofSpinner;
    boolean click = false;
    VolleyRequest volleyRequest;
    SharedPrefsManager sharedPrefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_limited_director_second_details);


        init();


    }

    private void init() {
        pl_dir2_fullName = findViewById(R.id.pl_dir2_fullName);
        pl_2_dob = findViewById(R.id.pl_2_dob);
        pl_dir2_address = findViewById(R.id.pl_dir2_address);
        pl_dir2_pan = findViewById(R.id.pl_dir2_pan);
        pl_dir2_adhar = findViewById(R.id.pl_dir2_adhar);
        pl_dir2_addressproof = findViewById(R.id.pl_dir2_addressproof);
        pl_dir2_addressProofSpinner = findViewById(R.id.pl_dir2_addressProofSpinner);
        next2_button = findViewById(R.id.next2_button);
        purplebackarrow = findViewById(R.id.purplebackarrow);
        pl_DIN2_Number = findViewById(R.id.pl_DIN2_Number);
        pl_dir2_mobile = findViewById(R.id.pl_dir2_mobile);

        volleyRequest = new VolleyRequest();
        sharedPrefsManager = new SharedPrefsManager(getApplicationContext());

        onClicks();

    }

    private void onClicks() {

        pl_2_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        pl_dir2_fullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!pl_dir2_fullName.getText().toString().equals("") &&
                        !pl_2_dob.getText().toString().equals("") &&
                        !pl_dir2_address.getText().toString().equals("") &&
                        pl_dir2_pan.getText().toString().length() == 10 &&
                        pl_dir2_adhar.getText().toString().length() == 12 &&
                        !pl_DIN2_Number.getText().toString().equals("") &&
                        pl_dir2_mobile.getText().toString().length() == 10 &&
                        !pl_dir2_addressproof.getText().toString().equals("-- Select Address Proof --"))

                {
                    next2_button.setBackground(getDrawable(R.drawable.gradient_neocredit));

                    click = true;
                } else {
                    next2_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pl_dir2_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!pl_dir2_fullName.getText().toString().equals("") &&
                        !pl_2_dob.getText().toString().equals("") &&
                        !pl_dir2_address.getText().toString().equals("") &&
                        pl_dir2_pan.getText().toString().length() == 10 &&
                        pl_dir2_adhar.getText().toString().length() == 12 &&
                        !pl_DIN2_Number.getText().toString().equals("") &&
                        pl_dir2_mobile.getText().toString().length() == 10 &&
                        !pl_dir2_addressproof.getText().toString().equals("-- Select Address Proof --")) {
                    next2_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                } else {
                    next2_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pl_dir2_pan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!pl_dir2_fullName.getText().toString().equals("") &&
                        !pl_2_dob.getText().toString().equals("") &&
                        !pl_dir2_address.getText().toString().equals("") &&
                        pl_dir2_pan.getText().toString().length() == 10 &&
                        pl_dir2_adhar.getText().toString().length() == 12 &&
                        !pl_DIN2_Number.getText().toString().equals("") &&
                        pl_dir2_mobile.getText().toString().length() == 10 &&
                        !pl_dir2_addressproof.getText().toString().equals("-- Select Address Proof --")) {
                    next2_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                    Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                    Matcher matcher = pattern.matcher(pl_dir2_pan.getText().toString());
                    // Check if pattern matches
                    if (matcher.matches()) {
                        Log.i("Matching","Yes");
                    }
                    else {
                        Log.i("Not-Matching","No");
                    }
                } else {
                    next2_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pl_dir2_adhar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!pl_dir2_fullName.getText().toString().equals("") &&
                        !pl_2_dob.getText().toString().equals("") &&
                        !pl_dir2_address.getText().toString().equals("") &&
                        pl_dir2_pan.getText().toString().length() == 10 &&
                        pl_dir2_adhar.getText().toString().length() == 12 &&
                        !pl_DIN2_Number.getText().toString().equals("") &&
                        pl_dir2_mobile.getText().toString().length() == 10 &&
                        !pl_dir2_addressproof.getText().toString().equals("-- Select Address Proof --")) {
                    next2_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                } else {
                    next2_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pl_dir2_addressproof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pl_dir2_addressProofSpinner.performClick();
            }
        });

        pl_dir2_addressProofSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        // String addressProof = parent.getItemAtPosition(pos).toString();

                        pl_dir2_addressproof.setText(parent.getItemAtPosition(pos).toString());

                        if (!pl_dir2_fullName.getText().toString().equals("") &&
                                !pl_2_dob.getText().toString().equals("") &&
                                !pl_dir2_address.getText().toString().equals("") &&
                                pl_dir2_pan.getText().toString().length() == 10 &&
                                pl_dir2_adhar.getText().toString().length() == 12 &&
                                !pl_DIN2_Number.getText().toString().equals("") &&
                                pl_dir2_mobile.getText().toString().length() == 10 &&
                                !pl_dir2_addressproof.getText().toString().equals("-- Select Address Proof --")) {
                            next2_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                            click = true;
                        } else {
                            next2_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                            click = false;
                        }

                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                        if (!pl_dir2_fullName.getText().toString().equals("") &&
                                !pl_2_dob.getText().toString().equals("") &&
                                !pl_dir2_address.getText().toString().equals("") &&
                                pl_dir2_pan.getText().toString().length() == 10 &&
                                pl_dir2_adhar.getText().toString().length() == 12 &&
                                !pl_DIN2_Number.getText().toString().equals("") &&
                                pl_dir2_mobile.getText().toString().length() == 10 &&
                                !pl_dir2_addressproof.getText().toString().equals("-- Select Address Proof --")) {
                            next2_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                            click = true;
                        } else {
                            next2_button.setBackgroundColor(getResources().getColor(R.color.colorash));
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

        next2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONObject jsonObjectBody = new JSONObject();

                try {
                    jsonObjectBody.put("name", pl_dir2_fullName.getText().toString());
                    Log.e("onClick", "onClick: " +  pl_dir2_fullName.getText().toString());

                    jsonObjectBody.put("pan_card", pl_dir2_pan.getText().toString());
                    Log.e("onClick", "onClick: " +  pl_dir2_pan.getText().toString());
                    Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                    Matcher matcher = pattern.matcher(pl_dir2_pan.getText().toString());
                    // Check if pattern matches
                    if (matcher.matches()) {
                        Log.i("Matching","Yes");
                    }
                    else {
                        Log.i("Not-Matching","No");
                    }

                    jsonObjectBody.put("aadhar", pl_dir2_adhar.getText().toString());
                    Log.e("onClick", "onClick: " +  pl_dir2_adhar.getText().toString());

                    jsonObjectBody.put("dob", pl_2_dob.getText().toString());
                    Log.e("onClick", "onClick: " +  pl_2_dob.getText().toString());

                    jsonObjectBody.put("din", pl_DIN2_Number.getText().toString());
                    Log.e("onClick", "onClick: " +  pl_DIN2_Number.getText().toString());

                    jsonObjectBody.put("current_address", pl_dir2_address.getText().toString());
                    Log.e("onClick", "onClick: " +  pl_dir2_address.getText().toString());

                    jsonObjectBody.put("current_address_proof", pl_dir2_addressproof.getText().toString());
                    Log.e("onClick", "onClick: " +  pl_dir2_addressproof.getText().toString());

                    jsonObjectBody.put("mobile_number", pl_dir2_mobile.getText().toString());
                    Log.e("onClick", "onClick: " +  pl_dir2_mobile.getText().toString());

                    jsonObjectBody.put("director", "2");

                    jsonObjectBody.put("user_type", sharedPrefsManager.getSegment());
                    Log.e("onClick", "onClick: " +  sharedPrefsManager.getSegment());

//                        jsonObjectBody.put("name", "nikhil");
//                        jsonObjectBody.put("pan_card", "PPPPH1234F");
//                        jsonObjectBody.put("aadhar", "123456789098");
//                        jsonObjectBody.put("dob", "1999-12-23");
//                        jsonObjectBody.put("din", "PPPPH1234F");
//                        jsonObjectBody.put("current_address", "PPPPH1234F");
//                        jsonObjectBody.put("current_address_proof", "PPPPH1234F");
//                        jsonObjectBody.put("mobile_number", "9090909090");
//                        jsonObjectBody.put("director", "2");
//                        jsonObjectBody.put("user_type", "2");

                    volleyRequest.JsonObjRequestAuthorization(PrivateLimitedDirectorSecondDetailsActivity.this,jsonObjectBody, Urls.DIRECTOR_DETAILS, Constants.director_details,sharedPrefsManager.getAccessToken());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    //here

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        //  String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        pl_2_dob.setText(year + "-" + (month + 1) + "-" + dayOfMonth);

        if (!pl_dir2_fullName.getText().toString().equals("") &&
                !pl_2_dob.getText().toString().equals("") &&
                !pl_dir2_address.getText().toString().equals("") &&
                pl_dir2_pan.getText().toString().length() == 10 &&
                pl_dir2_adhar.getText().toString().length() == 12 &&
                !pl_DIN2_Number.getText().toString().equals("") &&
                pl_dir2_mobile.getText().toString().length() == 10 &&
                !pl_dir2_addressproof.getText().toString().equals("-- Select Address Proof --")) {
            next2_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
            click = true;
        } else {
            next2_button.setBackgroundColor(getResources().getColor(R.color.colorash));
            click = false;
        }
    }

    @Override
    public void responseHandler(Object obj, int i) {

        if (i == Constants.director_details){

            JSONObject responseDirector = (JSONObject) obj;

            Log.e("responseDirector", "responseDirector: " + responseDirector );

            Intent intent = new Intent(getApplicationContext(), PrivateLimitedDocUploadActivity.class);
            startActivity(intent);
        }
    }
}