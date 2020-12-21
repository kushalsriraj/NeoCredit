package rutherfordit.com.instasalary.activities.sp;

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

public class SpCreditRequirementsActivity extends AppCompatActivity {

    RelativeLayout submitSpCreditReq;
    ImageView back_credit_req;
    TextInputEditText enterCreditAmount;
    boolean click = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_credit_requirements);

        enterCreditAmount = findViewById(R.id.enterCreditAmount);
        submitSpCreditReq = findViewById(R.id.submitSpCreditReq);
        back_credit_req = findViewById(R.id.back_credit_req);

        enterCreditAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!enterCreditAmount.getText().toString().equals(""))
                {
                    //submitSpCreditReq.setBackgroundColor(getResources().getColor(R.color.neopurple));
                    submitSpCreditReq.setBackground(getDrawable(R.drawable.gradient_neocredit));

                    click = true;
                }
                else
                {
                    submitSpCreditReq.setBackgroundColor(getResources().getColor(R.color.colorash));
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


        submitSpCreditReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (click)
                {
                    Intent intent = new Intent(getApplicationContext(), SoleProprietorshipBankDetailsActivity.class);
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