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

import rutherfordit.com.instasalary.R;

public class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.MyViewHolder> {

    Context context;
    ArrayList loanName;

    public DashBoardAdapter(Context context, ArrayList loanName) {

        this.context = context;
        this.loanName = loanName;
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

        Log.e("recdata", "onBindViewHolder: " + loanName.get(position));

        holder.dash_heading.setText((CharSequence) loanName.get(position));
    }

    @Override
    public int getItemCount() {
        return loanName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView dash_heading, dash_date, dash_amount, dash_repay, application_status;
        SlideToActView swipe_to_disburse;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            swipe_to_disburse = itemView.findViewById(R.id.swipe_to_disburse);
            dash_repay = itemView.findViewById(R.id.dash_repay);
            dash_heading = itemView.findViewById(R.id.dash_heading);
            dash_amount = itemView.findViewById(R.id.dash_amount);
            dash_date = itemView.findViewById(R.id.dash_date);
            application_status = itemView.findViewById(R.id.application_status);

        }
    }
}
