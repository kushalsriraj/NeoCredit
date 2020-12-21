package rutherfordit.com.instasalary.activities.partnership;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.activities.privatelimited.PrivateLimitedDirectorSecondDetailsActivity;
import rutherfordit.com.instasalary.activities.sp.DatePickerFragment;

public class PartnershipPartnerFirstDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    RelativeLayout par_next_button;
    ImageView purplebackarrow;
    TextInputEditText par_dir1_fullName, par_dob, par_dir1_address, par_dir1_pan, par_dir1_adhar, par_dir1_addressproof, par_DIN_Number, par_dir1_mobile;
    Spinner par_dir1_addressProofSpinner;
    boolean click = false;

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
        par_next_button = findViewById(R.id.par_next_button);
        purplebackarrow = findViewById(R.id.purplebackarrow);
        par_DIN_Number = findViewById(R.id.par_DIN_Number);
        par_dir1_mobile = findViewById(R.id.par_dir1_mobile);


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
                            par_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                            click = true;
                        } else {
                            par_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
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
                            par_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                            click = true;
                        } else {
                            par_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
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

        par_next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click) {
                    Intent intent = new Intent(getApplicationContext(), PrivateLimitedDirectorSecondDetailsActivity.class);
                    startActivity(intent);
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

        par_dob.setText(year + "-" + (month + 1) + "-" + dayOfMonth);

        if (!par_dir1_fullName.getText().toString().equals("") &&
                !par_dob.getText().toString().equals("") &&
                !par_dir1_address.getText().toString().equals("") &&
                par_dir1_pan.getText().toString().length() == 10 &&
                par_dir1_adhar.getText().toString().length() == 12 &&
                !par_DIN_Number.getText().toString().equals("") &&
                par_dir1_mobile.getText().toString().length() == 10 &&
                !par_dir1_addressproof.getText().toString().equals("-- Select Address Proof --")) {
            par_next_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
            click = true;
        } else {
            par_next_button.setBackgroundColor(getResources().getColor(R.color.colorash));
            click = false;
        }
    }
}