package rutherfordit.com.instasalary.activities.partnership;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.activities.BankDetailsActivity;

public class ParCreditRequirementsActivity extends AppCompatActivity {

    RelativeLayout submitParCreditReq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_par_credit_requirements);

        submitParCreditReq = findViewById(R.id.submitParCreditReq);

        submitParCreditReq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BankDetailsActivity.class);
                startActivity(intent);
                submitParCreditReq.setBackground(getDrawable(R.drawable.gradient_neocredit));
            }
        });
    }
}