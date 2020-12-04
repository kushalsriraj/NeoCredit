package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import rutherfordit.com.instasalary.R;

public class PrivateLimitedCompanyDetailsActivity extends AppCompatActivity {

    RelativeLayout next_button;
    ImageView purplebackarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_limited_company_details);

        next_button = findViewById(R.id.next_button);

        purplebackarrow = findViewById(R.id.purplebackarrow);

        purplebackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PrivateLimitedDirectorFirstDetailsActivity.class);
                startActivity(intent);
                next_button.setBackgroundColor(Color.parseColor("#10ddbc"));
            }
        });
    }
}