package fr.dta.formtion.dta_meetup.eventlistfragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.dta.formtion.dta_meetup.MeetUp;
import fr.dta.formtion.dta_meetup.R;
import fr.dta.formtion.dta_meetup.database.DateUtils;
import fr.dta.formtion.dta_meetup.database.Event;

/**
 * Created by Arnaud Ringenbach on 28/10/2017.
 */

public class EventListRecyclerViewAdapter extends RecyclerView.Adapter<EventListRecyclerViewAdapter.ViewHolder> {

    private final List<Event> mValues;
    private final EventListFragment.OnListFragmentInteractionListener mListener;

    public EventListRecyclerViewAdapter(List<Event> items, EventListFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
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
        holder.mEventNameView.setText(mValues.get(position).getTitle());
        holder.mEventWeekDayView.setText(DateUtils.getDayOfWeek(mValues.get(position).getDateTime()));
        holder.mEventTimeView.setText(DateUtils.getFormatedHour(mValues.get(position).getDateTime()));
        holder.mEventMonthDayView.setText(Integer.toString(DateUtils.getDayOfMonth(mValues.get(position).getDateTime())));
        holder.mEventInterestedNbView.setText(Integer.toString(mValues.get(position).getNbInterested()));
        holder.mEventTypeView.setText(mValues.get(position).getCategory());
        holder.mEventLocationView.setText(mValues.get(position).getLocation());
        holder.mEventMonthView.setText(DateUtils.getMonthFormated(mValues.get(position).getDateTime()));


        // TODO Set category text from resource
        /*int categoryResId = holder.mEventCategoryTextView.getResources().getIdentifier( mValues.get(position).getTitle(), "string", MeetUp.getContext().getPackageName());
        holder.mEventCategoryTextView.setText( holder.mEventCategoryTextView.getResources().getString( categoryResId ) );*/

        // Set category ImageView
        String category = mValues.get(position).getCategory();
        int srcDrawable = holder.mEventCategoryImageView.getResources().getIdentifier("ic_"+category, "drawable", MeetUp.getContext().getPackageName());
        holder.mEventCategoryImageView.setImageResource(srcDrawable);


        /* TODO : supprimer si l'autre solution fonctionne
            holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mEvent);
                }
            }
        });*/
        holder.bind(mValues.get(position), this.mListener);
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
        public final TextView mEventWeekDayView;
        public final TextView mEventTimeView;
        public final TextView mEventMonthDayView;
        public final TextView mEventInterestedNbView;
        public final TextView mEventTypeView;
        public final TextView mEventLocationView;
        public final TextView mEventMonthView;
        public final TextView mEventCategoryTextView;
        public final ImageView mEventCategoryImageView;

        public Event mEvent;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mEventNameView = view.findViewById(R.id.eventLabelTextView);
            mEventWeekDayView = view.findViewById(R.id.weekDayTextView);
            mEventTimeView = view.findViewById(R.id.timeTextView);
            mEventMonthDayView = view.findViewById(R.id.dayMonthTextView);
            mEventInterestedNbView = view.findViewById(R.id.interestedNumberTextView);
            mEventTypeView = view.findViewById(R.id.categoryTextView);
            mEventLocationView = view.findViewById(R.id.locationTextView);
            mEventMonthView = view.findViewById(R.id.monthTextView);
            mEventCategoryTextView = view.findViewById(R.id.categoryTextView);
            mEventCategoryImageView = view.findViewById(R.id.iconCategoryImageView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mEventNameView.getText() + "'";
        }

        public void bind(final Event event, final EventListFragment.OnListFragmentInteractionListener listener) {
            final Event clickedEvent = event;

            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListFragmentInteraction(clickedEvent);
                    }
                }
            });
        }
    }
}
