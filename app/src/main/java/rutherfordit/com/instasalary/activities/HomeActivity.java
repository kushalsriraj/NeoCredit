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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.digio.in.esign2sdk.Digio;
import com.digio.in.esign2sdk.DigioConfig;
import com.digio.in.esign2sdk.DigioEnvironment;
import com.digio.in.esign2sdk.DigioResponseListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.Urls;
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
import rutherfordit.com.instasalary.myinterfaces.CreateMandate;

public class HomeActivity extends AppCompatActivity implements LoadDetailedData, CreateMandate, DigioResponseListener {

    String id, user_id, proof_type, image;
    TextView maintext, dashboardtext, loantext, faqtext, name_text;
    DrawerLayout drawer;
    Snackbar snackbar;
    NavigationView nav_view;
    ImageView slider, dashboardimage, loanimage, faqimage;
    FrameLayout homescreenframe;
    LinearLayout dashboardbutton, loanbutton, faqbutton;
    Fragment fragment;
    View headerview;
    LinearLayout gotoprofile, gotosettings, gotorefer, gotosupport, gotodisclaimer, gototerms, gotoabout, logout;
    String UserAccessToken;
    CircleImageView profile_image;
    JSONObject data;
    SharedPrefsManager sharedPrefsManager;
    String conf = "";

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

        sharedPrefsManager = new SharedPrefsManager(getApplicationContext());
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

                sharedPrefsManager.setLoggedIn(false);

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

        dashboardimage.setImageResource(R.drawable.greendashboard);
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

    @Override
    public void create() {
        createMandateForm();
    }

    private void createMandateForm() {

        JSONObject jsonObject = new JSONObject();

        JSONObject mandate_obj = new JSONObject();

        try {
            mandate_obj.put("maximum_amount", "26000");
            mandate_obj.put("instrument_type", "debit");
            mandate_obj.put("first_collection_date", "2021-01-10");
            mandate_obj.put("is_recurring", "false");
            mandate_obj.put("frequency", "Monthly");
            mandate_obj.put("management_category", "L001");
            mandate_obj.put("customer_name", "kushal");
            mandate_obj.put("customer_account_number", "62207117005");
            mandate_obj.put("destination_bank_id", "SBIN0020061");
            mandate_obj.put("customer_account_type", "savings");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonObject.put("customer_identifier", "9030007890");
            jsonObject.put("auth_mode", "esign");
            jsonObject.put("mandate_type", "create");
            jsonObject.put("corporate_config_id", "TSE2101051408016871BKE4F721CVNZ2");
            jsonObject.put("mandate_data", mandate_obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("jsonObj", "mandateFormCreate: " + jsonObject);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.REQUEST_MANDATE_FORM, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.e("response", "onResponse: " + response);

                try {

                    conf = "abcd";
                    String m_Id = response.getString("mandate_id");
                    digioMandateSigningRequest(m_Id);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("volleyError", "onErrorResponse: " + error.getLocalizedMessage());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Basic QUlZODc1QjFZQjhFVTQxWTFaWlE3MUdSMUhQSjVEWDc6V05SVEQ4VVVTSkQ5UDgzS1RRWEkxU1g3NU1NQzNWVzY=");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jsonObjectRequest);

    }

    private void digioMandateSigningRequest(String m_Id) {

        // progressDialog.cancel();

        Log.e("mid", "digioSigningRequest: " + m_Id);

        Digio digio = new Digio();
        DigioConfig digioConfig = new DigioConfig();
        digioConfig.setLogo("https://cdn.logo.com/hotlink-ok/logo-social.png"); //Your company logo
        digioConfig.setEnvironment(DigioEnvironment.STAGE);   //Stage is sandbox

        try {
            digio.init(HomeActivity.this, digioConfig);
            digio.esign(m_Id, "9030007890");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onSigningSuccess(String s, String s1) {

        Toast.makeText(getApplicationContext(),"Success..",Toast.LENGTH_SHORT).show();
        Log.e("Signing", "onSigningSuccess: " + "s " + s + "s1" + s1);

        if (conf.equals("abcd"))
        {
            docUpload();
        }
    }

    public void onSigningFailure(String documentId, int code, String response){
        Toast.makeText(getApplicationContext(),"Failure..",Toast.LENGTH_SHORT).show();
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
    }

    private void docUpload()
    {

        JSONObject jsonObject = new JSONObject();

        try
        {
            JSONArray jsonArray = new JSONArray();

            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("identifier","9030007890");

            jsonArray.put(jsonObject1);

            jsonObject.put("signers",jsonArray);

            jsonObject.put("file_name","abcd.pdf");
            jsonObject.put("file_data","JVBERi0xLjMNCiXi48/TDQoNCjEgMCBvYmoNCjw8DQovVHlwZSAvQ2F0YWxvZw0KL091dGxpbmVzIDIgMCBSDQovUGFnZXMgMyAwIFINCj4+DQplbmRvYmoNCg0KMiAwIG9iag0KPDwNCi9UeXBlIC9PdXRsaW5lcw0KL0NvdW50IDANCj4+DQplbmRvYmoNCg0KMyAwIG9iag0KPDwNCi9UeXBlIC9QYWdlcw0KL0NvdW50IDINCi9LaWRzIFsgNCAwIFIgNiAwIFIgXSANCj4+DQplbmRvYmoNCg0KNCAwIG9iag0KPDwNCi9UeXBlIC9QYWdlDQovUGFyZW50IDMgMCBSDQovUmVzb3VyY2VzIDw8DQovRm9udCA8PA0KL0YxIDkgMCBSIA0KPj4NCi9Qcm9jU2V0IDggMCBSDQo+Pg0KL01lZGlhQm94IFswIDAgNjEyLjAwMDAgNzkyLjAwMDBdDQovQ29udGVudHMgNSAwIFINCj4+DQplbmRvYmoNCg0KNSAwIG9iag0KPDwgL0xlbmd0aCAxMDc0ID4+DQpzdHJlYW0NCjIgSg0KQlQNCjAgMCAwIHJnDQovRjEgMDAyNyBUZg0KNTcuMzc1MCA3MjIuMjgwMCBUZA0KKCBBIFNpbXBsZSBQREYgRmlsZSApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDY4OC42MDgwIFRkDQooIFRoaXMgaXMgYSBzbWFsbCBkZW1vbnN0cmF0aW9uIC5wZGYgZmlsZSAtICkgVGoNCkVUDQpCVA0KL0YxIDAwMTAgVGYNCjY5LjI1MDAgNjY0LjcwNDAgVGQNCigganVzdCBmb3IgdXNlIGluIHRoZSBWaXJ0dWFsIE1lY2hhbmljcyB0dXRvcmlhbHMuIE1vcmUgdGV4dC4gQW5kIG1vcmUgKSBUag0KRVQNCkJUDQovRjEgMDAxMCBUZg0KNjkuMjUwMCA2NTIuNzUyMCBUZA0KKCB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDYyOC44NDgwIFRkDQooIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlICkgVGoNCkVUDQpCVA0KL0YxIDAwMTAgVGYNCjY5LjI1MDAgNjE2Ljg5NjAgVGQNCiggdGV4dC4gQW5kIG1vcmUgdGV4dC4gQm9yaW5nLCB6enp6ei4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kICkgVGoNCkVUDQpCVA0KL0YxIDAwMTAgVGYNCjY5LjI1MDAgNjA0Ljk0NDAgVGQNCiggbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDU5Mi45OTIwIFRkDQooIEFuZCBtb3JlIHRleHQuIEFuZCBtb3JlIHRleHQuICkgVGoNCkVUDQpCVA0KL0YxIDAwMTAgVGYNCjY5LjI1MDAgNTY5LjA4ODAgVGQNCiggQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgKSBUag0KRVQNCkJUDQovRjEgMDAxMCBUZg0KNjkuMjUwMCA1NTcuMTM2MCBUZA0KKCB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBFdmVuIG1vcmUuIENvbnRpbnVlZCBvbiBwYWdlIDIgLi4uKSBUag0KRVQNCmVuZHN0cmVhbQ0KZW5kb2JqDQoNCjYgMCBvYmoNCjw8DQovVHlwZSAvUGFnZQ0KL1BhcmVudCAzIDAgUg0KL1Jlc291cmNlcyA8PA0KL0ZvbnQgPDwNCi9GMSA5IDAgUiANCj4+DQovUHJvY1NldCA4IDAgUg0KPj4NCi9NZWRpYUJveCBbMCAwIDYxMi4wMDAwIDc5Mi4wMDAwXQ0KL0NvbnRlbnRzIDcgMCBSDQo+Pg0KZW5kb2JqDQoNCjcgMCBvYmoNCjw8IC9MZW5ndGggNjc2ID4+DQpzdHJlYW0NCjIgSg0KQlQNCjAgMCAwIHJnDQovRjEgMDAyNyBUZg0KNTcuMzc1MCA3MjIuMjgwMCBUZA0KKCBTaW1wbGUgUERGIEZpbGUgMiApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDY4OC42MDgwIFRkDQooIC4uLmNvbnRpbnVlZCBmcm9tIHBhZ2UgMS4gWWV0IG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gKSBUag0KRVQNCkJUDQovRjEgMDAxMCBUZg0KNjkuMjUwMCA2NzYuNjU2MCBUZA0KKCBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSB0ZXh0LiBBbmQgbW9yZSApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDY2NC43MDQwIFRkDQooIHRleHQuIE9oLCBob3cgYm9yaW5nIHR5cGluZyB0aGlzIHN0dWZmLiBCdXQgbm90IGFzIGJvcmluZyBhcyB3YXRjaGluZyApIFRqDQpFVA0KQlQNCi9GMSAwMDEwIFRmDQo2OS4yNTAwIDY1Mi43NTIwIFRkDQooIHBhaW50IGRyeS4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gQW5kIG1vcmUgdGV4dC4gKSBUag0KRVQNCkJUDQovRjEgMDAxMCBUZg0KNjkuMjUwMCA2NDAuODAwMCBUZA0KKCBCb3JpbmcuICBNb3JlLCBhIGxpdHRsZSBtb3JlIHRleHQuIFRoZSBlbmQsIGFuZCBqdXN0IGFzIHdlbGwuICkgVGoNCkVUDQplbmRzdHJlYW0NCmVuZG9iag0KDQo4IDAgb2JqDQpbL1BERiAvVGV4dF0NCmVuZG9iag0KDQo5IDAgb2JqDQo8PA0KL1R5cGUgL0ZvbnQNCi9TdWJ0eXBlIC9UeXBlMQ0KL05hbWUgL0YxDQovQmFzZUZvbnQgL0hlbHZldGljYQ0KL0VuY29kaW5nIC9XaW5BbnNpRW5jb2RpbmcNCj4+DQplbmRvYmoNCg0KMTAgMCBvYmoNCjw8DQovQ3JlYXRvciAoUmF2ZSBcKGh0dHA6Ly93d3cubmV2cm9uYS5jb20vcmF2ZVwpKQ0KL1Byb2R1Y2VyIChOZXZyb25hIERlc2lnbnMpDQovQ3JlYXRpb25EYXRlIChEOjIwMDYwMzAxMDcyODI2KQ0KPj4NCmVuZG9iag0KDQp4cmVmDQowIDExDQowMDAwMDAwMDAwIDY1NTM1IGYNCjAwMDAwMDAwMTkgMDAwMDAgbg0KMDAwMDAwMDA5MyAwMDAwMCBuDQowMDAwMDAwMTQ3IDAwMDAwIG4NCjAwMDAwMDAyMjIgMDAwMDAgbg0KMDAwMDAwMDM5MCAwMDAwMCBuDQowMDAwMDAxNTIyIDAwMDAwIG4NCjAwMDAwMDE2OTAgMDAwMDAgbg0KMDAwMDAwMjQyMyAwMDAwMCBuDQowMDAwMDAyNDU2IDAwMDAwIG4NCjAwMDAwMDI1NzQgMDAwMDAgbg0KDQp0cmFpbGVyDQo8PA0KL1NpemUgMTENCi9Sb290IDEgMCBSDQovSW5mbyAxMCAwIFINCj4+DQoNCnN0YXJ0eHJlZg0KMjcxNA0KJSVFT0YNCg==");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.UPLOAD_PDF_ESIGN, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.e("resp", "onResponse: " + response );

                try {
                    String doc_Id = response.getString("id");

                    digioEsignRequest(doc_Id);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("volleyError", "onErrorResponse: " + error.getLocalizedMessage() );

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Basic QUlZODc1QjFZQjhFVTQxWTFaWlE3MUdSMUhQSjVEWDc6V05SVEQ4VVVTSkQ5UDgzS1RRWEkxU1g3NU1NQzNWVzY=");
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(HomeActivity.this);
        requestQueue.add(jsonObjectRequest);
    }

    private void digioEsignRequest(String DocID)
    {

     //   progressDialog.cancel();

        conf= "";

        Digio digio = new Digio();
        DigioConfig digioConfig = new DigioConfig();
        digioConfig.setLogo("www.your-website/logo-image"); //Your company logo
        digioConfig.setEnvironment(DigioEnvironment.STAGE);   //Stage is sandbox
        // digioConfig.setServiceMode(DigioServiceMode.FP);//FP is fingerprint/OTP/IRIS

        try {
            digio.init(HomeActivity.this, digioConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            digio.esign(DocID, "9030007890");// this refers DigioResponseListener
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
