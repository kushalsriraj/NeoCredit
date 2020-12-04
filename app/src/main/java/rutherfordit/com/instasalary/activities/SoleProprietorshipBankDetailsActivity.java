package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import rutherfordit.com.instasalary.R;

public class SoleProprietorshipBankDetailsActivity extends AppCompatActivity {

    ImageView purplebackarrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sole_proprietorship_bank_details);

        purplebackarrow = findViewById(R.id.purplebackarrow);

        purplebackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}