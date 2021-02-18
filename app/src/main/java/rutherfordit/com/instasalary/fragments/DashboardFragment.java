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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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
import rutherfordit.com.instasalary.activities.HomeActivity;
import rutherfordit.com.instasalary.activities.PromoterDetails;
import rutherfordit.com.instasalary.adapters.DashBoardAdapter;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.Urls;
import rutherfordit.com.instasalary.extras.VolleyRequest;
import rutherfordit.com.instasalary.models.LoanListModel;

public class DashboardFragment extends Fragment {

    View v;
    VolleyRequest volleyRequest;
    SharedPrefsManager sharedPrefsManager;
    List<LoanListModel> data;
    RecyclerView recdashboard;
    TextView emptydash;
    RelativeLayout apply_new_Loan;
    EditText enter_purpose;
    ImageView cancel_dialog;
    Button apply_for_loan;
    LinearLayout application_success, application_failure;
    CardView loader_dashboard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_dashboard, null);

        init();

        return v;
    }

    private void init() {

        data = new ArrayList<>();
        sharedPrefsManager = new SharedPrefsManager(v.getContext());
        volleyRequest = new VolleyRequest();
        loader_dashboard = v.findViewById(R.id.loader_dashboard);
        loader_dashboard.setVisibility(View.GONE);

        recdashboard = v.findViewById(R.id.recdashboard);
        emptydash = v.findViewById(R.id.emptydash);
        emptydash.setVisibility(View.GONE);
        recdashboard.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recdashboard.setLayoutManager(linearLayoutManager);

        Log.e("dashboard", "init: " + sharedPrefsManager.getAccessToken() );

        request();

    }

    private void request() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Urls.LOANS_LIST, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    JSONArray array = response.getJSONArray("data");

                    Log.d("aray", "onResponse: " + array);

                    for ( int i = 0 ; i < array.length() ; i ++)
                    {
                        JSONObject obj = array.getJSONObject(i);

                        LoanListModel model = new LoanListModel();

                        model.setAmount(obj.getString("amount"));
                        model.setApplication_number(obj.getString("application_number"));
                        model.setApplication_status(obj.getString("application_status"));
                        model.setAuthoriser_status(obj.getString("authoriser_status"));
                        model.setCompany_id(obj.getString("company_id"));
                        model.setDescription(obj.getString("description"));
                        model.setId(obj.getString("id"));
                        model.setIntrest(obj.getString("intrest"));
                        model.setJoined_on(obj.getString("joined_on"));
                        model.setMaker_status(obj.getString("maker_status"));
                        model.setUser_id(obj.getString("user_id"));
                        model.setTransacation_enter_date(obj.getString("transacation_enter_date"));
                        model.setJoined_on_human(obj.getString("joined_on_human"));
                        model.setEligible_amount(obj.getString("eligible_amount"));

                        data.add(model);
                    }

                    DashBoardAdapter dashBoardAdapter = new DashBoardAdapter(getContext(),data,(HomeActivity)getActivity());
                    recdashboard.setAdapter(dashBoardAdapter);
                    recdashboard.setNestedScrollingEnabled(false);

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
                headers.put("Authorization", sharedPrefsManager.getAccessToken());
                headers.put("Content-Type", "application/json");
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(v.getContext());
        requestQueue.add(jsonObjectRequest);

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
