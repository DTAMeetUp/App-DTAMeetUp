package fr.dta.formtion.dta_meetup;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.dta.formtion.dta_meetup.database.Event;
import fr.dta.formtion.dta_meetup.database.FirebaseRealtime;

public class AddEventActivity extends AppCompatActivity {
    Context context;
    EditText eventTitleEditText;
    EditText locationEditText;
    EditText descriptionEditText;
    Spinner categoriesSpinner;
    Button dateTimeButton;
    Calendar dateTime;
    Toolbar toolbar;
    ImageView categoryImageView;

    String selectedCategory;
    TypedArray iconsArray;
    List<String> categoriesArray;
    boolean dateSet = false;
    Event myEvent = new Event();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        // Toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Current Context
        context = AddEventActivity.this;


        // Get Views
        eventTitleEditText = (EditText) findViewById(R.id.eventTitleEditText);
        locationEditText = (EditText) findViewById(R.id.locationEditText);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        categoriesSpinner = (Spinner) findViewById(R.id.categoriesSpinner);
        categoryImageView = (ImageView) findViewById(R.id.categoryImageView);
        dateTimeButton = (Button) findViewById(R.id.datetimeButton);
        dateTime = Calendar.getInstance();


        // Categories Icons
        this.iconsArray = getResources().obtainTypedArray(R.array.icons);

        // Categories Spinner Handler
        this.categoriesArray = Arrays.asList(getResources().getStringArray(R.array.categories));
        ArrayAdapter<CharSequence> serviceAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.categories,
                android.R.layout.simple_spinner_item);
        serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner.setAdapter(serviceAdapter);
        categoriesSpinner.setSelection(0); // Event (the default event category)
        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();
                updateCategoryThumb(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCategory = "none";
            }
        });


        // Date Time ClickListener
        dateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DPDIALOG", "button clicked");
                displayDatePickerDialog();
            }
        });

        // Check for bundleargs (case : edit event)
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if(extras != null) {
            if (extras.containsKey("editEvent")) {
                myEvent = (Event) extras.getSerializable("editEvent");
                fillEventDetails();
            }
        }
    }

    private void fillEventDetails() {
        eventTitleEditText.setText(myEvent.getTitle());
        locationEditText.setText(myEvent.getLocation());
        descriptionEditText.setText(myEvent.getDescription());
        dateTime.setTimeInMillis(myEvent.getDateTime());
        dateTimeButton.setText(dateTime.get(Calendar.DAY_OF_MONTH) + " " + dateTime.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.FRENCH)
                + " " + dateTime.get(Calendar.YEAR) + " à " + dateTime.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", dateTime.get(Calendar.MINUTE)));
        dateSet = true;

        String eventCategories[] = getResources().getStringArray(R.array.categories);
        int aPosition=0;
        for(int i=0; i < eventCategories.length; i++)
            if(eventCategories[i].equals(myEvent.getCategory()))
                aPosition = i;
        categoriesSpinner.setSelection(aPosition);
    }

    private void updateCategoryThumb(int position) {
        String item = this.categoriesArray.get(position);
        int srcDrawable = this.getResources().getIdentifier("ic_"+item, "drawable", MeetUp.getContext().getPackageName());
        categoryImageView.setImageResource(srcDrawable);
    }

    private void displayDatePickerDialog() {
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

                        dateSet = true;

                    }
                }, 20, 0, true);
                tmDialog.show();
            }
        }, 2017, 11, 15);
        dpDialog.show();
    }

    protected boolean isEverythingFilled() {
        if (eventTitleEditText.getText().toString().equals("")
                || selectedCategory == "none"
                || locationEditText.getText().toString().equals("")
                || !dateSet) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    protected void commitEvent() {
        myEvent.setTitle(eventTitleEditText.getText().toString());
        myEvent.setCategory(selectedCategory);
        myEvent.setLocation((locationEditText.getText().toString()));
        myEvent.setDescription(descriptionEditText.getText().toString());
        myEvent.setAuthorId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        myEvent.setDateTime(dateTime.getTimeInMillis());
        myEvent.setCreatedAt(Calendar.getInstance().getTimeInMillis());
        myEvent.setModifiedAt(Calendar.getInstance().getTimeInMillis());

        if(myEvent.getId()==null)
            FirebaseRealtime.saveEvent(myEvent);
        else
            FirebaseRealtime.updateEvent(myEvent);

        Intent intent = new Intent(AddEventActivity.this, EventListActivity.class);
        startActivity(intent);
    }



    @Override
    public boolean onSupportNavigateUp() {
        Log.d("mylog", "Toolbar Back Button");
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_validate) {
            Log.d("mylog", "Validate Clicked");
            if(isEverythingFilled())
                commitEvent();
        }

        return super.onOptionsItemSelected(item);
    }
}
