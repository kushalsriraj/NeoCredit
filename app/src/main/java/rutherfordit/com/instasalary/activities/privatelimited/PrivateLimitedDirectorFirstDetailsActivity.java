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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.activities.sp.DatePickerFragment;
import rutherfordit.com.instasalary.activities.sp.SoleProprietorshipCompanyDetailsActivity;
import rutherfordit.com.instasalary.extras.Constants;
import rutherfordit.com.instasalary.extras.ResponseHandler;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.Urls;
import rutherfordit.com.instasalary.extras.VolleyRequest;

public class PrivateLimitedDirectorFirstDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, ResponseHandler {

    RelativeLayout next1_button;
    ImageView purplebackarrow;
    TextInputEditText pl_dir1_fullName, pl_dob, pl_dir1_address, pl_dir1_pan, pl_dir1_adhar, pl_dir1_addressproof, pl_DIN_Number,pl_dir1_mobile;
    Spinner pl_dir1_addressProofSpinner;
    boolean click = false;
    VolleyRequest volleyRequest;
    SharedPrefsManager sharedPrefsManager;
    TextView invalidText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_limited_director_first_details);


        init();


    }

    private void init() {
        pl_dir1_fullName = findViewById(R.id.pl_dir1_fullName);
        pl_dob = findViewById(R.id.pl_dob);
        pl_dir1_address = findViewById(R.id.pl_dir1_address);
        pl_dir1_pan = findViewById(R.id.pl_dir1_pan);
        pl_dir1_adhar = findViewById(R.id.pl_dir1_adhar);
        pl_dir1_addressproof = findViewById(R.id.pl_dir1_addressproof);
        pl_dir1_addressProofSpinner = findViewById(R.id.pl_dir1_addressProofSpinner);
        next1_button = findViewById(R.id.next1_button);
        purplebackarrow = findViewById(R.id.purplebackarrow);
        pl_DIN_Number = findViewById(R.id.pl_DIN_Number);
        pl_dir1_mobile = findViewById(R.id.pl_dir1_mobile);

        volleyRequest = new VolleyRequest();
        sharedPrefsManager = new SharedPrefsManager(getApplicationContext());

        onClicks();

    }

    private void onClicks() {

        pl_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        pl_dir1_fullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                Matcher matcher = pattern.matcher(pl_dir1_pan.getText().toString());
                if (!pl_dir1_fullName.getText().toString().equals("") &&
                        !pl_dob.getText().toString().equals("") &&
                        !pl_dir1_address.getText().toString().equals("") &&
                        pl_dir1_pan.getText().toString().length() == 10 &&
                        pl_dir1_adhar.getText().toString().length() == 12 &&
                        !pl_DIN_Number.getText().toString().equals("") &&
                        pl_dir1_mobile.getText().toString().length() == 10 && !matcher.matches() &&
                        !pl_dir1_addressproof.getText().toString().equals("-- Select Address Proof --")) {
                    next1_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;

                    invalidText.setVisibility(View.VISIBLE);
                } else {
                    next1_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                    invalidText.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pl_dir1_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                Matcher matcher = pattern.matcher(pl_dir1_pan.getText().toString());
                if (!pl_dir1_fullName.getText().toString().equals("") &&
                        !pl_dob.getText().toString().equals("") &&
                        !pl_dir1_address.getText().toString().equals("") &&
                        pl_dir1_pan.getText().toString().length() == 10 &&
                        pl_dir1_adhar.getText().toString().length() == 12 &&
                        !pl_DIN_Number.getText().toString().equals("") &&
                        pl_dir1_mobile.getText().toString().length() == 10 && !matcher.matches() &&
                        !pl_dir1_addressproof.getText().toString().equals("-- Select Address Proof --")) {
                    next1_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;

                    invalidText.setVisibility(View.VISIBLE);
                } else {
                    next1_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                    invalidText.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pl_dir1_pan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                Matcher matcher = pattern.matcher(pl_dir1_pan.getText().toString());
                if (!pl_dir1_fullName.getText().toString().equals("") &&
                        !pl_dob.getText().toString().equals("") &&
                        !pl_dir1_address.getText().toString().equals("") &&
                        pl_dir1_pan.getText().toString().length() == 10 &&
                        pl_dir1_adhar.getText().toString().length() == 12 &&
                        !pl_DIN_Number.getText().toString().equals("") &&
                        pl_dir1_mobile.getText().toString().length() == 10 && !matcher.matches() &&
                        !pl_dir1_addressproof.getText().toString().equals("-- Select Address Proof --")) {
                    next1_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;

                    invalidText.setVisibility(View.VISIBLE);
                } else {
                    next1_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pl_dir1_adhar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                Matcher matcher = pattern.matcher(pl_dir1_pan.getText().toString());
                if (!pl_dir1_fullName.getText().toString().equals("") &&
                        !pl_dob.getText().toString().equals("") &&
                        !pl_dir1_address.getText().toString().equals("") &&
                        pl_dir1_pan.getText().toString().length() == 10 &&
                        pl_dir1_adhar.getText().toString().length() == 12 &&
                        !pl_DIN_Number.getText().toString().equals("") &&
                        pl_dir1_mobile.getText().toString().length() == 10 && !matcher.matches() &&
                        !pl_dir1_addressproof.getText().toString().equals("-- Select Address Proof --")) {
                    next1_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;

                    invalidText.setVisibility(View.VISIBLE);
                } else {
                    next1_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pl_dir1_addressproof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pl_dir1_addressProofSpinner.performClick();
            }
        });

        pl_dir1_addressProofSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                        Matcher matcher = pattern.matcher(pl_dir1_pan.getText().toString());
                        // String addressProof = parent.getItemAtPosition(pos).toString();

                        pl_dir1_addressproof.setText(parent.getItemAtPosition(pos).toString());

                        if (!pl_dir1_fullName.getText().toString().equals("") &&
                                !pl_dob.getText().toString().equals("") &&
                                !pl_dir1_address.getText().toString().equals("") &&
                                pl_dir1_pan.getText().toString().length() == 10 &&
                                pl_dir1_adhar.getText().toString().length() == 12 &&
                                !pl_DIN_Number.getText().toString().equals("") &&
                                pl_dir1_mobile.getText().toString().length() == 10 && !matcher.matches() &&
                                !pl_dir1_addressproof.getText().toString().equals("-- Select Address Proof --")) {
                            next1_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                            click = true;

                            invalidText.setVisibility(View.VISIBLE);
                        } else {
                            next1_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                            click = false;
                        }

                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                        Matcher matcher = pattern.matcher(pl_dir1_pan.getText().toString());
                        if (!pl_dir1_fullName.getText().toString().equals("") &&
                                !pl_dob.getText().toString().equals("") &&
                                !pl_dir1_address.getText().toString().equals("") &&
                                pl_dir1_pan.getText().toString().length() == 10 &&
                                pl_dir1_adhar.getText().toString().length() == 12 &&
                                !pl_DIN_Number.getText().toString().equals("") &&
                                pl_dir1_mobile.getText().toString().length() == 10 && !matcher.matches() &&
                                !pl_dir1_addressproof.getText().toString().equals("-- Select Address Proof --")) {
                            next1_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                            click = true;

                            invalidText.setVisibility(View.VISIBLE);
                        } else {
                            next1_button.setBackgroundColor(getResources().getColor(R.color.colorash));
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

        next1_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click) {

                    JSONObject jsonObjectBody = new JSONObject();

                    try {
                        jsonObjectBody.put("name", pl_dir1_fullName.getText().toString());
                        Log.e("onClick", "onClick: " +  pl_dir1_fullName.getText().toString());

                        jsonObjectBody.put("pan_card", pl_dir1_pan.getText().toString());
                        Log.e("onClick", "onClick: " +  pl_dir1_pan.getText().toString());
                        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                        Matcher matcher = pattern.matcher(pl_dir1_pan.getText().toString());
                        // Check if pattern matches
                        if (matcher.matches()) {
                            Log.i("Matching","Yes");
                        }
                        else {
                            Log.i("Not-Matching","No");
                        }

                        jsonObjectBody.put("aadhar", pl_dir1_adhar.getText().toString());
                        Log.e("onClick", "onClick: " +  pl_dir1_adhar.getText().toString());

                        jsonObjectBody.put("dob", pl_dob.getText().toString());
                        Log.e("onClick", "onClick: " +  pl_dob.getText().toString());

                        jsonObjectBody.put("din", pl_DIN_Number.getText().toString());
                        Log.e("onClick", "onClick: " +  pl_DIN_Number.getText().toString());

                        jsonObjectBody.put("current_address", pl_dir1_address.getText().toString());
                        Log.e("onClick", "onClick: " +  pl_dir1_address.getText().toString());

                        jsonObjectBody.put("current_address_proof", pl_dir1_addressproof.getText().toString());
                        Log.e("onClick", "onClick: " +  pl_dir1_addressproof.getText().toString());

                        jsonObjectBody.put("mobile_number", pl_dir1_mobile.getText().toString());
                        Log.e("onClick", "onClick: " +  pl_dir1_mobile.getText().toString());

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

                        volleyRequest.JsonObjRequestAuthorization(PrivateLimitedDirectorFirstDetailsActivity.this,jsonObjectBody, Urls.DIRECTOR_DETAILS, Constants.director_details,sharedPrefsManager.getAccessToken());

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
        super.onBackPressed();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        //  String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
        Matcher matcher = pattern.matcher(pl_dir1_pan.getText().toString());

        pl_dob.setText(year + "-" + (month + 1) + "-" + (dayOfMonth));

        if (!pl_dir1_fullName.getText().toString().equals("") &&
                !pl_dob.getText().toString().equals("") &&
                !pl_dir1_address.getText().toString().equals("") &&
                pl_dir1_pan.getText().toString().length() == 10 &&
                pl_dir1_adhar.getText().toString().length() == 12 &&
                !pl_DIN_Number.getText().toString().equals("") &&
                pl_dir1_mobile.getText().toString().length() == 10 && !matcher.matches() &&
                !pl_dir1_addressproof.getText().toString().equals("-- Select Address Proof --")) {
            next1_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
            click = true;

            invalidText.setVisibility(View.VISIBLE);
        } else {
            next1_button.setBackgroundColor(getResources().getColor(R.color.colorash));
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
                Intent intent = new Intent(getApplicationContext(), PrivateLimitedDirectorSecondDetailsActivity.class);
                startActivity(intent);
            }

        }
    }
}