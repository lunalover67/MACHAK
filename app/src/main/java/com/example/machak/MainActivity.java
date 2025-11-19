package com.example.machak;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/*
 main activity will have
 - terminal
 - input
 - this month's data

 */


public class MainActivity extends AppCompatActivity {

    private TextView terminal_window;
    private EditText location_input; // POS (Point Of Sale)
    private EditText amount_input; // How much was spent?

    private ArrayList<MonthData> month_list;
    private MonthData current_month;

    private Gson gson = new Gson();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Appoint variable access.
        terminal_window = findViewById(R.id.terminal_textview);

        // test
        String formatTime = (new Timestamp()).toString();
        terminal_window.setText(formatTime);
        Log.d("testtime", formatTime);


        // Load month data.


        // if new month, save monthdata into oldmonthdata, make new monthdata (popup maybe w/ budget)


    }






    // -- UTILITY FUNCTIONS

    @SuppressLint("SetTextI18n")
    private void loadMonthData(Context context) {

        // Read file.

        File file = new File(context.getFilesDir(), "monthdata.json");

        try {

            if (file.exists()) {

                // read
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    String contents = new String(Files.readAllBytes(file.toPath()));

                    // have a list of monthdata obj --> [monthdata1, monthdata2]
                    Object contentObject = gson.fromJson(contents, MonthData.class);


                }
            }
            else {
                file.createNewFile();
                month_list = new ArrayList<>();
                current_month = new MonthData();
//                month_list.add()
            }
        }
        catch (Exception e) {
            terminal_window.setText("[FAILED TO LOAD]");
        }

        Object rawJsonObject = null;

        // convert to array/arralist/mutable array,


        // assign to month list

    }



    // -- ONCLICK METHODS


    // Associated with the 'submit' button.
    public void submitTransaction(View view) {

    }

    public void openMonthLog(View view) {

    }


}    