package rutherfordit.com.instasalary.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.adapters.DashBoardAdapter;

public class DashboardFragment extends Fragment {

    View v;
    RecyclerView recdashboard;
    TextView emptydash;
    RelativeLayout apply_new_Loan;
    EditText enter_purpose;
    ImageView cancel_dialog;
    Button apply_for_loan;
    LinearLayout application_success, application_failure;
    DashBoardAdapter dashBoardAdapter;
    CardView loader_dashboard;
    private String UserAccessToken;
    ArrayList loanName = new ArrayList<>(Arrays.asList("Education Loan","Personal Loan"));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_dashboard, null);

        init();

        return v;
    }

    private void init() {

        loader_dashboard = v.findViewById(R.id.loader_dashboard);
        loader_dashboard.setVisibility(View.GONE);

        recdashboard = v.findViewById(R.id.recdashboard);
        emptydash = v.findViewById(R.id.emptydash);
        emptydash.setVisibility(View.GONE);
        recdashboard.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recdashboard.setLayoutManager(linearLayoutManager);

        DashBoardAdapter dashBoardAdapter = new DashBoardAdapter(getActivity().getApplicationContext(), loanName);
        recdashboard.setAdapter(dashBoardAdapter);

    }

    private void showdialog() {

        final Dialog dialog = new Dialog(requireActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        cancel_dialog = dialog.findViewById(R.id.cancel_dialog);
        enter_purpose = dialog.findViewById(R.id.enter_purpose);
        apply_for_loan = dialog.findViewById(R.id.apply_for_loan);
        application_failure = dialog.findViewById(R.id.application_failure);
        application_success = dialog.findViewById(R.id.application_success);

        apply_for_loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        dialog.show();

        cancel_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (application_success.getVisibility() == View.VISIBLE) {
                    dialog.cancel();
                } else if (application_success.getVisibility() == View.GONE) {
                    dialog.cancel();
                }
            }
        });
    }
}
