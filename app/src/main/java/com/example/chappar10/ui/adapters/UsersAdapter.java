package com.example.chappar10.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chappar10.R;
import com.example.chappar10.model.User;
import com.example.chappar10.utils.Converter;
import com.example.chappar10.utils.SetImageTask;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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
        User user = users.get(position);
        String photoUrl = user.getProfileImageUrl();

        Picasso.with(holder.avatar.getContext())
                .load(photoUrl)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.defaul)
                .into(holder.avatar);

        holder.name.setText(user.getNickname());
        //convert type Date to int Birthday
        holder.status.setText(user.getStatus().equals(User.Status.ONLINE) ? "🟢" : "🔴");
        holder.age.setText(String.valueOf(Converter.getAge(user.getBirthDate())));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<User> users) {
        this.users.clear();
        this.users.addAll(users);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView status;
        TextView age;
        ShapeableImageView avatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            status = itemView.findViewById(R.id.tv_status);
            age = itemView.findViewById(R.id.tv_age);
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

