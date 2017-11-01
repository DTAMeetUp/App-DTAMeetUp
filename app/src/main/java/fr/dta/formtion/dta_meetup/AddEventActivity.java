package fr.dta.formtion.dta_meetup;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Date;

import fr.dta.formtion.dta_meetup.database.Event;

public class AddEventActivity extends AppCompatActivity {
    EditText eventTitleEditText;
    EditText locationEditText;
    EditText descriptionEditText;
    Spinner categoriesSpinner;
    String selectedCategory;
    Button dateTimeButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        eventTitleEditText = (EditText) findViewById(R.id.eventTitleEditText);
        locationEditText = (EditText) findViewById(R.id.locationEditText);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        categoriesSpinner = (Spinner) findViewById(R.id.categoriesSpinner);
        dateTimeButton = (Button) findViewById(R.id.datetimeButton);

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
                //DatePickerDialog dpDialog = new DatePickerDialog(this)
            }
        });
    }

    protected void newEventCreation() {
        Event myEvent = new Event();
        myEvent.setTitle(eventTitleEditText.getText().toString());
        myEvent.setCategory(selectedCategory);
        myEvent.setLocation((locationEditText.getText().toString()));
        myEvent.setDescription(descriptionEditText.getText().toString());

        //myEvent.setCreatedAt(new Date());
        //myEvent.setModifiedAt(new Date());




    }
}
