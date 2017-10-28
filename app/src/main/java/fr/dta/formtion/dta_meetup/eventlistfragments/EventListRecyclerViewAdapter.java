package fr.dta.formtion.dta_meetup.eventlistfragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fr.dta.formtion.dta_meetup.R;

/**
 * Created by Arnaud on 28/10/2017.
 */

public class EventListRecyclerViewAdapter extends RecyclerView.Adapter<EventListRecyclerViewAdapter.ViewHolder> {

    private final List<String> mValues;
    private final List<Integer> mIds;
    private final EventListFragment.OnListFragmentInteractionListener mListener;

    public EventListRecyclerViewAdapter(List<String> items, List<Integer> ids, EventListFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mIds = ids;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mEventNameView.setText(mValues.get(position));
        holder.mId = mIds.get(position);

        /*
        This sets up the communication with our main activity
        When the user clicks on an item of the recyclerview
        The main activity gets the id of the user selected
         */
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mId);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mValues != null)
            return mValues.size();
        else
            return -1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mEventNameView;

        public String mItem;
        public int mId;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mEventNameView = view.findViewById(R.id.eventLabelTextView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mEventNameView.getText() + "'";
        }
    }
}
