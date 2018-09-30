package sg.edu.nus.iss.phoenix.maintainschedule.android.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

public class PopulateScreen extends AppCompatActivity  {
    Spinner spinner_1,spinner_2,spinner_3,spinner_4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_populate);
        Resources res = getResources();

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,res.getStringArray(R.array.yearNum));
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,res.getStringArray(R.array.weekcountNum));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_1 = (Spinner) findViewById(R.id.FromYear);
        spinner_1.setAdapter(adapter1);

        spinner_2 = (Spinner)findViewById(R.id.FromWeek);
        spinner_2.setAdapter(adapter2);

        spinner_3 = (Spinner)findViewById(R.id.ToYear);
        spinner_3.setAdapter(adapter1);

        spinner_4 = (Spinner)findViewById(R.id.ToWeek);
        spinner_4.setAdapter(adapter2);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        MenuItem menuItem1 = menu.findItem(R.id.action_delete);
        MenuItem menuItem2 = menu.findItem(R.id.action_copy);
        menuItem1.setVisible(false);
        menuItem2.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                String fromYear= String.valueOf(spinner_1.getSelectedItem());
                String fromWeek=String.valueOf(spinner_2.getSelectedItem());
                String toYear=String.valueOf(spinner_3.getSelectedItem());
                String toWeek=String.valueOf(spinner_4.getSelectedItem());
                ControlFactory.getMaintainScheduleController().selectPopulateSchedule(fromYear,fromWeek,toYear,toWeek);
                return true;

            // Respond to a click on the "Cancel" menu option
            case R.id.action_cancel:
                Log.v( "PopulateScreen","Canceling creating/editing populate schedule...");
                ControlFactory.getMaintainScheduleController().selectCancelCreatePopulateSchedule();
                return true;
        }

        return true;
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

}
