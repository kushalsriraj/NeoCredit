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

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;
import rutherfordit.com.instasalary.R;

public class ProfileFragment extends Fragment {

    ProgressDialog progressBar;
    CardView loader_profile;
    CircleImageView img;
    View v;
    SharedPreferences sharedPreferences;
    String UserAccessToken;
    LinearLayout layout_driver_profition, layout_salary_profiyion;
    TextView profile_doorno, profile_street, profile_state, profile_city, profile_pincode,
            curr_profile_doorno, curr_profile_street, curr_profile_state, curr_profile_city, profile_c_name, profile_c_role, profile_c_joining, profile_c_email;
    TextView profile_fullname, profile_email, profile_phone, profile_aadhar, profile_pan, profile_dob, curr_profile_pincode;
    TextView profile_c_doorno, profile_c_street, profile_c_state, profile_c_city, profile_c_pincode, profile_d_vechilenumber, profile_d_avgincome;
    JSONObject data;
    String TAG = "profle";
    String id, userid, vendor_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_profile, null);

        init();

        return v;
    }

    private void init() {

        loader_profile = v.findViewById(R.id.loader_profile);

        sharedPreferences = getActivity().getSharedPreferences("mySharedPreference", Context.MODE_PRIVATE);
        UserAccessToken = "Bearer " + sharedPreferences.getString("AccessToken", "");

        Log.e(TAG, "init: " + UserAccessToken);

        loader_profile.setVisibility(View.VISIBLE);

        img = v.findViewById(R.id.img);
        profile_fullname = v.findViewById(R.id.profile_fullname);
        profile_email = v.findViewById(R.id.profile_email);
        profile_phone = v.findViewById(R.id.profile_phone);
        profile_aadhar = v.findViewById(R.id.profile_aadhar);
        profile_pan = v.findViewById(R.id.profile_pan);
        profile_dob = v.findViewById(R.id.profile_dob);
        profile_doorno = v.findViewById(R.id.profile_doorno);
        profile_street = v.findViewById(R.id.profile_street);
        profile_state = v.findViewById(R.id.profile_state);
        profile_city = v.findViewById(R.id.profile_city);

        curr_profile_doorno = v.findViewById(R.id.curr_profile_doorno);
        curr_profile_street = v.findViewById(R.id.curr_profile_street);
        curr_profile_state = v.findViewById(R.id.curr_profile_state);
        curr_profile_city = v.findViewById(R.id.curr_profile_city);
        profile_c_name = v.findViewById(R.id.profile_c_name);
        profile_c_role = v.findViewById(R.id.profile_c_role);
        profile_c_joining = v.findViewById(R.id.profile_c_joining);
        profile_c_email = v.findViewById(R.id.profile_c_email);
        profile_c_doorno = v.findViewById(R.id.profile_c_doorno);
        profile_c_street = v.findViewById(R.id.profile_c_street);
        profile_c_city = v.findViewById(R.id.profile_c_city);
        profile_c_state = v.findViewById(R.id.profile_c_state);
        profile_c_pincode = v.findViewById(R.id.profile_c_pincode);

        layout_salary_profiyion = v.findViewById(R.id.salary_profition);
        layout_driver_profition = v.findViewById(R.id.driver_profition);

        profile_d_vechilenumber = v.findViewById(R.id.profile_d_vehiclenumber);
        profile_d_avgincome = v.findViewById(R.id.profile_d_monthlyincome);


        curr_profile_pincode = v.findViewById(R.id.curr_profile_pincode);
        profile_pincode = v.findViewById(R.id.profile_pincode);


    }
}
