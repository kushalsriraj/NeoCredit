package rutherfordit.com.instasalary.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.extras.Urls;

public class LoansFragment extends Fragment {

    View v;
    TextView emptyloanfrag;
    CardView loader_loans;
    private String UserAccessToken;
    private RecyclerView recloans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.layout_loans, null);

        init();

        return v;
    }

    private void init() {

        loader_loans = v.findViewById(R.id.loader_loans);
        loader_loans.setVisibility(View.GONE);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mySharedPreference", Context.MODE_PRIVATE);
        UserAccessToken = "Bearer " + sharedPreferences.getString("AccessToken", "");

        recloans = v.findViewById(R.id.recloans);
        emptyloanfrag = v.findViewById(R.id.emptyloanfrag);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recloans.setLayoutManager(linearLayoutManager);

    }
}

