package com.vikrant.habittracker.data;

import android.provider.BaseColumns;

public final class HabitContract {
    private HabitContract() {
    }

    public static final class HabitEntry implements BaseColumns {
        public final static String TABLE_NAME = "habits";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_HABIT_NAME = "name";
        public final static String COLUMN_HABIT_TYPE = "type";
        public final static String COLUMN_HABIT_DURATION = "duration";
        public final static String COLUMN_HABIT_TIME = "time";
        public final static String COLUMN_HABIT_PLACE = "place";
        public static final int TYPE_CONSTRUCTIVE = 1;
        public static final int TYPE_AVOIDING = 2;
        public static final int TYPE_UNCONCIOUSNESS = 3;
        public static final int TYPE_DESTRUCTIVE = 4;
        public static final int TIME_MORNING = 11;
        public static final int TIME_AFTERNOON = 12;
        public static final int TIME_EVENING = 13;
        public static final int TIME_NIGHT = 14;
    }
}

