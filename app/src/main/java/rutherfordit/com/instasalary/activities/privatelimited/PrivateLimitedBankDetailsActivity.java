package rutherfordit.com.instasalary.activities.privatelimited;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
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

public class PrivateLimitedBankDetailsActivity extends AppCompatActivity {

    ImageView purplebackarrow;
    TextInputEditText pl_bank_name, pl_bankbranch, pl_accno, pl_bankifsc;
    RelativeLayout submitPlCreditReq;
    boolean click = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_limited_bank_details);

        init();
        onclicks();
        textChangeListeners();
    }

    @SuppressLint("WrongViewCast")
    private void init()
    {
        purplebackarrow = findViewById(R.id.purplebackarrow);
        pl_bank_name = findViewById(R.id.pl_bank_name);
        pl_bankbranch = findViewById(R.id.pl_bankbranch);
        pl_accno = findViewById(R.id.pl_accno);
        pl_bankifsc = findViewById(R.id.pl_bankifsc);
        submitPlCreditReq = findViewById(R.id.submitPlCreditReq);
    }

    private void onclicks() {

        purplebackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        submitPlCreditReq.setOnClickListener(new View.OnClickListener() {
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

        pl_bank_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!pl_bank_name.getText().toString().equals("") && !pl_bankbranch.getText().toString().equals("")
                        && pl_accno.getText().toString().length() > 10 && pl_bankifsc.getText().toString().length() == 11 )
                {
                    //sp_submitBankDetails.setBackgroundColor(getResources().getColor(R.color.neopurple));
                    submitPlCreditReq.setBackground(getDrawable(R.drawable.gradient_neocredit));

                    click = true;
                }
                else
                {
                    submitPlCreditReq.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        pl_bankbranch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!pl_bank_name.getText().toString().equals("") && !pl_bankbranch.getText().toString().equals("")
                        && pl_accno.getText().toString().length() > 10 && pl_bankifsc.getText().toString().length() == 11 )
                {
                    submitPlCreditReq.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                }
                else
                {
                    submitPlCreditReq.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        pl_accno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!pl_bank_name.getText().toString().equals("") && !pl_bankbranch.getText().toString().equals("")
                        && pl_accno.getText().toString().length() > 10 && pl_bankifsc.getText().toString().length() == 11 )
                {
                    submitPlCreditReq.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                }
                else
                {
                    submitPlCreditReq.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        pl_bankifsc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!pl_bank_name.getText().toString().equals("") && !pl_bankbranch.getText().toString().equals("")
                        && pl_accno.getText().toString().length() > 10 && pl_bankifsc.getText().toString().length() == 11 )
                {
                    submitPlCreditReq.setBackground(getDrawable(R.drawable.gradient_neocredit));
                    click = true;
                }
                else
                {
                    submitPlCreditReq.setBackgroundColor(getResources().getColor(R.color.colorash));
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