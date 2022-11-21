package com.example.chappar10.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chappar10.R;
import com.example.chappar10.data.Chat;
import com.example.chappar10.data.Message;
import com.example.chappar10.data.User;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapters extends RecyclerView.Adapter<ChatAdapters.ViewHolder> {

    private final ArrayList<Chat> chats;
    private OnClickListener onClickListener;

    public ChatAdapters(ArrayList<Chat> chats) {
        this.chats = chats;
    }

    public void setOnClickListener(OnClickListener listener) {
        this.onClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.chat_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(chats.get(position).getSenderId());
        holder.message.setText(chats.get(position).getLatestMessage().getMessage());
        holder.time.setText("9:00 AM");
        holder.avatar.setImageResource(R.drawable.brad_pitt);

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView message;
        TextView time;
        CircleImageView avatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            avatar = itemView.findViewById(R.id.tv_image);
            message = itemView.findViewById(R.id.tv_last_message);
            time = itemView.findViewById(R.id.tv_time);
            itemView.setOnClickListener(v -> {
                onClickListener.onClick(chats.get(getBindingAdapterPosition()));
            });
        }
    }


    public interface OnClickListener {
        void onClick(Chat chat);
    }
}
