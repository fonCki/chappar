package com.example.chappar10.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chappar10.R;
import com.example.chappar10.model.Location;
import com.example.chappar10.model.User;
import com.example.chappar10.ui.view_model.MainViewModel;
import com.example.chappar10.utils.Converter;
import com.example.chappar10.utils.Distance;
import com.example.chappar10.utils.SetImageTask;
import com.google.android.material.imageview.ShapeableImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private final ArrayList<User> users;
    private MainViewModel mainViewModel;
    private Location myLocation = new Location(0, 0);


    public CardAdapter(ArrayList<User> users) {
        this.users = users;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.fragment_user_details, parent, false);
        mainViewModel = new ViewModelProvider((ViewModelStoreOwner) parent.getContext()).get(MainViewModel.class);
        mainViewModel.getUser(mainViewModel.getMyUserID()).observeForever(user -> {
            myLocation = user.getLocation();
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = users.get(position);
        holder.name.setText(user.getNickname());
        //convert type Date to int Birthday
        int age = Converter.getAge(user.getBirthDate());//
        holder.age.setText(String.valueOf(age));
        String distance = String.format("%.1f", Distance.GetDistance(user.getLocation(), myLocation));
        holder.location.setText(distance + " km");

        String photoUrl = user.getProfileImageUrl();
        Picasso.with(holder.image.getContext())
                .load(photoUrl)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.defaul)
                .into(holder.image);
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

    public User getItem(int i) {
        return users.get(i);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView location;
        TextView age;
        ShapeableImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.user_details_name);
            location = itemView.findViewById(R.id.user_location);
            age = itemView.findViewById(R.id.user_details_age);
            image = itemView.findViewById(R.id.imageView);
        }
    }

}
