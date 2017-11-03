package fr.dta.formtion.dta_meetup.eventlistfragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.dta.formtion.dta_meetup.R;
import fr.dta.formtion.dta_meetup.database.DateUtils;
import fr.dta.formtion.dta_meetup.database.Event;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EventDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EventDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventDetailsFragment extends Fragment {
    Event myEvent;

    private OnFragmentInteractionListener mListener;

    public EventDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventDetailsFragment newInstance(Event myEvent) {
        EventDetailsFragment fragment = new EventDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("myevent", myEvent);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myEvent = (Event) getArguments().getSerializable("myevent");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);
        TextView titleTextView = (TextView) view.findViewById(R.id.titleTextView);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextView);
        TextView categoryTextView = (TextView) view.findViewById(R.id.categoryTextView);
        TextView nbInterestedTextView = (TextView) view.findViewById(R.id.nbInterestedTextView);
        TextView locationTextView = (TextView) view.findViewById(R.id.locationTextView);
        TextView dayMonthTextView = (TextView) view.findViewById(R.id.dayMonthTextView);
        TextView weekDayTextView = (TextView) view.findViewById(R.id.weekDayTextView);
        TextView monthTextView = (TextView) view.findViewById(R.id.monthTextView);



        titleTextView.setText(myEvent.getTitle());
        weekDayTextView.setText(DateUtils.getDayOfWeek(myEvent.getDateTime()));
        //holder.mEventTimeView.setText(DateUtils.getFormatedHour(mValues.get(position).getDateTime()));
        dayMonthTextView.setText(Integer.toString(DateUtils.getDayOfMonth(myEvent.getDateTime())));
        nbInterestedTextView.setText(Integer.toString(myEvent.getNbInterested()));
        categoryTextView.setText(myEvent.getCategory());
        locationTextView.setText(myEvent.getLocation());
        monthTextView.setText(DateUtils.getMonthFormated(myEvent.getDateTime()));
        descriptionTextView.setText(myEvent.getDescription());


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
