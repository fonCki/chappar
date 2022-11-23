package com.example.chappar10.ui.adapters;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chappar10.R;
import com.example.chappar10.model.Message;
import com.example.chappar10.ui.view_model.AccessViewModel;
import com.example.chappar10.ui.view_model.MainViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageAdapter  extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private final ArrayList<Message> messages;
    private OnClickListener onClickListener;
    private String myId;

    public MessageAdapter(ArrayList<Message> messages, String myId) {
        this.myId = myId;
        this.messages = messages;
    }

    public void setOnClickListener(OnClickListener listener) {
        this.onClickListener = listener;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.message_list_item, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MessageViewHolder holder, int position) {
        holder.message.setText(messages.get(position).getMessage());
        holder.messageLayout.setBackground(messages.get(position).senderId.equals(myId) ? holder.itemView.getContext().getDrawable(R.drawable.circular_my_message) : holder.itemView.getContext().getDrawable(R.drawable.circular_other_message));
        holder.messageContainer.setGravity(messages.get(position).senderId.equals(myId) ? Gravity.END : Gravity.START);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void setMessages(List<Message> messages) {
        Collections.sort(messages, (o1, o2) -> o1.getTimestamp().compareTo(o2.getTimestamp()));
        this.messages.clear();
        this.messages.addAll(messages);
        notifyDataSetChanged();
    }

    public class MessageViewHolder  extends RecyclerView.ViewHolder {
        TextView message;
        LinearLayout messageLayout, messageContainer;
        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            messageContainer = itemView.findViewById(R.id.full_line);
            messageLayout = itemView.findViewById(R.id.container);
            message = itemView.findViewById(R.id.tv_message);
            itemView.setOnClickListener(v -> {
                onClickListener.onClick(messages.get(getBindingAdapterPosition()));
            });
        }
    }

    public interface OnClickListener {
        void onClick(Message message);
    }
}

