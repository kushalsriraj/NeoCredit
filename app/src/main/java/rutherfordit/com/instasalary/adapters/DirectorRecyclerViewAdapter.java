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
import rutherfordit.com.instasalary.models.MyProfileDirectorModel;

public class DirectorRecyclerViewAdapter extends RecyclerView.Adapter<DirectorRecyclerViewAdapter.MyViewHolder> {

    Context context;
    List<MyProfileDirectorModel> myProfileDirectorModels;

    public DirectorRecyclerViewAdapter(Context context, List<MyProfileDirectorModel> myProfileDirectorModels) {
        this.context = context;
        this.myProfileDirectorModels = myProfileDirectorModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.director_rec_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MyProfileDirectorModel myProfileDirectorModel = myProfileDirectorModels.get(position);

        holder.profile_fullname1.setText(myProfileDirectorModel.getName());
        holder.profile_email1.setText(myProfileDirectorModel.getEmail());
        holder.profile_phone1.setText(myProfileDirectorModel.getMobile_phone());
        holder.profile_aadhar1.setText(myProfileDirectorModel.getAadhar());
        holder.profile_pan1.setText(myProfileDirectorModel.getPan_card());
        holder.profile_address1.setText(myProfileDirectorModel.getCurrent_address());
        holder.profile_dob1.setText(myProfileDirectorModel.getDob());

    }

    @Override
    public int getItemCount() {
        return myProfileDirectorModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView profile_fullname1, profile_email1, profile_phone1, profile_aadhar1, profile_pan1, profile_address1, profile_dob1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_fullname1 = itemView.findViewById(R.id.profile_fullname1);
            profile_email1 = itemView.findViewById(R.id.profile_email1);
            profile_phone1 = itemView.findViewById(R.id.profile_phone1);
            profile_aadhar1 = itemView.findViewById(R.id.profile_aadhar1);
            profile_pan1 = itemView.findViewById(R.id.profile_pan1);
            profile_address1 = itemView.findViewById(R.id.profile_address1);
            profile_dob1 = itemView.findViewById(R.id.profile_dob1);
        }
    }
}
