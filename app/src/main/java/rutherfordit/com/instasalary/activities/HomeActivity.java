package rutherfordit.com.instasalary.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;
import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.fragments.AboutFragment;
import rutherfordit.com.instasalary.fragments.DashboardFragment;
import rutherfordit.com.instasalary.fragments.DetailedFragment;
import rutherfordit.com.instasalary.fragments.DisclaimerFragment;
import rutherfordit.com.instasalary.fragments.FaqFragment;
import rutherfordit.com.instasalary.fragments.LoansFragment;
import rutherfordit.com.instasalary.fragments.ProfileFragment;
import rutherfordit.com.instasalary.fragments.ReferFragment;
import rutherfordit.com.instasalary.fragments.SettingsFragment;
import rutherfordit.com.instasalary.fragments.SupportFragment;
import rutherfordit.com.instasalary.fragments.TermsFragment;
import rutherfordit.com.instasalary.myinterfaces.LoadDetailedData;

public class HomeActivity extends AppCompatActivity implements LoadDetailedData {

    String id, user_id, proof_type, image;
    TextView maintext, dashboardtext, loantext, faqtext,name_text;
    DrawerLayout drawer;
    Snackbar snackbar;
    NavigationView nav_view;
    ImageView slider, dashboardimage, loanimage, faqimage;
    FrameLayout homescreenframe;
    LinearLayout dashboardbutton, loanbutton, faqbutton;
    Fragment fragment;
    View headerview;
    LinearLayout gotoprofile, gotosettings, gotorefer, gotosupport, gotodisclaimer, gototerms, gotoabout, logout;
    SharedPreferences sharedPreferences;
    String UserAccessToken;
    CircleImageView profile_image;
    JSONObject data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        Log.d("debug", "Screen inches : " + screenInches);
    }

    private void init() {

        sharedPreferences = getSharedPreferences("mySharedPreference", Context.MODE_PRIVATE);
        UserAccessToken = "Bearer " + sharedPreferences.getString("AccessToken", "");

        drawer = findViewById(R.id.drawer);
        nav_view = findViewById(R.id.nav_view);

        slider = findViewById(R.id.slider);
        slider.setTag("menu");
        maintext = findViewById(R.id.maintext);
        homescreenframe = findViewById(R.id.homescreenframe);

        dashboardbutton = findViewById(R.id.dashboardbutton);
        loanbutton = findViewById(R.id.loanbutton);
        faqbutton = findViewById(R.id.faqbutton);

        dashboardimage = findViewById(R.id.dashboardimage);
        loanimage = findViewById(R.id.loanimage);
        faqimage = findViewById(R.id.faqimage);

        dashboardtext = findViewById(R.id.dashboardtext);
        loantext = findViewById(R.id.loantext);
        faqtext = findViewById(R.id.faqtext);

        headerview = nav_view.getHeaderView(0);
        gotoprofile = headerview.findViewById(R.id.gotoprofile);
        gotosettings = headerview.findViewById(R.id.gotosettings);
        gotorefer = headerview.findViewById(R.id.gotorefer);
        gotosupport = headerview.findViewById(R.id.gotosupport);
        gotodisclaimer = headerview.findViewById(R.id.gotodisclaimer);
        gototerms = headerview.findViewById(R.id.gototerms);
        gotoabout = headerview.findViewById(R.id.gotoabout);
        logout = headerview.findViewById(R.id.logout);
        profile_image = headerview.findViewById(R.id.profile_image);
        name_text = headerview.findViewById(R.id.name_text);
        name_text.setText(sharedPreferences.getString("full_name",""));

        snackbar = Snackbar.make(drawer, "Press Again To Exit", Snackbar.LENGTH_SHORT);

        fragment = new DashboardFragment();
        replaceFragment(fragment, "dashboard");
        dashboardbutton.setClickable(false);
        loanbutton.setClickable(true);
        faqbutton.setClickable(false);

        gotoprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardbutton.setClickable(true);
                ProfileFragment profilefragment = new ProfileFragment();
                drawer.closeDrawer(GravityCompat.START);
                replaceFragment(profilefragment, "profile");
              //  profilefragment.senddata(data);
                slider.setImageResource(R.drawable.menu);
                slider.setTag("menu");
                maintext.setText("Profile");
                allwhiteicons();
            }
        });

        gotosettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardbutton.setClickable(true);
                fragment = new SettingsFragment();
                replaceFragment(fragment, "settings");
                drawer.closeDrawer(GravityCompat.START);
                slider.setImageResource(R.drawable.menu);
                slider.setTag("menu");
                maintext.setText("Settings");
                allwhiteicons();
            }
        });

        gotorefer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardbutton.setClickable(true);
                fragment = new ReferFragment();
                replaceFragment(fragment, "refer");
                drawer.closeDrawer(GravityCompat.START);
                slider.setImageResource(R.drawable.menu);
                slider.setTag("menu");
                maintext.setText("Referal");
                allwhiteicons();
            }
        });

        gotosupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardbutton.setClickable(true);
                fragment = new SupportFragment();
                replaceFragment(fragment, "support");
                slider.setImageResource(R.drawable.menu);
                slider.setTag("menu");
                drawer.closeDrawer(GravityCompat.START);
                maintext.setText("Support");
                allwhiteicons();
            }
        });

        gotodisclaimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardbutton.setClickable(true);
                fragment = new DisclaimerFragment();
                replaceFragment(fragment, "disc");
                drawer.closeDrawer(GravityCompat.START);
                slider.setImageResource(R.drawable.menu);
                slider.setTag("menu");
                maintext.setText("Disclaimer");
                allwhiteicons();
            }
        });

        gototerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardbutton.setClickable(true);
                fragment = new TermsFragment();
                replaceFragment(fragment, "terms");
                drawer.closeDrawer(GravityCompat.START);
                slider.setImageResource(R.drawable.menu);
                slider.setTag("menu");
                maintext.setText("Terms And Conditions");
                allwhiteicons();
            }
        });

        gotoabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dashboardbutton.setClickable(true);
                fragment = new AboutFragment();
                replaceFragment(fragment, "about");
                slider.setImageResource(R.drawable.menu);
                slider.setTag("menu");
                drawer.closeDrawer(GravityCompat.START);
                maintext.setText("About");
                allwhiteicons();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("loggedin", "false");
                editor.apply();

                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });


        slider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (slider.getTag().equals("back")) {
                    onBackPressed();
                } else if (slider.getTag().equals("menu")) {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });

        dashboardbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mydashboard();

            }
        });

        loanbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                maintext.setText("Loan History");

                dashboardbutton.setClickable(true);
                loanbutton.setClickable(false);
                faqbutton.setClickable(true);

                slider.setImageResource(R.drawable.menu);
                slider.setTag("menu");

                dashboardimage.setImageResource(R.drawable.whitedashboard);
                dashboardtext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

                loanimage.setImageResource(R.drawable.redhistory);
                loantext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.neopurple));

                faqimage.setImageResource(R.drawable.whitehelp);
                faqtext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

                fragment = new LoansFragment();
                replaceFragment(fragment, "loan");
            }
        });

        faqbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dashboardbutton.setClickable(true);
                loanbutton.setClickable(true);
                faqbutton.setClickable(false);

                maintext.setText("Frequently Asked Questions");

                slider.setImageResource(R.drawable.menu);
                slider.setTag("menu");

                dashboardimage.setImageResource(R.drawable.whitedashboard);
                dashboardtext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

                loanimage.setImageResource(R.drawable.whitehistory);
                loantext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

                faqimage.setImageResource(R.drawable.redhelp);
                faqtext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.neopurple));

                fragment = new FaqFragment();
                replaceFragment(fragment, "faq");

            }
        });
    }

    private void mydashboard() {
        maintext.setText("My Dashboard");

        slider.setImageResource(R.drawable.menu);
        slider.setTag("menu");

        dashboardbutton.setClickable(false);
        loanbutton.setClickable(true);
        faqbutton.setClickable(true);

        dashboardimage.setImageResource(R.drawable.reddashboard);
        dashboardtext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.neopurple));

        loanimage.setImageResource(R.drawable.whitehistory);
        loantext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

        faqimage.setImageResource(R.drawable.whitehelp);
        faqtext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

        fragment = new DashboardFragment();
        replaceFragment(fragment, "dashboard");
    }

    public void replaceFragment(Fragment someFragment, String tag) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_in_right);
        transaction.replace(R.id.homescreenframe, someFragment);

        if (tag.equals("details")) {
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_left);
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public void allwhiteicons() {

        slider.setImageResource(R.drawable.menu);
        slider.setTag("menu");

        dashboardimage.setImageResource(R.drawable.whitedashboard);
        dashboardtext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

        loanimage.setImageResource(R.drawable.whitehistory);
        loantext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

        faqimage.setImageResource(R.drawable.whitehelp);
        faqtext.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.black));

    }

    @Override
    public void loaddetails(String id) {

        maintext.setText("Loan Details");

        DetailedFragment detailedFragment = new DetailedFragment();
        fragment = new DetailedFragment();
      //  detailedFragment.setdata(id);
        replaceFragment(detailedFragment, "details");
        slider.setImageResource(R.drawable.blackbackarrow);
        slider.setTag("back");

    }

    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            if (snackbar.isShown()) {
                finishAffinity();
            } else {
                snackbar.show();
            }
        } else {
            getSupportFragmentManager().popBackStack();
            mydashboard();
        }
    }
}
