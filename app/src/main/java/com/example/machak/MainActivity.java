package com.example.machak;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.reflect.TypeToken;
import java.nio.charset.StandardCharsets;

import android.content.Context;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import java.nio.file.Files;
import java.util.ArrayList;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.*;

import java.util.Arrays;
import java.util.List;
import java.io.File;


/*
 main activity will have
 - terminal
 - input
 - this month's data

 */


public class MainActivity extends AppCompatActivity {


    // ---------------- VIEWS ----------------


    private TextView terminal_window;
    private EditText location_input; // POS (Point Of Sale)
    private EditText amount_input; // How much was spent?
    private Spinner tag_select;
    private PieChart tag_chart;
    private ProgressBar spent_bar;



    // -------------- DATASTUFFS --------------


    private ArrayList<MonthData> month_list;
    private MonthData current_month;


    // -------------- FILESTUFFS --------------


    private File data_file;
    private final String DATA_FILE_PATH = "monthdata.json";


    // -------------- ULILITIES --------------


    final Type MONTH_LIST_ARRAY_TYPE = new TypeToken<ArrayList<MonthData>>() {}.getType();
    private Gson gson = new Gson();


    // _________________________ ONCREATE _________________________


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Default layout creation.

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Localize data file.

        data_file = new File(this.getFilesDir(), DATA_FILE_PATH);

        // Appoint variable access to views.

        terminal_window = findViewById(R.id.terminal_textview);
        location_input = findViewById(R.id.locationTest);
        amount_input = findViewById(R.id.amountTest);
        tag_select = findViewById(R.id.spinnerTest);
        tag_chart = findViewById(R.id.tag_chart_test);
        spent_bar= findViewById(R.id.spent_progress_bar);

        // Load data and display it.

        loadMonthData();
        updateUI();

        // Setup tags display.

        setTagList(this);

    }


    // ____________________ UTILITY_FUNCTIONS ____________________


    // ----------- LOAD_MONTH_DATA -----------


    private void loadMonthData() {

        try {

            // Check that the file exists.

            if (data_file.exists()) {

                // Read the contents.

                String contents = new String(Files.readAllBytes(data_file.toPath()));

                // Get an array with MonthData objects inside.

                month_list = gson.fromJson(contents, MONTH_LIST_ARRAY_TYPE); // typetoken forces monthdata recognition in runtime

                // [NOTE]: At this point month_list is NOT empty, no need to check that condition.
                // Get current month.

                current_month = month_list.get(month_list.size() - 1); // [NOTE]: getLast() may be depreciated, using manual approach.

                // Check if it is a new month.

                if (current_month.getMonth() != Timestamp.getCurrentMonth() && current_month.getYear() != Timestamp.getCurrentYear()) {

                    // If it is, make a new MonthData object.

                    current_month = new MonthData();

                    // Set as current month.

                    month_list.add(current_month);

                }
            }

            // If no data file exists (fresh build).

            else {

                // Initialize array with all future months.

                month_list = new ArrayList<>();

                // Initialize current month.

                current_month = new MonthData();

                // Append it to month array.

                month_list.add(current_month);

                // Save it to the file.

                updateFileContents();
            }
        }

        // If the file can't be read for whatever reason :/ idk shouldnt happen really

        catch (Exception e) {
            terminal_window.setText("[FAILED TO LOAD]"); // i mean what else am i to do?
        }
    }


    // --------- UPDATE_FILE_CONTENTS ---------


    private void updateFileContents() {

        // Serialize month_list object.

        String serializedMonthData = gson.toJson(month_list); // no type needed O.O (wow) // assuming latest month has been updated (yes should be updated)

        // Write it to file.

        try {
            Files.write(data_file.toPath(), serializedMonthData.getBytes(StandardCharsets.UTF_8));
        }
        catch (Exception e) {
            Log.d("err", "couldnt locate file, or sum issue like that");
        }
    }


    // ------- UPDATE_TERMINAL_DISPLAY -------


    private void updateUI() {

        // ====== TERMINAL TEXT =====

        // Setup empty string.

        String text = "";

        // Build the string with all the transactions.

        for (Transaction transaction : current_month.getTransactionLog()) {

            text += transaction.getSummary() + "\n\n";
        }

        // Set the text.

        terminal_window.setText(text);


        // ===== PERCENTAGE BAR =====


        double spent = current_month.getSpent();
        double budget = current_month.getBudget();

// Avoid division by zero
        int progressPercent = budget > 0 ? (int) ((spent / budget) * 100) : 0;

// Cap at 100%
        if (progressPercent > 100) progressPercent = 100;

        spent_bar.setProgress(progressPercent);


        // todo: add percentage


// ===== PIE CHART =====

// Convert HashMap keys to a stable list ONCE
        // Start with all "official" tags from TAGS
        List<String> keys = new ArrayList<>(Transaction.TAGS.keySet());

// Add any tags found in the transaction log (e.g. BUSF, POOL)
        for (Transaction t : current_month.getTransactionLog()) {
            if (!keys.contains(t.getTag())) {
                keys.add(t.getTag());     // adds BUSF, POOL, etc. but NOT to spinner
            }
        }


        double[] sliceAmounts = new double[keys.size() + 2]; // for bus and pool
        Arrays.fill(sliceAmounts, 0);

// add numerical values
        for (Transaction transaction : current_month.getTransactionLog()) {

            int index = keys.indexOf(transaction.getTag());

            if (index == -1) {

                Log.e("ERROR", "Tag not found: " + transaction.getTag());
                continue; // prevents crash
            }

            sliceAmounts[index] += transaction.getAmount();
        }

// ---- doing chart

        ArrayList<PieEntry> slices = new ArrayList<>();

        for (int sliceIndex = 0; sliceIndex < keys.size(); sliceIndex++) {

            if (sliceAmounts[sliceIndex] != 0) {

                String key = keys.get(sliceIndex);            // e.g. "GROC"
                String label = key.equals("BUSF") ? "Bus Fares"
                        : key.equals("POOL") ? "Pool Admission"
                        : Transaction.TAGS.containsKey(key) ? Transaction.TAGS.get(key)
                        : key;


                Log.d("bug", label);

                slices.add(new PieEntry(
                        (float) sliceAmounts[sliceIndex],
                        label
                ));
            }
        }
        // Create PieDataSet and set properties
//
        PieDataSet dataSet = new PieDataSet(slices, "");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS); // Keep the material color palette
        dataSet.setValueTextColor(Color.BLACK); // Set text color for values (percentages/numbers)
        dataSet.setValueTextSize(16f); // Set size for value text

        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                // Format the value with a dollar sign
                return String.format("%.0f$", value);
            }
        });

        PieData pieData = new PieData(dataSet);

// Set PieData on the chart
        tag_chart.setData(pieData);
        tag_chart.notifyDataSetChanged();   // tell the chart the data changed
        tag_chart.invalidate();

// Chart configurations
        tag_chart.setDrawHoleEnabled(false); // Disable the center hole (donut chart style)
        tag_chart.setRotationEnabled(false); // Disable rotation of the pie chart
        tag_chart.getDescription().setEnabled(false); // Disable description
// tag_chart.setCenterText("Items"); // Uncomment if you want center text
// tag_chart.animate(); // Uncomment if you want animation


        // Set the Legend to appear on the right
        Legend legend = tag_chart.getLegend();
        legend.setOrientation(Legend.LegendOrientation.VERTICAL); // Set vertical orientation
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER); // Align vertically in the center
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT); // Align to the right
        legend.setDrawInside(false); // Ensure the legend is drawn outside the chart
        legend.setTextSize(14f); // Set text size for legend

// Optional: Set a custom text color for the legend (category names)
        legend.setTextColor(Color.BLACK);
        tag_chart.setDrawEntryLabels(false);

        //TODO: avoid making all visual updates every reload


    }


    // ------------- SET_TAG_LIST -------------


    private void setTagList(Context context) {

        // [FOR LATER] read from file (some resource they want or plain text)

        // Convert to List<String>.

        List<String> tagList = new ArrayList<>(Transaction.TAGS.keySet());
        tagList.add(0, "[TAG]"); // for appearances

        // Convert to ArrayAdapter<String>.

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, tagList);

        // Set spinner contents to adapter.

        tag_select.setAdapter(adapter);

    }


    // ____________________ ONCLICK_FUNCTIONS ____________________


    // ---------- SUBMIT_TRANSACTION ----------
    // Associated with the 'submit' button.

    public void submitTransaction(View view) {

        // [FOR LATER] (check that none are empty) <-- might make button just disabled until those are entered


        // Read entered parameters.

        double amount = Double.parseDouble(amount_input.getText().toString());
        String location = location_input.getText().toString();
        String tag = (String) tag_select.getSelectedItem();

        // Account for empty tag.

        if (tag.equals("[TAG]")) {
            tag = "MISC";
        }

        // Construct and append new Transaction obj to current month log.

        current_month.appendTransaction(new Transaction(location, amount, new Timestamp(), tag));

        // Write to file.

        updateFileContents();

        // Clear input fields.

        amount_input.getText().clear();
        location_input.getText().clear();

        // Update GUI.

        updateUI();

    }


    // -------- QUICK_ADD_TRANSACTION --------
    // Associated with 'pool' and 'bus' buttons.


    public void quickAddTransaction(View view) {

        // [NOTE / FOR LATER] Currently hardcoded, later on prolly fix this.

        if (view.getId() == R.id.quickadd_busfare) {
            current_month.appendTransaction(new Transaction("OC Transpo", 4.00, new Timestamp(), "BUSF"));
        }
        else if (view.getId() == R.id.quickadd_pool) {
            current_month.appendTransaction(new Transaction("Richcraft", 4.58, new Timestamp(), "POOL"));
        }

        // [NOTE] yeah just doxxed myself didnt i --> XD

        // Rewrite into file, and update terminal.

        updateFileContents();
        updateUI();

    }


    // ------------ OPEN_MONTH_LOG ------------ (coming later)


    public void openMonthLog(View view) {

    }

}    