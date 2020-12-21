package rutherfordit.com.instasalary.activities.sp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import es.dmoral.toasty.Toasty;
import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.activities.HomeActivity;

public class SoleProprietorshipBankDetailsActivity extends AppCompatActivity {

    ImageView purplebackarrow;
    TextInputEditText sp_bankname,sp_bankbranch,sp_accno,sp_bankifsc;
    CardView sp_submitBankDetails;
    boolean click = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sole_proprietorship_bank_details);

        init();
        onclicks();
        textChangeListeners();
    }

    private void init()
    {
        purplebackarrow = findViewById(R.id.purplebackarrow);
        sp_bankname = findViewById(R.id.sp_bankname);
        sp_bankbranch = findViewById(R.id.sp_bankbranch);
        sp_accno = findViewById(R.id.sp_accno);
        sp_bankifsc = findViewById(R.id.sp_bankifsc);
        sp_submitBankDetails = findViewById(R.id.sp_submitBankDetails);
    }

    private void onclicks() {

        purplebackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        sp_submitBankDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click)
                {
                    Toasty.success(getApplicationContext(), "Saved Successfully..", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                }
                else
                {
                    Toasty.warning(getApplicationContext(), "Please Fill The Fields..", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void textChangeListeners()
    {

        sp_bankname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!sp_bankname.getText().toString().equals("") && !sp_bankbranch.getText().toString().equals("")
                        && sp_accno.getText().toString().length() > 10 && sp_bankifsc.getText().toString().length() == 11 )
                {
                    //sp_submitBankDetails.setBackgroundColor(getResources().getColor(R.color.neopurple));
                    sp_submitBankDetails.setBackground(getDrawable(R.drawable.gradient_neocredit));

                    click = true;
                }
                else
                {
                    sp_submitBankDetails.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        sp_bankbranch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!sp_bankname.getText().toString().equals("") && !sp_bankbranch.getText().toString().equals("")
                        && sp_accno.getText().toString().length() > 10 && sp_bankifsc.getText().toString().length() == 11 )
                {
                    sp_submitBankDetails.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                }
                else
                {
                    sp_submitBankDetails.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        sp_accno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!sp_bankname.getText().toString().equals("") && !sp_bankbranch.getText().toString().equals("")
                        && sp_accno.getText().toString().length() > 10 && sp_bankifsc.getText().toString().length() == 11 )
                {
                    sp_submitBankDetails.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                }
                else
                {
                    sp_submitBankDetails.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        sp_bankifsc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!sp_bankname.getText().toString().equals("") && !sp_bankbranch.getText().toString().equals("")
                        && sp_accno.getText().toString().length() > 10 && sp_bankifsc.getText().toString().length() == 11 )
                {
                    sp_submitBankDetails.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                }
                else
                {
                    sp_submitBankDetails.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

    }

    @Override
    public void onBackPressed()

    {
        super.onBackPressed();
    }
}