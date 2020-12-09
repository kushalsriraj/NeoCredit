package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import rutherfordit.com.instasalary.R;

public class SpCreditRequirementsActivity extends AppCompatActivity {

    RelativeLayout submitSpCreditReq;
    ImageView back_credit_req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_credit_requirements);

        submitSpCreditReq = findViewById(R.id.submitSpCreditReq);
        back_credit_req = findViewById(R.id.back_credit_req);

        back_credit_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        submitSpCreditReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SoleProprietorshipBankDetailsActivity.class);
                startActivity(intent);

            }
        });
    }
}