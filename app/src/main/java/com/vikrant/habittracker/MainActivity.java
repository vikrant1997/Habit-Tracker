package com.vikrant.habittracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.vikrant.habittracker.data.HabitContract.HabitEntry;
import com.vikrant.habittracker.data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {
    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        mDbHelper = new HabitDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_TYPE,
                HabitEntry.COLUMN_HABIT_DURATION,
                HabitEntry.COLUMN_HABIT_TIME,
                HabitEntry.COLUMN_HABIT_PLACE};

        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayView = (TextView) findViewById(R.id.text_view_habit);

        try {
            displayView.setText("The habits table contains " + cursor.getCount() + " habits.\n\n");
            displayView.append(HabitEntry._ID + "  -  " +
                    HabitEntry.COLUMN_HABIT_NAME + "  -  " +
                    HabitEntry.COLUMN_HABIT_TYPE + "  -  " +
                    HabitEntry.COLUMN_HABIT_DURATION + "  -  " +
                    HabitEntry.COLUMN_HABIT_TIME + "  -  " +
                    HabitEntry.COLUMN_HABIT_PLACE + "\n");

            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int typeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_TYPE);
            int durationColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_DURATION);
            int timeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_TIME);
            int placeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_PLACE);

            while (cursor.moveToNext()) {

                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentType = cursor.getString(typeColumnIndex);
                int currentDuration = cursor.getInt(durationColumnIndex);
                int currentTime = cursor.getInt(timeColumnIndex);
                String currentPlace = cursor.getString(placeColumnIndex);
                displayView.append(("\n" + currentID + "  -  " +
                        currentName + "  -  " +
                        currentType + "  -  " +
                        currentDuration + "  -  " +
                        currentTime + "  -  " +
                        currentPlace));
            }
        } finally {
            cursor.close();
        }
    }

    private void insertHabit() {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, "Running");
        values.put(HabitEntry.COLUMN_HABIT_TYPE, HabitEntry.TYPE_CONSTRUCTIVE);
        values.put(HabitEntry.COLUMN_HABIT_DURATION, "45");
        values.put(HabitEntry.COLUMN_HABIT_TIME, HabitEntry.TIME_EVENING);
        values.put(HabitEntry.COLUMN_HABIT_PLACE, "Park");
        long newRowId = db.insert(HabitEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertHabit();
                displayDatabaseInfo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
