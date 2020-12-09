package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import rutherfordit.com.instasalary.R;

public class SPDocumentUploadActivity extends AppCompatActivity {

    RelativeLayout Submit_sp_docs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_p_document_upload);

        Submit_sp_docs = findViewById(R.id.Submit_sp_docs);

        Submit_sp_docs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),SpCreditRequirementsActivity.class);
                startActivity(i);
            }
        });

    }
}