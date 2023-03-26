package com.example.nearfall;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Tutorial7Fragment extends Fragment  implements View.OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Create view from fragment_tutorial7.xml
        View view = inflater.inflate(R.layout.fragment_tutorial7, container, false);

        //Get purpose
        String purpose = MainActivity.getUserManager().getUser().getPurpose();

        //Set home_mode_text to the correct purpose mode
        TextView text = (TextView) view.findViewById(R.id.home_mode_text);
        text.setText(purpose + " Mode");

        //When page is clicked
        View page = view.findViewById(R.id.home_page);
        page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Navigate to tutorial8Fragment
                Navigation.findNavController(view).navigate(R.id.action_tutorial7Fragment_to_tutorial8Fragment);
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {

    }
}