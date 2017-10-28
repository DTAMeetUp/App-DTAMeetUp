package fr.dta.formtion.dta_meetup.eventlistfragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.dta.formtion.dta_meetup.EventListActivity;
import fr.dta.formtion.dta_meetup.R;
import fr.dta.formtion.dta_meetup.database.local.Event;
import fr.dta.formtion.dta_meetup.database.local.EventListDAO;
import fr.dta.formtion.dta_meetup.database.local.EventListDataSource;

/**
 * Created by Arnaud Ringenbach on 28/10/2017.
 */

public class EventListFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<String> events;
    private List<Integer> ids;
    private Context context;


    public EventListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);


        // Set the adapter for our recyclerview
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            fillEventList();
            recyclerView.setAdapter(new EventListRecyclerViewAdapter(events, ids, mListener));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void fillEventList() {
        EventListDataSource eventListDataSource = EventListDataSource.getInstance(getActivity());
        EventListDAO eventListDAO = eventListDataSource.newEventListDAO();
        ArrayList<Event> allEvents = eventListDAO.readAll();
        events = new ArrayList<>();
        ids = new ArrayList<>();
        for (int i = 0; i < allEvents.size(); i++) {
            events.add(allEvents.get(i).getEventName());
            ids.add(allEvents.get(i).getEventId());
        }
    }


    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(int id);
    }

}