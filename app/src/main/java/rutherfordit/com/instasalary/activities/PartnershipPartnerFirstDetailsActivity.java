package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import rutherfordit.com.instasalary.R;

public class PartnershipPartnerFirstDetailsActivity extends AppCompatActivity {

    RelativeLayout next1_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnership_first_partner_details);

        next1_button = findViewById(R.id.next1_button);

        next1_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PartnershipPartnerSecondDetailsActivity.class);
                startActivity(intent);
                next1_button.setBackgroundColor(Color.rgb(16, 221, 188));
            }
        });
    }
}