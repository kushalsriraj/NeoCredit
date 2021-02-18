package rutherfordit.com.instasalary.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.activities.partnership.PartnershipDocUploadActivity;
import rutherfordit.com.instasalary.activities.privatelimited.PrivateLimitedDocUploadActivity;
import rutherfordit.com.instasalary.activities.sp.SPDocumentUploadActivity;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView logo;
    SharedPrefsManager sharedPrefsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {

        logo = findViewById(R.id.logo);

        RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RESTART, 0.5f, Animation.RESTART, 0.5f);
        rotate.setDuration(1000);
        rotate.setInterpolator(new LinearInterpolator());
        logo.startAnimation(rotate);
        sharedPrefsManager = new SharedPrefsManager(getApplicationContext());

        new Handler().postDelayed(new Runnable() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {

                if (sharedPrefsManager.getCHECK_PAGE().equals("2")){
                    Intent intent = new Intent(SplashScreenActivity.this, SegmentActivity.class);
                    startActivity(intent);

                }
                else if (sharedPrefsManager.getCHECK_PAGE().equals("3")){
                    Intent intent = new Intent(SplashScreenActivity.this, CompanyDetails.class);
                    startActivity(intent);
                }

                else if (sharedPrefsManager.getCHECK_PAGE().equals("4")){
                    Intent intent = new Intent(SplashScreenActivity.this, SPDocumentUploadActivity.class);
                    startActivity(intent);
                }

                else if (sharedPrefsManager.getCHECK_PAGE().equals("5")){
                    Intent intent = new Intent(SplashScreenActivity.this, PrivateLimitedDocUploadActivity.class);
                    startActivity(intent);
                }

                else if (sharedPrefsManager.getCHECK_PAGE().equals("6")){
                    Intent intent = new Intent(SplashScreenActivity.this, PartnershipDocUploadActivity.class);
                    startActivity(intent);
                }

                else if (sharedPrefsManager.getCHECK_PAGE().equals("7")){
                    Intent intent = new Intent(SplashScreenActivity.this, BankDetailsActivity.class);
                    startActivity(intent);
                }

                else if (sharedPrefsManager.getCHECK_PAGE().equals("8")){
                    Intent intent = new Intent(SplashScreenActivity.this, UploadInvoice.class);
                    startActivity(intent);
                }

                else if (sharedPrefsManager.getCHECK_PAGE().equals("9")){
                    Intent intent = new Intent(SplashScreenActivity.this, PromoterDetails.class);
                    startActivity(intent);
                }

                else if (sharedPrefsManager.getCHECK_PAGE().equals("10")){
                    Intent intent = new Intent(SplashScreenActivity.this, DirectorDetails.class);
                    startActivity(intent);
                }

                else {

                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);


                    Pair[] pairs = new Pair[1];
                    pairs[0] = new Pair<View, String>(logo, "logo_image");

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SplashScreenActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                }
            }
        }, 2000);

    }

    public void openpermission(View view) {
    }
}