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
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import rutherfordit.com.instasalary.R;

public class SegmentActivity extends AppCompatActivity {

    RelativeLayout SegmentsubmitButton;
    RadioGroup rg;
    AppCompatRadioButton soleProprietorship;
    AppCompatRadioButton privateLimited;
    AppCompatRadioButton partnershipForm;
    ImageView purplebackarrow;

    @Override
    protected void onRestart() {
        super.onRestart();
       // SegmentsubmitButton.setBackgroundColor(getResources().getColor(R.color.colorash));
    }

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
        purplebackarrow = findViewById(R.id.purplebackarrow);

        purplebackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if(Build.VERSION.SDK_INT>=21) {
            ColorStateList colorStateList = new ColorStateList(
                    new int[][]{
                            new int[]{-android.R.attr.state_checked},
                            new int[]{android.R.attr.state_checked}
                    },
                    new int[]{

                            R.color.neopurple
                            ,Color.rgb(255, 255, 255),
                    }
            );

            soleProprietorship.setSupportButtonTintList(colorStateList);
            privateLimited.setSupportButtonTintList(colorStateList);
            partnershipForm.setSupportButtonTintList(colorStateList);

            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (soleProprietorship.isChecked())
                    {
                        SegmentsubmitButton.setBackgroundColor(getResources().getColor(R.color.neopurple));

                        soleProprietorship.setTextColor(Color.WHITE);
                        soleProprietorship.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackgroundcolor));

                        privateLimited.setTextColor(Color.BLACK);
                        privateLimited.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackground));

                        partnershipForm.setTextColor(Color.BLACK);
                        partnershipForm.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackground));
                    }
                    else if (privateLimited.isChecked())
                    {
                        SegmentsubmitButton.setBackgroundColor(getResources().getColor(R.color.neopurple));

                        privateLimited.setTextColor(Color.WHITE);
                        privateLimited.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackgroundcolor));

                        partnershipForm.setTextColor(Color.BLACK);
                        partnershipForm.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackground));

                        soleProprietorship.setTextColor(Color.BLACK);
                        soleProprietorship.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.radiobackground));
                    }

                    else if (partnershipForm.isChecked())
                    {
                        SegmentsubmitButton.setBackgroundColor(getResources().getColor(R.color.neopurple));

                        partnershipForm.setTextColor(Color.WHITE);
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
            @Override
            public void onClick(View v) {

                if (soleProprietorship.isChecked())
                {
                    Intent intent = new Intent(getApplicationContext(), SoleProprietorshipDetailsActivity.class);
                    startActivity(intent);
                }
                else if (privateLimited.isChecked())
                {
                    Intent intent = new Intent(getApplicationContext(), PrivateLimitedCompanyDetailsActivity.class);
                    startActivity(intent);
                }
                else if (partnershipForm.isChecked())
                {
                    Intent intent = new Intent(getApplicationContext(), PartnershipCompanyDetailsActivity.class);
                    startActivity(intent);
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