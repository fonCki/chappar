package com.example.chappar10.ui.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chappar10.R;
import com.example.chappar10.model.Chat;
import com.example.chappar10.model.User;
import com.example.chappar10.ui.view_model.MainViewModel;
import com.example.chappar10.utils.SetImageTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapters extends RecyclerView.Adapter<ChatAdapters.ViewHolder> {

    private final ArrayList<Chat> chats;
    private OnClickListener onClickListener;
    private MainViewModel viewModel;

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
        viewModel = new ViewModelProvider((ViewModelStoreOwner) parent.getContext()).get(MainViewModel.class);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AtomicReference<User> otherUser = new AtomicReference<>();
        Chat chat = chats.get(position);
        chat.getInvolvedUsers().forEach(user -> {
                if (!user.getUid().equals(viewModel.getMyUserID()))
                        otherUser.set(user);
        });
        holder.name.setText(otherUser.get().getNickname());
        holder.message.setText(chats.get(position).getLastMessage());
        Date date = new Date(chats.get(position).getTimestamp().toDate().getTime());
        // show date in format: 12:00
        holder.time.setText(String.format("%02d:%02d", date.getHours(), date.getMinutes()));
        String photoUrl = otherUser.get().getProfileurl();
        if (photoUrl != null) {
            new SetImageTask(holder.avatar).execute(photoUrl);
        }
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public void setChats(List<Chat> chats) {
        this.chats.clear();
        this.chats.addAll(chats);
        notifyDataSetChanged();


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
