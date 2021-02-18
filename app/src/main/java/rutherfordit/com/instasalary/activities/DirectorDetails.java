 package rutherfordit.com.instasalary.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import es.dmoral.toasty.Toasty;
import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.activities.partnership.PartnershipDocUploadActivity;
import rutherfordit.com.instasalary.activities.privatelimited.PrivateLimitedDocUploadActivity;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.VolleyRequest;

public class DirectorDetails extends AppCompatActivity {

    int LAUNCH_SECOND_ACTIVITY = 1;
    LinearLayout ll_private, ll_partnership;
    VolleyRequest volleyRequest;
    SharedPrefsManager sharedPrefsManager;
    RelativeLayout add_director1, add_director2, add_partner1, add_partner2;
    boolean dir1 , dir2 , part1, part2;
    RelativeLayout Submit_director, partnerButton;
    ImageView pl1_before_details, pl1_after_details,
            pl2_before_details, pl2_after_details,
            pp1_before_details, pp1_after_details,
            pp2_before_details, pp2_after_details;

    @Override
    public void onBackPressed() {

        Toasty.error(getApplicationContext(), "Action denied.", Toast.LENGTH_SHORT).show();
        Toasty.error(getApplicationContext(), "Please proceed further to Process your application form.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_director_detatils);

        ll_private = findViewById(R.id.ll_private);
        ll_partnership = findViewById(R.id.ll_partnership);
        volleyRequest = new VolleyRequest();
        sharedPrefsManager = new SharedPrefsManager(getApplicationContext());
        add_director1 = findViewById(R.id.add_director1);
        add_director2 = findViewById(R.id.add_director2);
        add_partner1 = findViewById(R.id.add_partner1);
        add_partner2 = findViewById(R.id.add_partner2);
        Submit_director = findViewById(R.id.Submit_director);
        partnerButton = findViewById(R.id.partnerButton);

        pl1_before_details = findViewById(R.id.pl1_before_details);
        pl1_after_details = findViewById(R.id.pl1_after_details);

        pl2_before_details = findViewById(R.id.pl2_before_details);
        pl2_after_details = findViewById(R.id.pl2_after_details);

        pp1_before_details = findViewById(R.id.pp1_before_details);
        pp1_after_details = findViewById(R.id.pp1_after_details);

        pp2_before_details = findViewById(R.id.pp2_before_details);
        pp2_after_details = findViewById(R.id.pp2_after_details);

        sharedPrefsManager.setCHECK_PAGE("10");

        Log.e("getsegment", "onCreate: " + sharedPrefsManager.getSegment() );

        if (sharedPrefsManager.getSegment().equals("2")){

            ll_private.setVisibility(View.VISIBLE);
            ll_partnership.setVisibility(View.GONE);


        }
        else if (sharedPrefsManager.getSegment().equals("3")){

            ll_private.setVisibility(View.GONE);
            ll_partnership.setVisibility(View.VISIBLE);
        }

        add_director1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DirectorDetails.this, PromoterDetails.class);
                i.putExtra("id","1");
                startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
            }
        });

        add_director2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DirectorDetails.this, PromoterDetails.class);
                i.putExtra("id","2");
                startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
            }
        });

        add_partner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DirectorDetails.this, PromoterDetails.class);
                i.putExtra("id","3");
                startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
            }
        });

        add_partner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DirectorDetails.this, PromoterDetails.class);
                i.putExtra("id","4");
                startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
            }
        });

        Submit_director.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dir1 && dir2)
                {
                    Intent i = new Intent(getApplicationContext(),PrivateLimitedDocUploadActivity.class);
                    startActivity(i);
                }
                else if (dir1)
                {
                    Toast.makeText(getApplicationContext(),"Please Fill director 2 Details.",Toast.LENGTH_SHORT).show();
                }
                else if (dir2)
                {
                    Toast.makeText(getApplicationContext(),"Please Fill director 1 Details.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please Fill Details.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        partnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (part1 && part2)
                {
                    Intent i = new Intent(getApplicationContext(),PartnershipDocUploadActivity.class);
                    startActivity(i);
                }
                else if (part1)
                {
                    Toast.makeText(getApplicationContext(),"Please Fill Partner 2 Details.",Toast.LENGTH_SHORT).show();
                }
                else if (part2)
                {
                    Toast.makeText(getApplicationContext(),"Please Fill Partner 1 Details.",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please Fill Details.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == LAUNCH_SECOND_ACTIVITY) {

            if(resultCode == Activity.RESULT_OK)
            {
                String result = data.getStringExtra("result");

                Log.e("result", "onActivityResult:  " + result );

                if (result.equals("1"))
                {

                    pl1_before_details.setVisibility(View.GONE);
                    pl1_after_details.setVisibility(View.VISIBLE);
                    dir1 = true;

                    Log.e("dir1", "onActivityResult: " + dir1 );
                    add_director1.setClickable(false);

                    if (dir1 && dir2)
                    {
                        pl2_before_details.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Director 1 & 2 details Uploaded.",Toast.LENGTH_SHORT).show();
                    }
                }
                else if (result.equals("2"))
                {
                    dir2 = true;
                    pl2_before_details.setVisibility(View.GONE);
                    pl2_after_details.setVisibility(View.VISIBLE);
                    Log.e("dir2", "onActivityResult: " + dir2 );
                    add_director2.setClickable(false);

                    if (dir1 && dir2)
                    {
                        Submit_director.setBackground(getDrawable(R.drawable.gradient_neocredit));

                    }

                }
                else if (result.equals("3"))
                {
                    part1 = true;
                    pp1_before_details.setVisibility(View.GONE);
                    pp1_after_details.setVisibility(View.VISIBLE);
                    Log.e("part1", "onActivityResult: " + part1 );
                    add_partner1.setClickable(false);
                    if (part1 && part2)
                    {
                        Toast.makeText(getApplicationContext(),"Partner 1 & 2 details Uploaded.",Toast.LENGTH_SHORT).show();
                    }
                }
                else if (result.equals("4"))
                {
                    part2 = true;
                    pp2_before_details.setVisibility(View.GONE);
                    pp2_after_details.setVisibility(View.VISIBLE);
                    Log.e("part2", "onActivityResult: " + part2 );
                    add_partner2.setClickable(false);
                    if (part1 && part2)
                    {
                        partnerButton.setBackground(getDrawable(R.drawable.gradient_neocredit));

                    }
                }
            }
            else if (resultCode == Activity.RESULT_CANCELED) {

                Toasty.error(getApplicationContext(),"Cancelled..",Toasty.LENGTH_SHORT).show();

            }
        }

    }
}