package com.jorgereina.www.buttonchallenge;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jorgereina.www.buttonchallenge.models.User;

import java.util.List;

/**
 * Created by jorgereina on 3/8/18.
 */

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ViewHolder> {

    private Context context;
    private List<User> userList;

    public ButtonAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.nameTv.setText(user.getName());
        holder.emailTv.setText(user.getEmail());
        holder.candidateTv.setText(user.getCandidate());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;
        TextView emailTv;
        TextView candidateTv;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.item_name_tv);
            emailTv = itemView.findViewById(R.id.item_email_tv);
            candidateTv = itemView.findViewById(R.id.item_candidate_tv);
        }
    }

    public void updateList(List<User> items) {
        if (items != null && items.size() > 0) {
            userList.clear();
            userList.addAll(items);
            notifyDataSetChanged();
        }
    }
}
