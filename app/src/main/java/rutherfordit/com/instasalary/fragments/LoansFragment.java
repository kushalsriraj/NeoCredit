package rutherfordit.com.instasalary.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import rutherfordit.com.instasalary.R;

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
        loader_loans.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("mySharedPreference", Context.MODE_PRIVATE);
        UserAccessToken = "Bearer " + sharedPreferences.getString("AccessToken", "");

        recloans = v.findViewById(R.id.recloans);
        emptyloanfrag = v.findViewById(R.id.emptyloanfrag);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recloans.setLayoutManager(linearLayoutManager);

    }
}

