package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import rutherfordit.com.instasalary.R;

public class SoleProprietorshipCompanyDetailsActivity extends AppCompatActivity {

    RelativeLayout salariedprofsubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sole_proprietorship_company_details);

        salariedprofsubmit = findViewById(R.id.salariedprofsubmit);


        salariedprofsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salariedprofsubmit.setBackgroundColor(Color.parseColor("#10ddbc"));
                Intent intent = new Intent(getApplicationContext(), SoleProprietorshipBankDetailsActivity.class);
                startActivity(intent);

            }
        });
    }
}