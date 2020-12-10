package rutherfordit.com.instasalary.activities.partnership;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import rutherfordit.com.instasalary.R;

public class PartnershipDocUploadActivity extends AppCompatActivity {

    RelativeLayout Submit_par_docs;
    ImageView back_pardoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnership_doc_upload);

        Submit_par_docs = findViewById(R.id.Submit_par_docs);
        back_pardoc = findViewById(R.id.back_pardoc);

        back_pardoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Submit_par_docs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(getApplicationContext(),ParCreditRequirementsActivity.class);
                startActivity(i);
            }
        });
    }
}