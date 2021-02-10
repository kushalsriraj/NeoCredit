package rutherfordit.com.instasalary.activities.sp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
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

import java.text.DateFormat;
import java.util.Calendar;

import rutherfordit.com.instasalary.R;

public class SoleProprietorshipDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextInputEditText sp_fullname,sp_dob,sp_address,sp_pan,sp_adhar,sp_addressproof;
    Spinner sp_addressProofSpinner;
    RelativeLayout submitSpInfo;
    ImageView purplebackarrow;
    boolean click = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sole_proprietorship);

        init();

    }

    private void init()
    {

        sp_fullname = findViewById(R.id.sp_fullname);
        sp_dob = findViewById(R.id.sp_dob);
        sp_address = findViewById(R.id.sp_address);
        sp_pan = findViewById(R.id.sp_pan);
        sp_adhar = findViewById(R.id.sp_adhar);
        sp_addressproof = findViewById(R.id.sp_addressproof);
        sp_addressProofSpinner = findViewById(R.id.sp_addressProofSpinner);
        submitSpInfo = findViewById(R.id.submitSpInfo);
        purplebackarrow = findViewById(R.id.purplebackarrow);

        onClicks();

    }

    private void onClicks()
    {

        sp_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");

            }
        });

        sp_fullname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!sp_fullname.getText().toString().equals("") && !sp_dob.getText().toString().equals("") &&
                        !sp_address.getText().toString().equals("") && sp_pan.getText().toString().length() == 10 &&
                         sp_adhar.getText().toString().length() == 12 && !sp_addressproof.getText().toString().equals("-- Select Address Proof --") )
                {
                    //submitSpInfo.setBackgroundColor(getResources().getColor(R.color.neopurple));
                    submitSpInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));

                    click = true;
                }
                else
                {
                    submitSpInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sp_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!sp_fullname.getText().toString().equals("") && !sp_dob.getText().toString().equals("") &&
                        !sp_address.getText().toString().equals("") && sp_pan.getText().toString().length() == 10 &&
                        sp_adhar.getText().toString().length() == 12 && !sp_addressproof.getText().toString().equals("-- Select Address Proof --") )
                {
                    submitSpInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                }
                else
                {
                    submitSpInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sp_pan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!sp_fullname.getText().toString().equals("") && !sp_dob.getText().toString().equals("") &&
                        !sp_address.getText().toString().equals("") && sp_pan.getText().toString().length() == 10 &&
                        sp_adhar.getText().toString().length() == 12 && !sp_addressproof.getText().toString().equals("-- Select Address Proof --") )
                {
                    submitSpInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                }
                else
                {
                    submitSpInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sp_adhar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!sp_fullname.getText().toString().equals("") && !sp_dob.getText().toString().equals("") &&
                        !sp_address.getText().toString().equals("") && sp_pan.getText().toString().length() == 10 &&
                        sp_adhar.getText().toString().length() == 12 && !sp_addressproof.getText().toString().equals("-- Select Address Proof --") )
                {
                    submitSpInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                }
                else
                {
                    submitSpInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sp_addressproof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sp_addressProofSpinner.performClick();
            }
        });

        sp_addressProofSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                       // String addressProof = parent.getItemAtPosition(pos).toString();

                        sp_addressproof.setText(parent.getItemAtPosition(pos).toString());

                        if (!sp_fullname.getText().toString().equals("") && !sp_dob.getText().toString().equals("") &&
                                !sp_address.getText().toString().equals("") && sp_pan.getText().toString().length() == 10 &&
                                sp_adhar.getText().toString().length() == 12 && !sp_addressproof.getText().toString().equals("-- Select Address Proof --") )
                        {
                            submitSpInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));
                            click = true;
                        }
                        else
                        {
                            submitSpInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
                            click = false;
                        }

                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                        if (!sp_fullname.getText().toString().equals("") && !sp_dob.getText().toString().equals("") &&
                                !sp_address.getText().toString().equals("") && sp_pan.getText().toString().length() == 10 &&
                                sp_adhar.getText().toString().length() == 12 && !sp_addressproof.getText().toString().equals("-- Select Address Proof --") )
                        {
                            submitSpInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));
                            click = true;
                        }
                        else
                        {
                            submitSpInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
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

        submitSpInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click)
                {
                    Log.e("sp_dob", "sp_dob: " + sp_dob );

                    Intent intent = new Intent(getApplicationContext(), SoleProprietorshipCompanyDetailsActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please Check All The Fields.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
          super.onBackPressed();
    }

    @SuppressLint("SetTextI18n")
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

        sp_dob.setText( year + "-" + (newMonth) + "-" + newDay );

        if (!sp_fullname.getText().toString().equals("") && !sp_dob.getText().toString().equals("") &&
                !sp_address.getText().toString().equals("") && sp_pan.getText().toString().length() == 10 &&
                sp_adhar.getText().toString().length() == 12 && !sp_addressproof.getText().toString().equals("-- Select Address Proof --"))
        {
            submitSpInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));
            click = true;
            
        }
        else
        {
            submitSpInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
            click = false;
        }
    }
}