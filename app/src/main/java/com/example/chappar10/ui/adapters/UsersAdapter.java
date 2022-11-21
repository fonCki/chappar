package com.example.chappar10.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chappar10.R;
import com.example.chappar10.data.User;
import com.google.android.material.imageview.ShapeableImageView;

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
//        Uri uri = Uri.parse("https://i.picsum.photos/id/25/200/300.jpg?hmac=ScdLbPfGd_kI3MUHvJUb12Fsg1meDQEaHY_mM613BVM");
//        holder.avatar.setImageURI(uri);
        holder.avatar.setImageResource(R.drawable.brad_pitt);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ShapeableImageView avatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            avatar = itemView.findViewById(R.id.tv_image);
            itemView.setOnClickListener(v -> {
                onClickListener.onClick(users.get(getBindingAdapterPosition()));
            });
            }
        }

    public interface OnClickListener {
        void onClick(User user);
    }
}

