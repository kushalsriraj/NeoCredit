package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import at.wirecube.additiveanimations.additive_animator.AdditiveAnimator;
import rutherfordit.com.instasalary.R;

public class BankDetailsActivity extends AppCompatActivity {

    ImageView moveimage;
    RelativeLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);

        root = findViewById(R.id.root);
        moveimage = findViewById(R.id.moveimage);

        Display display = getWindowManager().getDefaultDisplay();
        float width = display.getWidth();
        TranslateAnimation animation = new TranslateAnimation(width-2000, 0 , 0, 0); // new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(1000); // animation duration
        animation.setRepeatCount(0); // animation repeat count
        animation.setRepeatMode(1); // repeat animation (left to right, right to
        // left )
        animation.setFillAfter(true);

        moveimage.startAnimation(animation);

     //   moveimage.animate().x(1000).y(10).setDuration(1000);
    }
}