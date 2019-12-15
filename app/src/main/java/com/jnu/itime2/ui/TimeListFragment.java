package com.jnu.itime2.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.jnu.itime2.MainActivity;
import com.jnu.itime2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeListFragment extends Fragment {

    private MainActivity.TimeArrayAdapter timeArrayAdapter;
    public TimeListFragment( MainActivity.TimeArrayAdapter timeArrayAdapter)
    {
        this.timeArrayAdapter=timeArrayAdapter;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_time_list, container, false);
        ListView listViewSuper= (ListView) view.findViewById(R.id.list_view_goods);
        listViewSuper.setAdapter(timeArrayAdapter);

        this.registerForContextMenu(listViewSuper);
        // Inflate the layout for this fragment
        return view;
    }

}
