package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import rutherfordit.com.instasalary.R;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout loginbottombutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginbottombutton = findViewById(R.id.loginbottombutton);


        loginbottombutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EnterOTPActivity.class);
                startActivity(intent);
            }
        });
    }
}