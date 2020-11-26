package rutherfordit.com.instasalary.activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import rutherfordit.com.instasalary.R;

public class MainActivity extends AppCompatActivity {

    ImageView logo;
    VideoView vid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        logo = findViewById(R.id.logo);
//        vid = findViewById(R.id.videoView);
//
//        MediaController m = new MediaController(getApplicationContext());
//        vid.setMediaController(m);
//
//        String path = "android.resource://rutherfordit.com.instasalary.activities/" + R.raw.splashscreevid;
//
//        Uri u = Uri.parse(path);
//
//        vid.setVideoURI(u);
//        vid.requestFocus();
//        vid.start();


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                Pair[] pairs = new Pair[1];
                pairs[0] = new Pair<View, String>(logo, "logo_image");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pairs);
                startActivity(intent, options.toBundle());

            }
        }, 4000);


    }
}