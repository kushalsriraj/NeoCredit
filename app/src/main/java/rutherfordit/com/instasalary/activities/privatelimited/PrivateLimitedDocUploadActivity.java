package rutherfordit.com.instasalary.activities.privatelimited;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.activities.sp.SpCreditRequirementsActivity;

public class PrivateLimitedDocUploadActivity extends AppCompatActivity {

    RelativeLayout Submit_pl_docs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_limited_doc_upload);

        Submit_pl_docs = findViewById(R.id.Submit_pl_docs);

        Submit_pl_docs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), PlCreditRequirementsActivity.class);
                startActivity(i);
            }
        });

    }
}