package rutherfordit.com.instasalary.activities.privatelimited;

import androidx.appcompat.app.AppCompatActivity;

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
import rutherfordit.com.instasalary.activities.BankDetailsActivity;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;

public class PlCreditRequirementsActivity extends AppCompatActivity {

    RelativeLayout submitPLCreditReq;
    ImageView back_credit_req;
    TextInputEditText pl_enter_amount;
    boolean click = false;
    SharedPrefsManager sharedPrefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pl_credit_requirements);

        pl_enter_amount = findViewById(R.id.pl_enter_amount);
        submitPLCreditReq = findViewById(R.id.submitPLCreditReq);
        back_credit_req = findViewById(R.id.back_credit_req);
        sharedPrefsManager = new SharedPrefsManager(getApplicationContext());

        pl_enter_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!pl_enter_amount.getText().toString().equals(""))
                {
                    //submitSpCreditReq.setBackgroundColor(getResources().getColor(R.color.neopurple));
                    submitPLCreditReq.setBackground(getDrawable(R.drawable.gradient_neocredit));

                    click = true;
                }
                else
                {
                    submitPLCreditReq.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        back_credit_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        submitPLCreditReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (click)
                {
                    Intent intent = new Intent(getApplicationContext(), BankDetailsActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toasty.warning(getApplicationContext(), "Please Check The Fields..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}