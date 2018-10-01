package sg.edu.nus.iss.phoenix.maintainschedule.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.maintainschedule.entity.ProgramSlot;

public class ScheduleListScreen extends AppCompatActivity {
    // Tag for logging
    private static final String TAG = ScheduleListScreen.class.getName();

    private ListView mListView;
    private ProgramSlotAdapter mPSAdapter;
    private ProgramSlot selectedPS = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programslot_list);


        ArrayList<ProgramSlot> programSlots = new ArrayList<ProgramSlot>();
        mPSAdapter = new ProgramSlotAdapter(this, programSlots);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlFactory.getMaintainScheduleController().selectCreateSchedule();
            }
        });

        mListView = (ListView) findViewById(R.id.program_slot_list);
        mListView.setAdapter(mPSAdapter);

        // Setup the item selection listener
        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ProgramSlot ps = (ProgramSlot) adapterView.getItemAtPosition(position);
                selectedPS = ps;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //Not in use
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setSelection(0);

        ControlFactory.getMaintainScheduleController().onDisplayScheduleList(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        MenuItem menuItem = menu.findItem(R.id.action_populate);
        menuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "View" menu option
            case R.id.action_view:
                if (selectedPS == null) {
                    // Prompt for the selection of a radio program.
                    Toast.makeText(this, "Select a program slot first! Use arrow keys on emulator", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "There is no selected program slot.");
                }
                else {
                    Log.v(TAG, "Viewing program slots: " + selectedPS.getRadioProgramName() + "...");
                    ControlFactory.getMaintainScheduleController().selectEditProgramSlot(selectedPS);
                }
                return true;
            case R.id.action_copy:
                if(selectedPS==null){
                    Toast.makeText(this, "Select a program slot first! Use arrow keys on emulator", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "There is no selected program slot.");
                } else {
                    Intent intent = new Intent(ScheduleListScreen.this, AnnualListScreen.class);
                    intent.setAction("Copy");
                    intent.putExtra("rp_name",selectedPS.getRadioProgramName());
                    intent.putExtra("presenter", selectedPS.getPresenterName());
                    intent.putExtra("producer", selectedPS.getProducerName());
                    intent.putExtra("duration", selectedPS.getDuration());
                    startActivity(intent);
                }
                return true;
        }
    return true;

    }

    @Override
    public void onBackPressed() {
        ControlFactory.getMaintainScheduleController().maintainedProgram();
    }

    public void showSchedules(List<ProgramSlot> programSlots) {
        mPSAdapter.clear();
        for (int i = 0; i < programSlots.size(); i++) {
            mPSAdapter.add(programSlots.get(i));
        }
    }
}
