package com.choubapp.ensias_wiki_hub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.choubapp.ensias_wiki_hub.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class FooterFragment extends Fragment {
    ImageView home, newquestion, notification,profile;
    //Mandatory constructor for instantiating the fragment
    public FooterFragment() {
    }
    /**
     * Inflates the fragment layout file footer_layout
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.footerbar, container, false);

        // write your buttons and the OnClickListener logic


        // Return the rootView
        return rootView;
    }
}
