package com.example.nearfall;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Tutorial3Fragment extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create view from fragment_tutorial3.xml
        View view = inflater.inflate(R.layout.fragment_tutorial3, container, false);

        //Get name
        String name = MainActivity.getUserManager().getUser().getUsername();


        //Set home_mode_text to the correct purpose mode
        TextView text = (TextView) view.findViewById(R.id.name_text);
        text.setText(name);

        //When page is clicked
        View page = view.findViewById(R.id.home_page);
        page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to tutorial4Fragment
                Navigation.findNavController(view).navigate(R.id.action_tutorial3Fragment_to_tutorial4Fragment);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}