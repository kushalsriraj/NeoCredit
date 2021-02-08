package rutherfordit.com.instasalary.activities.partnership;

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
import rutherfordit.com.instasalary.activities.privatelimited.PrivateLimitedDirectorFirstDetailsActivity;
import rutherfordit.com.instasalary.activities.privatelimited.PrivateLimitedDirectorSecondDetailsActivity;
import rutherfordit.com.instasalary.activities.sp.DatePickerFragment;
import rutherfordit.com.instasalary.extras.Constants;
import rutherfordit.com.instasalary.extras.ResponseHandler;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.Urls;
import rutherfordit.com.instasalary.extras.VolleyRequest;

public class PartnershipPartnerFirstDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, ResponseHandler {

    RelativeLayout par1_next_button;
    ImageView purplebackarrow;
    TextInputEditText par_dir1_fullName, par_dob, par_dir1_address, par_dir1_pan, par_dir1_adhar, par_dir1_addressproof, par_DIN_Number, par_dir1_mobile;
    Spinner par_dir1_addressProofSpinner;
    boolean click = false;
    VolleyRequest volleyRequest;
    SharedPrefsManager sharedPrefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnership_first_partner_details);


        init();


    }

    private void init() {
        par_dir1_fullName = findViewById(R.id.par_dir1_fullName);
        par_dob = findViewById(R.id.par_dob);
        par_dir1_address = findViewById(R.id.par_dir1_address);
        par_dir1_pan = findViewById(R.id.par_dir1_pan);
        par_dir1_adhar = findViewById(R.id.par_dir1_adhar);
        par_dir1_addressproof = findViewById(R.id.par_dir1_addressproof);
        par_dir1_addressProofSpinner = findViewById(R.id.par_dir1_addressProofSpinner);
        par1_next_button = findViewById(R.id.par1_next_button);
        purplebackarrow = findViewById(R.id.purplebackarrow);
        par_DIN_Number = findViewById(R.id.par_DIN_Number);
        par_dir1_mobile = findViewById(R.id.par_dir1_mobile);

        volleyRequest = new VolleyRequest();
        sharedPrefsManager = new SharedPrefsManager(getApplicationContext());

        onClicks();

    }

    private void onClicks() {

        par_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        par_dir1_fullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!par_dir1_fullName.getText().toString().equals("") &&
                        !par_dob.getText().toString().equals("") &&
                        !par_dir1_address.getText().toString().equals("") &&
                        par_dir1_pan.getText().toString().length() == 10 &&
                        par_dir1_adhar.getText().toString().length() == 12 &&
                        !par_DIN_Number.getText().toString().equals("") &&
                        par_dir1_mobile.getText().toString().length() == 10 &&
                        !par_dir1_addressproof.getText().toString().equals("-- Select Address Proof --"))

                {
                    par1_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));

                    click = true;
                } else {
                    par1_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        par_dir1_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!par_dir1_fullName.getText().toString().equals("") &&
                        !par_dob.getText().toString().equals("") &&
                        !par_dir1_address.getText().toString().equals("") &&
                        par_dir1_pan.getText().toString().length() == 10 &&
                        par_dir1_adhar.getText().toString().length() == 12 &&
                        !par_DIN_Number.getText().toString().equals("") &&
                        par_dir1_mobile.getText().toString().length() == 10 &&
                        !par_dir1_addressproof.getText().toString().equals("-- Select Address Proof --")) {
                    par1_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                } else {
                    par1_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        par_dir1_pan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!par_dir1_fullName.getText().toString().equals("") &&
                        !par_dob.getText().toString().equals("") &&
                        !par_dir1_address.getText().toString().equals("") &&
                        par_dir1_pan.getText().toString().length() == 10 &&
                        par_dir1_adhar.getText().toString().length() == 12 &&
                        !par_DIN_Number.getText().toString().equals("") &&
                        par_dir1_mobile.getText().toString().length() == 10 &&
                        !par_dir1_addressproof.getText().toString().equals("-- Select Address Proof --")) {
                    par1_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                } else {
                    par1_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        par_dir1_adhar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!par_dir1_fullName.getText().toString().equals("") &&
                        !par_dob.getText().toString().equals("") &&
                        !par_dir1_address.getText().toString().equals("") &&
                        par_dir1_pan.getText().toString().length() == 10 &&
                        par_dir1_adhar.getText().toString().length() == 12 &&
                        !par_DIN_Number.getText().toString().equals("") &&
                        par_dir1_mobile.getText().toString().length() == 10 &&
                        !par_dir1_addressproof.getText().toString().equals("-- Select Address Proof --")) {
                    par1_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                } else {
                    par1_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        par_dir1_addressproof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                par_dir1_addressProofSpinner.performClick();
            }
        });

        par_dir1_addressProofSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                        // String addressProof = parent.getItemAtPosition(pos).toString();

                        par_dir1_addressproof.setText(parent.getItemAtPosition(pos).toString());

                        if (!par_dir1_fullName.getText().toString().equals("") &&
                                !par_dob.getText().toString().equals("") &&
                                !par_dir1_address.getText().toString().equals("") &&
                                par_dir1_pan.getText().toString().length() == 10 &&
                                par_dir1_adhar.getText().toString().length() == 12 &&
                                !par_DIN_Number.getText().toString().equals("") &&
                                par_dir1_mobile.getText().toString().length() == 10 &&
                                !par_dir1_addressproof.getText().toString().equals("-- Select Address Proof --")) {
                            par1_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                            click = true;
                        } else {
                            par1_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                            click = false;
                        }

                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                        if (!par_dir1_fullName.getText().toString().equals("") &&
                                !par_dob.getText().toString().equals("") &&
                                !par_dir1_address.getText().toString().equals("") &&
                                par_dir1_pan.getText().toString().length() == 10 &&
                                par_dir1_adhar.getText().toString().length() == 12 &&
                                !par_DIN_Number.getText().toString().equals("") &&
                                par_dir1_mobile.getText().toString().length() == 10 &&
                                !par_dir1_addressproof.getText().toString().equals("-- Select Address Proof --")) {
                            par1_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                            click = true;
                        } else {
                            par1_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
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

        par1_next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click) {

                    JSONObject jsonObjectBody = new JSONObject();

                    try {
                        jsonObjectBody.put("name", par_dir1_fullName.getText().toString());
                        Log.e("onClick", "onClick: " +  par_dir1_fullName.getText().toString());

                        jsonObjectBody.put("pan_card", par_dir1_pan.getText().toString());
                        Log.e("onClick", "onClick: " +  par_dir1_pan.getText().toString());
                        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                        Matcher matcher = pattern.matcher(par_dir1_pan.getText().toString());
                        // Check if pattern matches
                        if (matcher.matches()) {
                            Log.i("Matching","Yes");
                        }
                        else {
                            Log.i("Not-Matching","No");
                        }

                        jsonObjectBody.put("aadhar", par_dir1_adhar.getText().toString());
                        Log.e("onClick", "onClick: " +  par_dir1_adhar.getText().toString());

                        jsonObjectBody.put("dob", par_dob.getText().toString());
                        Log.e("onClick", "onClick: " +  par_dob.getText().toString());

                        jsonObjectBody.put("din", par_DIN_Number.getText().toString());
                        Log.e("onClick", "onClick: " +  par_DIN_Number.getText().toString());

                        jsonObjectBody.put("current_address", par_dir1_address.getText().toString());
                        Log.e("onClick", "onClick: " +  par_dir1_address.getText().toString());

                        jsonObjectBody.put("current_address_proof", par_dir1_addressproof.getText().toString());
                        Log.e("onClick", "onClick: " +  par_dir1_addressproof.getText().toString());

                        jsonObjectBody.put("mobile_number", par_dir1_mobile.getText().toString());
                        Log.e("onClick", "onClick: " +  par_dir1_mobile.getText().toString());

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

                        volleyRequest.JsonObjRequestAuthorization(PartnershipPartnerFirstDetailsActivity.this,jsonObjectBody, Urls.DIRECTOR_DETAILS, Constants.director_details,sharedPrefsManager.getAccessToken());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Please Check All The Fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

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

        par_dob.setText(year + "-" + (month + 1) + "-" + dayOfMonth);

        if (!par_dir1_fullName.getText().toString().equals("") &&
                !par_dob.getText().toString().equals("") &&
                !par_dir1_address.getText().toString().equals("") &&
                par_dir1_pan.getText().toString().length() == 10 &&
                par_dir1_adhar.getText().toString().length() == 12 &&
                !par_DIN_Number.getText().toString().equals("") &&
                par_dir1_mobile.getText().toString().length() == 10 &&
                !par_dir1_addressproof.getText().toString().equals("-- Select Address Proof --")) {
            par1_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
            click = true;
        } else {
            par1_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
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
                Intent intent = new Intent(getApplicationContext(), PartnershipPartnerSecondDetailsActivity.class);
                startActivity(intent);
            }

        }
    }
}