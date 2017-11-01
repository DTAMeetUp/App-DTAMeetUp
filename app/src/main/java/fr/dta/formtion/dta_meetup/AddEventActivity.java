package fr.dta.formtion.dta_meetup;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fr.dta.formtion.dta_meetup.database.Event;
import fr.dta.formtion.dta_meetup.database.FirebaseRealtime;

public class AddEventActivity extends AppCompatActivity {
    Context context;
    EditText eventTitleEditText;
    EditText locationEditText;
    EditText descriptionEditText;
    Spinner categoriesSpinner;
    String selectedCategory;
    Button dateTimeButton;
    Calendar dateTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        context = AddEventActivity.this;

        FirebaseRealtime.setEventListener();

        eventTitleEditText = (EditText) findViewById(R.id.eventTitleEditText);
        locationEditText = (EditText) findViewById(R.id.locationEditText);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        categoriesSpinner = (Spinner) findViewById(R.id.categoriesSpinner);
        dateTimeButton = (Button) findViewById(R.id.datetimeButton);
        dateTime = Calendar.getInstance();

        ArrayAdapter<CharSequence> serviceAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(serviceAdapter);
        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCategory = "none";
            }
        });

        dateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DPDIALOG", "button clicked");

                DatePickerDialog dpDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateTime.set(Calendar.YEAR, year);
                        dateTime.set(Calendar.MONTH, month);
                        dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        TimePickerDialog tmDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                dateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                dateTime.set(Calendar.MINUTE, minute);
                                dateTimeButton.setText(dateTime.get(Calendar.DAY_OF_MONTH) + " " + dateTime.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.FRENCH)
                                + " " + dateTime.get(Calendar.YEAR) + " à " + hourOfDay + ":" + String.format("%02d", minute));

                                //
                                newEventCreation();
                            }
                        }, 20, 0, true);
                        tmDialog.show();
                    }
                }, 2017, 11, 15);
                dpDialog.show();

            }
        });

    }

    protected void newEventCreation() {
        Event myEvent = new Event();

        myEvent.setTitle(eventTitleEditText.getText().toString());
        myEvent.setCategory(selectedCategory);
        myEvent.setLocation((locationEditText.getText().toString()));
        myEvent.setDescription(descriptionEditText.getText().toString());
        myEvent.setAuthorId(FirebaseAuth.getInstance().getCurrentUser().getUid());

        myEvent.setDateTime(new Date(dateTime.getTimeInMillis()));

        myEvent.setCreatedAt(new Date());
        myEvent.setModifiedAt(new Date());

        FirebaseRealtime.saveEvent(myEvent);




    }
}
