package com.caroline.android.thehangercure;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.caroline.android.thehangercure.R.id.dateTime;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleDateFormat dayFormat = new SimpleDateFormat( "EEEE", Locale.US);
        Calendar calendar = Calendar.getInstance();
        String weekDay = dayFormat.format(calendar.getTime());

        final String greetingsText = getResources().getString(R.string.day_of_week, weekDay);

        TextView greetingsView = (TextView) findViewById( dateTime );
        greetingsView.setText(greetingsText);

    }



}
