package com.example.eventznow;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class EventsAdapterMember extends RecyclerView.Adapter<EventsAdapterMember.ViewHolder> {

    private List<HelperClassEvents> eventsList;
    private Context context;
    private String userId;
    private DatabaseReference usersRef;

    public EventsAdapterMember(Context context, FirebaseAuth firebaseAuth) {
        this.context = context;
        this.userId = firebaseAuth.getCurrentUser().getUid();
        this.usersRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
    }

    public void setEventsList(List<HelperClassEvents> eventsList) {
        this.eventsList = eventsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        HelperClassEvents event = eventsList.get(position);

        holder.eventName.setText(event.getEventname());
        holder.eventDate.setText(event.getDate());
        holder.eventTime.setText(event.getTime());
        holder.eventLocation.setText(event.getLocation());
        holder.eventSlot.setText(event.getSlot());
        holder.eventPrice.setText(event.getPrice());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperClassEvents event = eventsList.get(holder.getAdapterPosition());

                // Create an intent to start the MemberEventDetailActivity
                Intent intent = new Intent(context, MemberEventDetailActivity.class);
                intent.putExtra("eventID", event.getEventID());
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return eventsList != null ? eventsList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView eventName;
        public TextView eventDate;
        public TextView eventTime;
        public TextView eventLocation;
        public TextView eventSlot;
        public TextView eventPrice;
        public LinearLayout eventItemAdmin;

        public ViewHolder(View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.eventName);
            eventDate = itemView.findViewById(R.id.eventDate);
            eventTime = itemView.findViewById(R.id.eventTime);
            eventLocation = itemView.findViewById(R.id.eventLocation);
            eventSlot = itemView.findViewById(R.id.btn_memberpicker);
            eventPrice = itemView.findViewById(R.id.btn_price);
            eventItemAdmin = itemView.findViewById(R.id.eventItemAdmin);
        }
    }

}
