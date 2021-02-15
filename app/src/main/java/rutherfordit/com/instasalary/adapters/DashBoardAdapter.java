package rutherfordit.com.instasalary.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
import com.ncorti.slidetoact.SlideToActView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.activities.HomeActivity;
import rutherfordit.com.instasalary.extras.MySingleton;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.extras.Urls;
import rutherfordit.com.instasalary.models.LoanListModel;
import rutherfordit.com.instasalary.myinterfaces.CreateMandate;

public class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.MyViewHolder> {

    Context context;
    List<LoanListModel> data;
    SharedPrefsManager sharedPrefsManager;
    CreateMandate createMandate;

    public DashBoardAdapter(Context context, List<LoanListModel> data, CreateMandate createMandate) {

        this.context = context;
        this.data = data;
        sharedPrefsManager = new SharedPrefsManager(context);
        this.createMandate = createMandate;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_rec_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        LoanListModel model = data.get(position);

        holder.dash_heading.setText(model.getApplication_number());

        String auth_ok = model.getApplication_status();

        if  (auth_ok.equals("1"))
        {
            holder.swipe_to_disburse.setVisibility(View.VISIBLE);
        }
        else if (auth_ok.equals("0"))
        {
            holder.swipe_to_disburse.setVisibility(View.GONE);
        }

        String appstatus = model.getApplication_status();

        if (appstatus.equals("1"))
        {
            holder.application_status.setText("Maker Approved.");
        }
        else if  (appstatus.equals("2"))
        {
            holder.application_status.setText("Valuator Approved.");
        }else if  (appstatus.equals("3"))
        {
            holder.application_status.setText("Authorizer Approved.");
        }

        holder.dash_amount.setText(model.getAmount());
        holder.dash_lan.setText(model.getId());
        holder.dash_date.setText(model.getJoined_on());

        holder.swipe_to_disburse.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {

                createMandate.create();
               // disburseAmount(model.getId());

            }
        });

    }

    private void disburseAmount(String id)
    {

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("user_disbursed","1");
            jsonObject.put("id",id);
            jsonObject.put("company_id",sharedPrefsManager.getCOMPANY_ID());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Urls.LOAN_DISBURSE, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.e("disburseapi", "onResponse: " + response );

                try {
                    Log.e("message", "onResponse: " + response.getString("message") );
                    Toasty.success(context,"Loan Disbursed",Toasty.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("disburseapi", "onErrorResponse: " + error.getLocalizedMessage() );
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Accept", "application/json");
                params.put("Authorization",sharedPrefsManager.getAccessToken() );
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dash_heading, dash_date, dash_amount, dash_repay, application_status,dash_lan;
        SlideToActView swipe_to_disburse;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dash_lan = itemView.findViewById(R.id.dash_lan);
            swipe_to_disburse = itemView.findViewById(R.id.swipe_to_disburse);
            dash_repay = itemView.findViewById(R.id.dash_repay);
            dash_heading = itemView.findViewById(R.id.dash_heading);
            dash_amount = itemView.findViewById(R.id.dash_amount);
            dash_date = itemView.findViewById(R.id.dash_date);
            application_status = itemView.findViewById(R.id.application_status);

        }
    }
}
