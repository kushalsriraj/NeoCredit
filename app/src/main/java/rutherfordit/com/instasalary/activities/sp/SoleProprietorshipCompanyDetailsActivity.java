package rutherfordit.com.instasalary.activities.sp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import rutherfordit.com.instasalary.R;

public class SoleProprietorshipCompanyDetailsActivity extends AppCompatActivity {

    RelativeLayout submitCompanyInfo;
    ImageView purplebackarrow;
    TextInputEditText sp_businessLandline,sp_company_name,sp_typeOfService,sp_howOldIsTheCompany,sp_annualTurnover,sp_company_address,sp_addressProof;
    Spinner Spinner_typeOfService,Spinner_howOldIsTheCompany,Spinner_annualTurnover,Spinner_businessAddressProof;
    boolean click = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sole_proprietorship_company_details);

        init();

    }

    private void init()
    {

        sp_businessLandline = findViewById(R.id.sp_businessLandline);
        sp_company_name = findViewById(R.id.sp_company_name);
        sp_typeOfService = findViewById(R.id.sp_typeOfService);
        sp_howOldIsTheCompany = findViewById(R.id.sp_howOldIsTheCompany);
        sp_annualTurnover = findViewById(R.id.sp_annualTurnover);
        sp_company_address = findViewById(R.id.sp_company_address);
        sp_addressProof = findViewById(R.id.sp_addressProof);
        Spinner_typeOfService = findViewById(R.id.Spinner_typeOfService);
        Spinner_howOldIsTheCompany = findViewById(R.id.Spinner_howOldIsTheCompany);
        Spinner_annualTurnover = findViewById(R.id.Spinner_annualTurnover);
        Spinner_businessAddressProof = findViewById(R.id.Spinner_businessAddressProof);

        submitCompanyInfo = findViewById(R.id.submitCompanyInfo);
        purplebackarrow = findViewById(R.id.purplebackarrow);

        onClicks();
        SpinnersSelected();
        textWatchers();
    }

    private void textWatchers()
    {

        sp_company_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!sp_company_name.getText().toString().equals("") && !sp_company_address.getText().toString().equals("") &&
                        !sp_typeOfService.getText().toString().equals("-- Select Type of Service --") &&
                        !sp_howOldIsTheCompany.getText().toString().equals("-- Select Tenuraty the Company --") &&
                        !sp_annualTurnover.getText().toString().equals("-- Select Annual Turnover --") &&
                        !sp_addressProof.getText().toString().equals("-- Select Address Proof --") )
                {
                    //submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.neopurple));
                    submitCompanyInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));

                    click = true;
                }
                else
                {
                    submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        sp_company_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!sp_company_name.getText().toString().equals("") && !sp_company_address.getText().toString().equals("") &&
                        !sp_typeOfService.getText().toString().equals("-- Select Type of Service --") &&
                        !sp_howOldIsTheCompany.getText().toString().equals("-- Select Tenuraty the Company --") &&
                        !sp_annualTurnover.getText().toString().equals("-- Select Annual Turnover --") &&
                        !sp_addressProof.getText().toString().equals("-- Select Address Proof --") )
                {
                    submitCompanyInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                }
                else
                {
                    submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
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

                // String addressProof = parent.getItemAtPosition(pos).toString();

                sp_typeOfService.setText(parent.getItemAtPosition(pos).toString());

                if (!sp_company_name.getText().toString().equals("") && !sp_company_address.getText().toString().equals("") &&
                        !sp_typeOfService.getText().toString().equals("-- Select Type of Service --") &&
                        !sp_howOldIsTheCompany.getText().toString().equals("-- Select Tenuraty the Company --") &&
                        !sp_annualTurnover.getText().toString().equals("-- Select Annual Turnover --") &&
                        !sp_addressProof.getText().toString().equals("-- Select Address Proof --") )
                {
                    submitCompanyInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                }
                else
                {
                    submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        Spinner_howOldIsTheCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                // String addressProof = parent.getItemAtPosition(pos).toString();

                sp_howOldIsTheCompany.setText(parent.getItemAtPosition(pos).toString());

                if (!sp_company_name.getText().toString().equals("") && !sp_company_address.getText().toString().equals("") &&
                        !sp_typeOfService.getText().toString().equals("-- Select Type of Service --") &&
                        !sp_howOldIsTheCompany.getText().toString().equals("-- Select Tenuraty the Company --") &&
                        !sp_annualTurnover.getText().toString().equals("-- Select Annual Turnover --") &&
                        !sp_addressProof.getText().toString().equals("-- Select Address Proof --") )
                {
                    submitCompanyInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                }
                else
                {
                    submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        Spinner_annualTurnover.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                // String addressProof = parent.getItemAtPosition(pos).toString();

                sp_annualTurnover.setText(parent.getItemAtPosition(pos).toString());

                if (!sp_company_name.getText().toString().equals("") && !sp_company_address.getText().toString().equals("") &&
                        !sp_typeOfService.getText().toString().equals("-- Select Type of Service --") &&
                        !sp_howOldIsTheCompany.getText().toString().equals("-- Select Tenuraty the Company --") &&
                        !sp_annualTurnover.getText().toString().equals("-- Select Annual Turnover --") &&
                        !sp_addressProof.getText().toString().equals("-- Select Address Proof --") )
                {
                    submitCompanyInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                }
                else
                {
                    submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        Spinner_businessAddressProof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                // String addressProof = parent.getItemAtPosition(pos).toString();

                sp_addressProof.setText(parent.getItemAtPosition(pos).toString());

                if (!sp_company_name.getText().toString().equals("") && !sp_company_address.getText().toString().equals("") &&
                        !sp_typeOfService.getText().toString().equals("-- Select Type of Service --") &&
                        !sp_howOldIsTheCompany.getText().toString().equals("-- Select Tenuraty the Company --") &&
                        !sp_annualTurnover.getText().toString().equals("-- Select Annual Turnover --") &&
                        !sp_addressProof.getText().toString().equals("-- Select Address Proof --") )
                {
                    submitCompanyInfo.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                }
                else
                {
                    submitCompanyInfo.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }
            public void onNothingSelected(AdapterView<?> parent) { }
        });

    }

    private void onClicks()
    {

        sp_typeOfService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner_typeOfService.performClick();
            }
        });
        sp_howOldIsTheCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner_howOldIsTheCompany.performClick();
            }
        });
        sp_annualTurnover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner_annualTurnover.performClick();
            }
        });
        sp_addressProof.setOnClickListener(new View.OnClickListener() {
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
                    Intent intent = new Intent(getApplicationContext(), SPDocumentUploadActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please Check All The Fields.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}