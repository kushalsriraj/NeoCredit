package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputEditText;

import rutherfordit.com.instasalary.R;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout loginbottombutton;
    TextInputEditText enterphoneno_login;
    boolean click = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginbottombutton = findViewById(R.id.loginbottombutton);
        enterphoneno_login = findViewById(R.id.enterphoneno_login);

        loginbottombutton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EnterOTPActivity.class);
                startActivity(intent);
                loginbottombutton.setBackgroundColor(Color.parseColor(String.valueOf(R.color.neogreen)));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        enterphoneno_login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (enterphoneno_login.getText().toString().trim().length() == 14) {
                    loginbottombutton.setBackgroundColor(Color.parseColor("#10ddbc"));
                    click = true;
                } else {
                    loginbottombutton.setBackgroundColor(Color.parseColor("#36000000"));
                    click = false;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                String prefix = "+91 ";
                int count = (s == null) ? 0 : s.toString().length();
                if (count < prefix.length()) {
                    enterphoneno_login.setText(prefix);
                    int selectionIndex = Math.max(count + 1, prefix.length());
                    enterphoneno_login.setSelection(selectionIndex);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        finishAffinity();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}