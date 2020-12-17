package rutherfordit.com.instasalary.activities.privatelimited;

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
import rutherfordit.com.instasalary.activities.sp.DatePickerFragment;
import rutherfordit.com.instasalary.activities.sp.SoleProprietorshipCompanyDetailsActivity;

public class PrivateLimitedDirectorFirstDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    RelativeLayout next1_button;
    ImageView purplebackarrow;
    TextInputEditText pl_dir1_fullName, pl_dob, pl_dir1_address, pl_dir1_pan, pl_dir1_adhar, pl_dir1_addressproof, pl_DIN_Number,pl_dir1_mobile;
    Spinner pl_dir1_addressProofSpinner;
    boolean click = false;

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
                if (!pl_dir1_fullName.getText().toString().equals("") &&
                        !pl_dob.getText().toString().equals("") &&
                        !pl_dir1_address.getText().toString().equals("") &&
                        pl_dir1_pan.getText().toString().length() == 10 &&
                        pl_dir1_adhar.getText().toString().length() == 12 &&
                        !pl_DIN_Number.getText().toString().equals("") &&
                        pl_dir1_mobile.getText().toString().length() == 10 &&
                        !pl_dir1_addressproof.getText().toString().equals("-- Select Address Proof --"))

                        {
                    next1_button.setBackground(getDrawable(R.drawable.gradient_neocredit));

                    click = true;
                } else {
                    next1_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
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
                if (!pl_dir1_fullName.getText().toString().equals("") &&
                        !pl_dob.getText().toString().equals("") &&
                        !pl_dir1_address.getText().toString().equals("") &&
                        pl_dir1_pan.getText().toString().length() == 10 &&
                        pl_dir1_adhar.getText().toString().length() == 12 &&
                        !pl_DIN_Number.getText().toString().equals("") &&
                        pl_dir1_mobile.getText().toString().length() == 10 &&
                        !pl_dir1_addressproof.getText().toString().equals("-- Select Address Proof --")) {
                    next1_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                } else {
                    next1_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
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
                if (!pl_dir1_fullName.getText().toString().equals("") &&
                        !pl_dob.getText().toString().equals("") &&
                        !pl_dir1_address.getText().toString().equals("") &&
                        pl_dir1_pan.getText().toString().length() == 10 &&
                        pl_dir1_adhar.getText().toString().length() == 12 &&
                        !pl_DIN_Number.getText().toString().equals("") &&
                        pl_dir1_mobile.getText().toString().length() == 10 &&
                        !pl_dir1_addressproof.getText().toString().equals("-- Select Address Proof --")) {
                    next1_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
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
                if (!pl_dir1_fullName.getText().toString().equals("") &&
                        !pl_dob.getText().toString().equals("") &&
                        !pl_dir1_address.getText().toString().equals("") &&
                        pl_dir1_pan.getText().toString().length() == 10 &&
                        pl_dir1_adhar.getText().toString().length() == 12 &&
                        !pl_DIN_Number.getText().toString().equals("") &&
                        pl_dir1_mobile.getText().toString().length() == 10 &&
                        !pl_dir1_addressproof.getText().toString().equals("-- Select Address Proof --")) {
                    next1_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
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

                        // String addressProof = parent.getItemAtPosition(pos).toString();

                        pl_dir1_addressproof.setText(parent.getItemAtPosition(pos).toString());

                        if (!pl_dir1_fullName.getText().toString().equals("") &&
                                !pl_dob.getText().toString().equals("") &&
                                !pl_dir1_address.getText().toString().equals("") &&
                                pl_dir1_pan.getText().toString().length() == 10 &&
                                pl_dir1_adhar.getText().toString().length() == 12 &&
                                !pl_DIN_Number.getText().toString().equals("") &&
                                pl_dir1_mobile.getText().toString().length() == 10 &&
                                !pl_dir1_addressproof.getText().toString().equals("-- Select Address Proof --")) {
                            next1_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                            click = true;
                        } else {
                            next1_button.setBackgroundColor(getResources().getColor(R.color.colorash));
                            click = false;
                        }

                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                        if (!pl_dir1_fullName.getText().toString().equals("") &&
                                !pl_dob.getText().toString().equals("") &&
                                !pl_dir1_address.getText().toString().equals("") &&
                                pl_dir1_pan.getText().toString().length() == 10 &&
                                pl_dir1_adhar.getText().toString().length() == 12 &&
                                !pl_DIN_Number.getText().toString().equals("") &&
                                pl_dir1_mobile.getText().toString().length() == 10 &&
                                !pl_dir1_addressproof.getText().toString().equals("-- Select Address Proof --")) {
                            next1_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
                            click = true;
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

        pl_dob.setText(year + "-" + (month + 1) + "-" + dayOfMonth);

        if (!pl_dir1_fullName.getText().toString().equals("") &&
                !pl_dob.getText().toString().equals("") &&
                !pl_dir1_address.getText().toString().equals("") &&
                pl_dir1_pan.getText().toString().length() == 10 &&
                pl_dir1_adhar.getText().toString().length() == 12 &&
                !pl_DIN_Number.getText().toString().equals("") &&
                pl_dir1_mobile.getText().toString().length() == 10 &&
                !pl_dir1_addressproof.getText().toString().equals("-- Select Address Proof --")) {
            next1_button.setBackground(getDrawable(R.drawable.gradient_neocredit));
            click = true;
        } else {
            next1_button.setBackgroundColor(getResources().getColor(R.color.colorash));
            click = false;
        }
    }
}