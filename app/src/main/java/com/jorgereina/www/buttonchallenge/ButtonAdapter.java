package com.jorgereina.www.buttonchallenge;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jorgereina.www.buttonchallenge.databinding.ItemRowBinding;
import com.jorgereina.www.buttonchallenge.models.User;

import java.util.List;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ViewHolder> {

    private List<User> userList;

    public ButtonAdapter(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
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
