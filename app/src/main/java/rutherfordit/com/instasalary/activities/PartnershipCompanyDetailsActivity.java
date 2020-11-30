package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import rutherfordit.com.instasalary.R;

public class PartnershipCompanyDetailsActivity extends AppCompatActivity {

    RelativeLayout next_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnership_company_details);

        next_button = findViewById(R.id.next_button);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PartnershipPartnerFirstDetailsActivity.class);
                startActivity(intent);
                next_button.setBackgroundColor(Color.rgb(16, 221, 188));
            }
        });
    }
}