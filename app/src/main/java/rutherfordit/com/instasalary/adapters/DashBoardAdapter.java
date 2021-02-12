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
import com.ncorti.slidetoact.SlideToActView;

import java.util.ArrayList;
import java.util.List;

import rutherfordit.com.instasalary.R;
import rutherfordit.com.instasalary.extras.SharedPrefsManager;
import rutherfordit.com.instasalary.models.LoanListModel;

public class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.MyViewHolder> {

    Context context;
    List<LoanListModel> data;

    public DashBoardAdapter(Context context, List<LoanListModel> data) {

        this.context = context;
        this.data = data;

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
