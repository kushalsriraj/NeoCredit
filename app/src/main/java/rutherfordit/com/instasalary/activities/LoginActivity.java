package rutherfordit.com.instasalary.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputEditText;

import rutherfordit.com.instasalary.R;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout loginbottombutton;
    EditText enterphoneno_login;
    boolean click = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginbottombutton = findViewById(R.id.loginbottombutton);
        enterphoneno_login = findViewById(R.id.enterphoneno_login);
        enterphoneno_login.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(enterphoneno_login, InputMethodManager.SHOW_FORCED);

        enterphoneno_login.addTextChangedListener(new TextWatcher() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (enterphoneno_login.getText().toString().length() == 10)
                {
                    loginbottombutton.setBackgroundColor(getResources().getColor(R.color.neopurple));
                    click=true;
                }
                else
                {
                    loginbottombutton.setBackgroundColor(getResources().getColor(R.color.colorash));
                    click=false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        loginbottombutton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (click)
                {
                    Intent intent = new Intent(getApplicationContext(), EnterOTPActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        finishAffinity();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}