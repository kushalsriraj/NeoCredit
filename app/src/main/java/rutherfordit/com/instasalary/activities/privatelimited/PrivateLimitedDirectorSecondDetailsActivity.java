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

public class PrivateLimitedDirectorSecondDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    RelativeLayout next2_button;
    ImageView purplebackarrow;
    TextInputEditText pl_dir2_fullName, pl_2_dob, pl_dir2_address, pl_dir2_pan, pl_dir2_adhar, pl_dir2_addressproof, pl_DIN2_Number, pl_dir2_mobile;
    Spinner pl_dir2_addressProofSpinner;
    boolean click = false;

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
                if (click) {
                    Intent intent = new Intent(getApplicationContext(), PrivateLimitedDocUploadActivity.class);
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
}