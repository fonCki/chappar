package com.example.chappar10.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chappar10.R;
import com.example.chappar10.data.User;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private final ArrayList<User> users;
    private OnClickListener onClickListener;

    public UsersAdapter(ArrayList<User> users) {
        this.users = users;
    }

    public void setOnClickListener(OnClickListener listener) {
        this.onClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.user_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(users.get(position).getNickname());
//        holder.icon.setImageResource(users.get(position).getIconId());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            icon = itemView.findViewById(R.id.iv_icon);
            itemView.setOnClickListener(v -> {
                onClickListener.onClick(users.get(getBindingAdapterPosition()));
            });
            }
        }


    public interface OnClickListener {
        void onClick(User user);
    }
}

