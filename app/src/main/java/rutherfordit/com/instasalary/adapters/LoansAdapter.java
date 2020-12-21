package rutherfordit.com.instasalary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import rutherfordit.com.instasalary.R;

public class LoansAdapter extends RecyclerView.Adapter<LoansAdapter.MyViewHolder> {

    Context context;

    public LoansAdapter(Context context) {

        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.loans_rec_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView loan_heading, loan_date, loan_amount, application_status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            loan_heading = itemView.findViewById(R.id.loan_heading);
            loan_date = itemView.findViewById(R.id.loan_date);
            loan_amount = itemView.findViewById(R.id.loan_amount);
            application_status = itemView.findViewById(R.id.loan_status);

        }
    }
}
