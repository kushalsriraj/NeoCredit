package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.chaos.view.PinView;

import rutherfordit.com.instasalary.R;

public class EnterOTPActivity extends AppCompatActivity {

    Button submitadharotp;
    ImageView purplebackarrow;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_o_t_p);

        submitadharotp = findViewById(R.id.submitadharotp);
        purplebackarrow = findViewById(R.id.purplebackarrow);

        final PinView pinView = findViewById(R.id.pinView);
      //  pinView.setTextColor(ResourcesCompat.getColor(getResources(), R.color.black, getTheme()));
      //  pinView.setTextColor(ResourcesCompat.getColorStateList(getResources(), R.color.black, getTheme()));
      //  pinView.setLineColor(ResourcesCompat.getColor(getResources(), R.color.black, getTheme()));
        pinView.setLineColor(ResourcesCompat.getColorStateList(getResources(), R.color.neopurple, getTheme()));
        pinView.setItemCount(4);
        pinView.setAnimationEnable(true);// start animation when adding text
        pinView.setCursorVisible(true);
     //   pinView.setCursorColor(ResourcesCompat.getColor(getResources(), R.color.black, getTheme()));
        pinView.setCursorWidth(getResources().getDimensionPixelSize(R.dimen.pv_pin_view_cursor_width));
        pinView.setCursorColor(Color.BLACK);

        submitadharotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SegmentActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        purplebackarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}