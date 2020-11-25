package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import rutherfordit.com.instasalary.R;

public class GetAadhaarDetailsActivity extends AppCompatActivity {

    RelativeLayout AdharsubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_aadhaar_details);

        AdharsubmitButton = findViewById(R.id.AdharsubmitButton);

        AdharsubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GetPanDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}