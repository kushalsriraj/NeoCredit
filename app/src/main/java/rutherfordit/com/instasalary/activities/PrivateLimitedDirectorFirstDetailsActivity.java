package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import rutherfordit.com.instasalary.R;

public class PrivateLimitedDirectorFirstDetailsActivity extends AppCompatActivity {

    RelativeLayout next1_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_limited_director_first_details);

        next1_button = findViewById(R.id.next1_button);

        next1_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PrivateLimitedDirectorSecondDetailsActivity.class);
                startActivity(intent);
                next1_button.setBackgroundColor(Color.parseColor(String.valueOf(R.color.neogreen)));
            }
        });
    }
}