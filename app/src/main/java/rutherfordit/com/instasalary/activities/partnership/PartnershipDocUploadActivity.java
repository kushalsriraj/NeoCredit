package rutherfordit.com.instasalary.activities.partnership;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import rutherfordit.com.instasalary.R;

public class PartnershipDocUploadActivity extends AppCompatActivity {

    RelativeLayout Submit_sp_docs;
    ImageView back_spdoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnership_doc_upload);

        Submit_sp_docs = findViewById(R.id.Submit_sp_docs);
        back_spdoc = findViewById(R.id.back_spdoc);

        back_spdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Submit_sp_docs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                /*Intent i = new Intent(getApplicationContext(),SoleProprietorshipBankDetailsActivity.class);
                startActivity(i);*/
            }
        });
    }
}