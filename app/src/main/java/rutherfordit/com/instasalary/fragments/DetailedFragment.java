package rutherfordit.com.instasalary.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import rutherfordit.com.instasalary.R;

public class DetailedFragment extends Fragment {

    View v;
    private String detid = "0", UserAccessToken;
    private TextView detail_heading, detail_date, detail_amount, detail_interest, detail_repay_date, detail_status;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_detailed, null);

        init();

        return v;
    }

    private void init() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mySharedPreference", Context.MODE_PRIVATE);
        UserAccessToken = "Bearer " + sharedPreferences.getString("AccessToken", "");

        detail_heading = v.findViewById(R.id.detail_heading);
        detail_date = v.findViewById(R.id.detail_date);
        detail_amount = v.findViewById(R.id.detail_amount);
        detail_interest = v.findViewById(R.id.detail_interest);
        detail_repay_date = v.findViewById(R.id.detail_repay_date);
        detail_status = v.findViewById(R.id.detail_status);

        Log.e("detfrag", "init:  " + detid);
    }
}
