package com.example.nearfall;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class HeatmapFragment extends Fragment implements View.OnClickListener {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create view from fragment_heatmap
        View view = inflater.inflate(R.layout.fragment_heatmap, container, false);

        //When user icon is clicked
        LinearLayout profile = view.findViewById(R.id.user_icon_grey);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to profileFragment
                Navigation.findNavController(view).navigate(R.id.action_heatmapFragment_to_profileFragment);
            }
        });

        //When home icon is clicked
        LinearLayout home = view.findViewById(R.id.home_icon_grey);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to homeFragment
                Navigation.findNavController(view).navigate(R.id.action_heatmapFragment_to_homeFragment);
            }
        });

        return view;
    }


    @Override
    public void onClick(View view) {

    }
}