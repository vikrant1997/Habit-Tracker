package com.vikrant.habittracker;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.vikrant.habittracker.data.HabitContract.HabitEntry;
import com.vikrant.habittracker.data.HabitDbHelper;

public class EditorActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mDurationEditText;
    private EditText mPlaceEditText;
    private Spinner mTypeSpinner;
    private Spinner mTimeSpinner;

    private int mType = HabitEntry.TYPE_CONSTRUCTIVE;
    private int mTime = HabitEntry.TIME_EVENING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        mNameEditText = (EditText) findViewById(R.id.edit_habit_name);
        mDurationEditText = (EditText) findViewById(R.id.edit_habit_duration);
        mPlaceEditText = (EditText) findViewById(R.id.edit_habit_location);
        mTypeSpinner = (Spinner) findViewById(R.id.spinner_type);
        mTimeSpinner = (Spinner) findViewById(R.id.spinner_time);
        setupSpinner();
    }

    private void setupSpinner() {

        ArrayAdapter typeSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_habit_type_options, android.R.layout.simple_spinner_item);

        ArrayAdapter timeSpinnerAdapter = ArrayAdapter.createFromResource(this, R.array.array_habit_time_options, android.R.layout.simple_spinner_item);

        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        timeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mTypeSpinner.setAdapter(typeSpinnerAdapter);
        mTimeSpinner.setAdapter(timeSpinnerAdapter);

        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.type_constructive))) {
                        mType = HabitEntry.TYPE_CONSTRUCTIVE;
                    } else if (selection.equals(getString(R.string.type_avoiding))) {
                        mType = HabitEntry.TYPE_AVOIDING;
                    } else if (selection.equals(getString(R.string.type_unconciousness))) {
                        mType = HabitEntry.TYPE_UNCONCIOUSNESS;
                    } else {
                        mType = HabitEntry.TYPE_DESTRUCTIVE;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mType = HabitEntry.TYPE_UNCONCIOUSNESS;
            }
        });
        mTimeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.time_morning))) {
                        mTime = HabitEntry.TIME_MORNING;
                    } else if (selection.equals(getString(R.string.time_afternoon))) {
                        mTime = HabitEntry.TIME_AFTERNOON;
                    } else if (selection.equals(getString(R.string.time_evening))) ;
                    else mTime = HabitEntry.TIME_NIGHT;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mTime = HabitEntry.TIME_EVENING;
            }
        });
    }

    private void insertHabit() {
        String nameString = mNameEditText.getText().toString().trim();
        String durationString = mDurationEditText.getText().toString().trim();
        String placeString = mPlaceEditText.getText().toString().trim();
        int duration = Integer.parseInt(durationString);

        HabitDbHelper mDbHelper = new HabitDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, nameString);
        values.put(HabitEntry.COLUMN_HABIT_TYPE, mType);
        values.put(HabitEntry.COLUMN_HABIT_DURATION, duration);
        values.put(HabitEntry.COLUMN_HABIT_TIME, mTime);
        values.put(HabitEntry.COLUMN_HABIT_PLACE, placeString);

        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving habit", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Habit saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertHabit();
                finish();
                return true;
            case R.id.action_delete:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}