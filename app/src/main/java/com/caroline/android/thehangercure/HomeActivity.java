package com.caroline.android.thehangercure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.pinterest.android.pdk.PDKCallback;
import com.pinterest.android.pdk.PDKClient;
import com.pinterest.android.pdk.PDKException;
import com.pinterest.android.pdk.PDKResponse;
import com.pinterest.android.pdk.PDKUser;
import com.pinterest.android.pdk.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by carolinestewart on 1/24/17.
 */

public class HomeActivity extends AppCompatActivity {

    private static boolean DEBUG = true;
    private Button pinsButton, boardsButton, followingButton, logoutButton, pathButton;
    private TextView nameTv;
    private ImageView profileIv;
    private final String USER_FIELDS = "id,image,counts,created_at,first_name,last_name,bio";
    PDKUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);
        setTitle("Dinner and a Movie");



        logoutButton = (Button) findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLogout();
            }
        });

        getMe();
    }

//    private void setUser() {
//        nameTv.setText(user.getFirstName() + " " + user.getLastName());
//        Picasso.with( this ).load( user.getImageUrl() ).into( profileIv );
//    }

//

    private void setDate() {
        SimpleDateFormat dayFormat = new SimpleDateFormat( "EEEE", Locale.US );
        Calendar calendar = Calendar.getInstance();
        String weekDay = dayFormat.format(calendar.getTime());


    }

    private void setWelcomeMessage() {
        //retrieve username
        String userName = user.getFirstName();

        SimpleDateFormat dayFormat = new SimpleDateFormat( "EEEE", Locale.US );
        Calendar calendar = Calendar.getInstance();
        String weekDay = dayFormat.format(calendar.getTime());
        //create string concat with name and welcome message
        final String welcomeMessage = getResources().getString( R.string.what_are_you_looking_for, userName );



        //set the view
        nameTv = (TextView) findViewById(R.id.name_textview);
        nameTv.setText( welcomeMessage );
    }

    private void  getMe() {
        PDKClient.getInstance().getMe( USER_FIELDS, new PDKCallback() {
            @Override
            public void onSuccess(PDKResponse response) {
                if (DEBUG) log(String.format("status: %d", response.getStatusCode()));
                user = response.getUser();
                setWelcomeMessage();
//                setUser();
            }
            @Override
            public void onFailure(PDKException exception) {
                if (DEBUG)  log(exception.getDetailMessage());
                Toast.makeText( HomeActivity.this, "/me Request failed", Toast.LENGTH_SHORT ).show();
            }
        } );
    }



    private void  onLogout() {
        PDKClient.getInstance().logout();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }



    private void log(String msg) {
        if (!Utils.isEmpty( msg ))
            Log.d( getClass().getName(), msg );
    }
}