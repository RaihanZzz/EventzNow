package com.example.eventznow;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeFragment extends Fragment {

    private DatabaseReference eventsRef;
    private RecyclerView recyclerView;
    private EventsAdapter adapter;
    private String userId;

    public AdminHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        // Initialize the RecyclerView and its adapter
        recyclerView = view.findViewById(R.id.reEventList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        adapter = new EventsAdapter(getContext(), firebaseAuth);
        adapter.setEventsList(new ArrayList<>()); // pass empty ArrayList using setEventsList()
        recyclerView.setAdapter(adapter);

        // Get the Realtime Database reference for events
        eventsRef = FirebaseDatabase.getInstance().getReference().child("events");

        // Add spacing between items
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        if (firebaseAuth.getCurrentUser() != null) {
            userId = firebaseAuth.getCurrentUser().getUid();
        }

        // Listen for changes to the events data and update the adapter
        eventsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<HelperClassEvents> eventsList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String eventID = snapshot.child("eventID").getValue(String.class);
                    String userIdAdmin = snapshot.child("userId").getValue(String.class);
                    String eventname = snapshot.child("eventname").getValue(String.class);
                    String date = snapshot.child("date").getValue(String.class);
                    String time = snapshot.child("time").getValue(String.class);
                    String location = snapshot.child("location").getValue(String.class);
                    String slot = snapshot.child("slot").getValue(String.class);
                    String price = snapshot.child("price").getValue(String.class);
                    String creator = snapshot.child("creator").getValue(String.class);

                    List<String> joinedUsersList = new ArrayList<>();
                    for (DataSnapshot userSnapshot : snapshot.child("joinedUsersList").getChildren()) {
                        String userID = userSnapshot.getValue(String.class);
                        joinedUsersList.add(userID);
                    }

                    if (userIdAdmin != null && userIdAdmin.equals(userId)) {
                    HelperClassEvents event = new HelperClassEvents(eventID, userIdAdmin, eventname, date, time, location, slot, price, joinedUsersList, creator);
                    eventsList.add(event);
                    }
                }
                adapter.setEventsList(eventsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error
            }
        });

        // Define the button and its click listener
        Button createNewEventButton = view.findViewById(R.id.btCreateNewEvent);
        createNewEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), AdminCreateEventActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
