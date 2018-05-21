package com.jorgereina.www.buttonchallenge;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jorgereina.www.buttonchallenge.databinding.ItemRowBinding;
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
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemRowBinding binding = ItemRowBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.binding.setUser(user);
        holder.binding.executePendingBindings();

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ItemRowBinding binding;

        public ViewHolder(ItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
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
