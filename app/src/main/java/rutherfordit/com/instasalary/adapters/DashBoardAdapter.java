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
import rutherfordit.com.instasalary.R;

public class DashBoardAdapter extends RecyclerView.Adapter<DashBoardAdapter.MyViewHolder> {

    Context context;
    SharedPreferences sharedPreferences;
    String UserAccessToken;

    public DashBoardAdapter(Context context) {

        this.context = context;
        sharedPreferences = context.getSharedPreferences("mySharedPreference", Context.MODE_PRIVATE);
        UserAccessToken = "Bearer " + sharedPreferences.getString("AccessToken", "");
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        holder.swipe_to_disburse.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(SlideToActView slideToActView) {


            }
        });

    }

    @Override
    public int getItemCount() {
        return 0;
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
