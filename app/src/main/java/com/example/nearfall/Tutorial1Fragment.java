package com.example.nearfall;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Tutorial1Fragment extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutorial1, container, false);

        String purpose = MainActivity.getUserManager().getUser().getPurpose();

        TextView text = (TextView) view.findViewById(R.id.home_mode_text_overlay);
        text.setText(purpose + " Mode");



        View page = view.findViewById(R.id.home_page);
        page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Navigation.findNavController(view).navigate(R.id.action_tutorial1Fragment_to_tutorial2Fragment);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}