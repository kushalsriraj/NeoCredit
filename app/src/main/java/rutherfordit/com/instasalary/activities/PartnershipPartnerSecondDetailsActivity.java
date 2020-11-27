package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import rutherfordit.com.instasalary.R;

public class PartnershipPartnerSecondDetailsActivity extends AppCompatActivity {

    RelativeLayout next2_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnership_partner_second_details);

        next2_button = findViewById(R.id.next2_button);

        next2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PartnershipBankDetailsActivity.class);
                startActivity(intent);
                next2_button.setBackgroundColor(Color.rgb(16, 221, 188));
            }
        });
    }
}