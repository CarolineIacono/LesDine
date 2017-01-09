package com.caroline.android.thehangercure;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Created by carolinestewart on 12/20/16.
 */
public class DinnerItems extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dinner_list);

        ArrayList<Dinners> dinners = new ArrayList<>();
        dinners.add(new Dinners("salmon", "seafood"));
        dinners.add(new Dinners("beef stew", "beef"));
        dinners.add(new Dinners("chicken parm", "chicken"));


    }


}
