package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import rutherfordit.com.instasalary.R;

public class PartnershipPartnerSecondDetailsActivity extends AppCompatActivity {

    RelativeLayout next_button3;
    ImageView purplebackarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnership_partner_second_details);

        next_button3 = findViewById(R.id.next_button3);
        purplebackarrow = findViewById(R.id.purplebackarrow);

        purplebackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        next_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PartnershipBankDetailsActivity.class);
                startActivity(intent);
                next_button3.setBackgroundColor(Color.parseColor("#10ddbc"));
            }
        });
    }
}