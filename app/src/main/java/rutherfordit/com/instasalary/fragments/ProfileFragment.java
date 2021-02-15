package rutherfordit.com.instasalary.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.adapters.DirectorRecyclerViewAdapter;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.Urls;
import rutherfordit.com.instasalary.extras.VolleyRequest;
import rutherfordit.com.instasalary.models.MyProfileDirectorModel;

public class ProfileFragment extends Fragment {

    ProgressDialog progressBar;
    CardView loader_profile;
    CircleImageView img;
    View v;
    SharedPreferences sharedPreferences;
    String UserAccessToken;
    LinearLayout layout_driver_profition, layout_salary_profiyion;
    TextView profile1_company_name, profile1_company_email, profile1_company_landline, profile1_company_pan, profile1_company_service;
    TextView bank_name1, bank_branch1, ac_number1, bank_ifcs1;
    List<MyProfileDirectorModel> myProfileDirectorModels;
    RecyclerView personalDeailsRec;

    String TAG = "profle";
    SharedPrefsManager sharedPrefsManager;
    VolleyRequest volleyRequest;
    String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_profile, null);

        init();
        getUserApi();

        return v;
    }

    private void init() {

        loader_profile = v.findViewById(R.id.loader_profile);
        loader_profile.setVisibility(View.VISIBLE);

        sharedPreferences = getActivity().getSharedPreferences("mySharedPreference", Context.MODE_PRIVATE);
        UserAccessToken = "Bearer " + sharedPreferences.getString("AccessToken", "");

        Log.e(TAG, "init: " + UserAccessToken);


        img = v.findViewById(R.id.img);
        profile1_company_name = v.findViewById(R.id.profile1_company_name);
        profile1_company_email = v.findViewById(R.id.profile1_company_email);
        profile1_company_landline = v.findViewById(R.id.profile1_company_landline);
        profile1_company_pan = v.findViewById(R.id.profile1_company_pan);
        profile1_company_service = v.findViewById(R.id.profile1_company_service);

        bank_name1 = v.findViewById(R.id.bank_name1);
        bank_branch1 = v.findViewById(R.id.bank_branch1);
        ac_number1 = v.findViewById(R.id.ac_number1);
        bank_ifcs1 = v.findViewById(R.id.bank_ifcs1);
        personalDeailsRec = v.findViewById(R.id.personalDeailsRec);

        myProfileDirectorModels = new ArrayList<>();

        layout_salary_profiyion = v.findViewById(R.id.salary_profition);

        sharedPrefsManager = new SharedPrefsManager(getContext());
        token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIyIiwianRpIjoiZDhiMGJhYjQzMmY0ZGNmYzExZjgwZDQ1MDNhOTVjMTljMjkwMjc2NjlhMTdlNWE0NzkxZGQxOWFjOWZiOGM0N2I0Zjg0YTE0Yzg3MzY1MzUiLCJpYXQiOjE2MTMxMjM1OTcsIm5iZiI6MTYxMzEyMzU5NywiZXhwIjoxNjQ0NjU5NTk3LCJzdWIiOiIyNiIsInNjb3BlcyI6WyIqIl19.As4N8G7d55PikPs9XGVRFv1X1LywpQyUVikfXIvxrNdLpi_W0KRq6T7DTM4dWKM2SN9wkZP15Bny_WwN4fXBrDol1M84dvjT_Hs7nQwxKNrFBv3FU6H5quedwwYrkI-F-RXanmXyS4wVOSdo44M3ArdxyA4GBvoFMa4MfmUx3lpjUN-X4lnAZ4_MBzQ06sw0rskI9VdfEAFPHHRVLbYsPYgV89T4sM5N0OZJ7fVvc4xJv8uvh-onv7reCFwZ-d9gEWNTmlmEzIxlfdzsSlj5Q_u4JfLF4g4gT_y_0SJuk2tyir3ml4DAawfa4EnGDPh1ITt4DfVNzb0iI8z0aRZLAOEPxbpkM8bsci-HucnSe4ZoQOkFyd49fnX2R_dLrgEsOn2VFgOvhgPPFg7VohFAZNpG5zQiA1po2QShIBIyxbXXV6zabxeHi4l7vekLd0jgNAM1fH7jfbGRCyl_HiTA-_HQYS6ZxS75hajcb0RRmnWmgRMp7wZev28UQVFQpj9DWfhhcEWKPB_GCTZhJSFOYc-XvjKJRpUI4kRosZ86cInKgbNW6BZ75b_lJK-Iegnz_lLvUpa3pd1hOQf5DijA3SIZZPzN2C4gg2Cvlq1-RUQsoCOFv7HlzPAqkUzWgivZIl6fYIyQ-3ChwYQTMkXlSQ8tWcapEFMDeaqgkRR6BlA";

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        personalDeailsRec.setLayoutManager(linearLayoutManager);
    }

    private void getUserApi() {

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Urls.GET_USER, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("response", "response: " + response );

                    try {
                        JSONObject jsonObjectData = response.getJSONObject("data");

                        JSONObject jsonObjectCompany = jsonObjectData.getJSONObject("companydetails");

                        JSONArray arrayCompany = jsonObjectCompany.getJSONArray("data");

                        for (int i = 0; i < arrayCompany.length(); i++){

                            JSONObject jsonObjectI = arrayCompany.getJSONObject(i);

                            profile1_company_name.setText(jsonObjectI.getString("name"));
                            profile1_company_landline.setText(jsonObjectI.getString("landline_number"));
                            profile1_company_pan.setText(jsonObjectI.getString("pan_card"));
                            profile1_company_email.setText(jsonObjectI.getString("pan_card"));
                            profile1_company_service.setText(jsonObjectI.getString("type_of_services"));

                        }

                        JSONObject jsonObjectBank = jsonObjectData.getJSONObject("bankdetails");
                        Log.e("jsonObjectBank", "jsonObjectBank: " + jsonObjectBank );

                        JSONObject jsonObjectBankData = jsonObjectBank.getJSONObject("data");

                        bank_name1.setText(jsonObjectBankData.getString("bank_name"));
                        bank_branch1.setText(jsonObjectBankData.getString("bank_branch"));
                        ac_number1.setText(jsonObjectBankData.getString("ac_number"));
                        bank_ifcs1.setText(jsonObjectBankData.getString("bank_ifcs"));

                        JSONObject jsonObjectDirector = jsonObjectData.getJSONObject("directorpartner");
                        Log.e("jsonObjectDirector", "jsonObjectDirector: " + jsonObjectDirector );

                        JSONArray jsonArrayData = jsonObjectDirector.getJSONArray("data");
                        Log.e("jsonArrayData", "jsonArrayData: " + jsonArrayData );

                        for (int i = 0; i < jsonArrayData.length(); i++){

                            JSONObject jsonDataI = jsonArrayData.getJSONObject(i);

                            MyProfileDirectorModel obj = new MyProfileDirectorModel();

                            obj.setName(jsonDataI.getString("name"));
                            obj.setEmail(jsonDataI.getString("email"));
                            obj.setMobile_phone(jsonDataI.getString("mobile_number"));
                            obj.setAadhar(jsonDataI.getString("aadhar"));
                            obj.setPan_card(jsonDataI.getString("pan_card"));
                            obj.setCurrent_address(jsonDataI.getString("current_address"));
                            obj.setDob(jsonDataI.getString("dob"));

                            myProfileDirectorModels.add(obj);
                        }

                        DirectorRecyclerViewAdapter directorRecyclerViewAdapter = new DirectorRecyclerViewAdapter(getContext(), myProfileDirectorModels);
                        personalDeailsRec.setAdapter(directorRecyclerViewAdapter);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("onErrorResponse", "onErrorResponse: " + error.getLocalizedMessage() );
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", token);

                    return headers;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(jsonObjectRequest);
        }

}
