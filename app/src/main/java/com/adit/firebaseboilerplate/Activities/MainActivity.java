package com.adit.firebaseboilerplate.Activities;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.adit.firebaseboilerplate.Fragments.MainFragment;
import com.adit.firebaseboilerplate.Fragments.PreloaderFragment;
import com.adit.firebaseboilerplate.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements
            MainFragment.OnFragmentInteractionListener,
            PreloaderFragment.OnFragmentInteractionListener {

    public DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentById(R.id.fragment_container);

        if (fragment == null){
            fragment = new PreloaderFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.fragment_container, fragment);
            transaction.commit();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(), "Database Connection Success" , Toast.LENGTH_SHORT).show();
                showMainFragment();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Database Connection Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showMainFragment(){
        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mainFragment, "mainFragment").addToBackStack("mainFragment").commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
