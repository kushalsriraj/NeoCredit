package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import rutherfordit.com.instasalary.R;

public class SegmentActivity extends AppCompatActivity {

    RelativeLayout SegmentsubmitButton;
    RadioGroup rg;
    AppCompatRadioButton soleProprietorship;
     AppCompatRadioButton privateLimited;
     AppCompatRadioButton partnershipForm;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segment);

        rg = findViewById(R.id.radio_group_transfer);
        soleProprietorship = findViewById(R.id.soleProprietorship);
        privateLimited = findViewById(R.id.privateLimited);
        partnershipForm = findViewById(R.id.partnershipForm);
        SegmentsubmitButton = findViewById(R.id.SegmentsubmitButton);


        if(Build.VERSION.SDK_INT>=21) {
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_checked},
                            new int[]{android.R.attr.state_checked}
                    },
                    new int[]{

                            R.color.neopurple
                            , Color.rgb(16, 221, 188),
                    }
            );

            soleProprietorship.setSupportButtonTintList(colorStateList);
            privateLimited.setSupportButtonTintList(colorStateList);
            partnershipForm.setSupportButtonTintList(colorStateList);



            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (soleProprietorship.isChecked()) {
                        soleProprietorship.setTextColor(Color.WHITE);
                        //vendorradioButton.setBackgroundColor(Color.rgb(212, 93, 133));
                        soleProprietorship.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackgroundcolor));


                        privateLimited.setTextColor(Color.BLACK);
                        privateLimited.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackground));

                        partnershipForm.setTextColor(Color.BLACK);
                        partnershipForm.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackground));

                    }
                    if (privateLimited.isChecked()){
                        privateLimited.setTextColor(Color.WHITE);
                        //driverownerradioButton.setBackgroundColor(Color.rgb(212, 93, 133));
                        privateLimited.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackgroundcolor));

                        partnershipForm.setTextColor(Color.BLACK);
                        partnershipForm.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackground));

                        soleProprietorship.setTextColor(Color.BLACK);
                        soleProprietorship.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackground));


                    }

                    if (partnershipForm.isChecked()){
                        partnershipForm.setTextColor(Color.WHITE);
                        //driverownerradioButton.setBackgroundColor(Color.rgb(212, 93, 133));
                        partnershipForm.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackgroundcolor));

                        privateLimited.setTextColor(Color.BLACK);
                        privateLimited.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackground));

                        soleProprietorship.setTextColor(Color.BLACK);
                        soleProprietorship.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackground));

                    }
                }
            });
        }

        SegmentsubmitButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                if (soleProprietorship.isChecked()){

                    Intent intent = new Intent(getApplicationContext(), SoleProprietorshipDetailsActivity.class);
                    startActivity(intent);
                    SegmentsubmitButton.setBackgroundColor(Color.rgb(16, 221, 188));


                }else if (privateLimited.isChecked()){

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    SegmentsubmitButton.setBackgroundColor(Color.rgb(16, 221, 188));


                }else if (partnershipForm.isChecked()){

                    Intent intent = new Intent(getApplicationContext(), GetPanDetailsActivity.class);
                    startActivity(intent);
                    SegmentsubmitButton.setBackgroundColor(Color.rgb(16, 221, 188));

                }
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}