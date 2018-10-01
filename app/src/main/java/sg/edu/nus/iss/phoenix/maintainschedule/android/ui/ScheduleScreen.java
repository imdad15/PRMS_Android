package sg.edu.nus.iss.phoenix.maintainschedule.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.maintainschedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.radioprogram.android.ui.ReviewSelectProgramScreen;
import sg.edu.nus.iss.phoenix.user.android.ui.ReviewSelectUserScreen;

public class ScheduleScreen extends AppCompatActivity {
    // Tag for logging
    private static final String TAG = ScheduleScreen.class.getName();
    public static final int REQUEST_CODE_PROGRAM_SLOT = 2;
    public static final int REQUEST_CODE_PRODUCER = 3;
    public static final int REQUEST_CODE_PRESENTER = 4;

    private EditText mRPNameEditText;
    private EditText mProducerNameEditText;
    private EditText mPresenterNameEditText;
    private EditText mDurationEditText;
    private EditText mDateEditText;
    private EditText mStartTimeEditText;
    private EditText mAssignedByEditText;
    private Button mSelectRadioProgramButton;
    private Button mSelectPresenterButton;
    private Button mSelectProducerButton;
    //private Menu mMenu;

    private ProgramSlot psedit = null;
    KeyListener mRPNameEditTextKeyListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_slot);
        ControlFactory.getMainController().selectedSchedule(null);

        // Find all relevant views that we will need to read user input from
        mRPNameEditText = (EditText) findViewById(R.id.maintain_schedule_program_name_text_view);

        mDurationEditText = (EditText) findViewById(R.id.maintain_schedule_program_duration_text_view);
        mProducerNameEditText = (EditText) findViewById(R.id.maintain_schedule_program_producer_text_view);
        mPresenterNameEditText = (EditText) findViewById(R.id.maintain_schedule_program_presenter_text_view);
        mDateEditText = (EditText) findViewById(R.id.maintain_schedule_program_date_text_view);
        mStartTimeEditText = (EditText) findViewById(R.id.maintain_schedule_program_start_time_text_view);
        mAssignedByEditText = (EditText) findViewById(R.id.maintain_schedule_assigned_by_text_view);

        // Keep the KeyListener for name EditText so as to enable editing after disabling it.
        mRPNameEditTextKeyListener = mRPNameEditText.getKeyListener();

        mSelectRadioProgramButton = (Button) findViewById(R.id.selectRadioProgram);
        mSelectPresenterButton = (Button) findViewById(R.id.selectPresenter);
        mSelectProducerButton = (Button) findViewById(R.id.selectProducer);


        mSelectRadioProgramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ControlFactory.getReviewSelectProgramController().startUseCase();
                Intent intent = new Intent(ScheduleScreen.this, ReviewSelectProgramScreen.class);
                startActivityForResult(intent, REQUEST_CODE_PROGRAM_SLOT);

            }
        });

        mSelectPresenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewSelectUser(REQUEST_CODE_PRESENTER);
            }


        });

        mSelectProducerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewSelectUser(REQUEST_CODE_PRODUCER);
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ControlFactory.getMaintainScheduleController().onDisplayProgram(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PROGRAM_SLOT && resultCode == RESULT_OK) {
            String programName = data.getStringExtra("program_name");
            String duration = data.getStringExtra("duration");
            //RadioProgram rp = ControlFactory.getMainController().selectedProgram();
            if (programName != null && !programName.isEmpty()) {
                mRPNameEditText.setText(programName);
            }
            if (duration != null && !duration.isEmpty()) {
                mDurationEditText.setText(duration);
            }

        } else if (requestCode == REQUEST_CODE_PRODUCER && resultCode == RESULT_OK) {
            String producerName = data.getStringExtra("selected_user");
            if (!TextUtils.isEmpty(producerName)) {
                mProducerNameEditText.setText(producerName);
            }
        } else if (requestCode == REQUEST_CODE_PRESENTER && resultCode == RESULT_OK) {
            String presenterName = data.getStringExtra("selected_user");
            if (!TextUtils.isEmpty(presenterName)) {
                mPresenterNameEditText.setText(presenterName);
            }
        }
        mRPNameEditText.setFocusable(false);
        mDurationEditText.setFocusable(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        //mMenu = menu;
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new radioprogram, hide the "Delete" menu item.
        if (psedit == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save radio program.
                if (psedit == null) { // Newly created.
                    Log.v(TAG, "Saving Prpointyogram Slot " + mRPNameEditText.getText().toString() + "...");
                    int yearNum = ControlFactory.getMaintainScheduleController().getYear();
                    int weekNum = ControlFactory.getMaintainScheduleController().getWeek();
                    ProgramSlot ps = new ProgramSlot(mRPNameEditText.getText().toString(), mPresenterNameEditText.getText().toString(),
                            mProducerNameEditText.getText().toString(), mDurationEditText.getText().toString(),
                            mDateEditText.getText().toString(), mStartTimeEditText.getText().toString(),
                            mAssignedByEditText.getText().toString());
                    ControlFactory.getMaintainScheduleController().selectCreateSchedule(ps); //This calls the create delegate
                } else { // Edited.
                    Log.v(TAG, "Saving radio program " + psedit.getRadioProgramName() + "...");
                    psedit = new ProgramSlot(mRPNameEditText.getText().toString(), mPresenterNameEditText.getText().toString(),
                            mProducerNameEditText.getText().toString(), mDurationEditText.getText().toString(),
                            mDateEditText.getText().toString(), mStartTimeEditText.getText().toString(),
                            mAssignedByEditText.getText().toString());
                    ControlFactory.getMaintainScheduleController().selectModifySchedule(psedit); //This calls the modify delegate
                }
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                Log.v(TAG, "Deleting Program Slot " + psedit.getRadioProgramName() + "...");
                ControlFactory.getMaintainScheduleController().selectDeleteSchedule(psedit); //This calls delete delegate
                return true;
            // Respond to a click on the "Cancel" menu option */
            case R.id.action_cancel:
                Log.v(TAG, "Canceling creating/editing program slots...");
                ControlFactory.getMaintainScheduleController().selectCancelCreateEditSchedule();
                return true;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void createProgramSlot() {
        this.psedit = null;
        mRPNameEditText.setText("", TextView.BufferType.EDITABLE);
        mDurationEditText.setText("", TextView.BufferType.EDITABLE);

        mProducerNameEditText.setText("", TextView.BufferType.EDITABLE);
        mPresenterNameEditText.setText("", TextView.BufferType.EDITABLE);
        mDateEditText.setText("", TextView.BufferType.EDITABLE);
        mStartTimeEditText.setText("", TextView.BufferType.EDITABLE);
        mAssignedByEditText.setText("", TextView.BufferType.EDITABLE);
        mRPNameEditText.setKeyListener(mRPNameEditTextKeyListener);
        mRPNameEditText.setFocusable(false);
        mDurationEditText.setFocusable(false);
        mProducerNameEditText.setFocusable(false);
        mPresenterNameEditText.setFocusable(false);

    }

    public void editSchedule(ProgramSlot psedit) {
        this.psedit = psedit;
        //MenuItem menuItem = mMenu.findItem(R.id.action_copy);

        if (psedit != null) {
            if (!TextUtils.isEmpty(psedit.getRadioProgramName())) {
                mRPNameEditText.setText(psedit.getRadioProgramName(), TextView.BufferType.EDITABLE);
            }
            if (!TextUtils.isEmpty(psedit.getDuration())) {
                mDurationEditText.setText(psedit.getDuration(), TextView.BufferType.EDITABLE);
            }
            if (!TextUtils.isEmpty(psedit.getProducerName())) {
                mProducerNameEditText.setText(psedit.getProducerName(), TextView.BufferType.EDITABLE);
            }
            if (!TextUtils.isEmpty(psedit.getPresenterName())) {
                mPresenterNameEditText.setText(psedit.getPresenterName(), TextView.BufferType.EDITABLE);
            }
            if (!TextUtils.isEmpty(psedit.getDate())) {
                mDateEditText.setText(psedit.getDate(), TextView.BufferType.EDITABLE);
            }
            if (!TextUtils.isEmpty(psedit.getStartTime())) {
                mStartTimeEditText.setText(psedit.getStartTime(), TextView.BufferType.EDITABLE);
            }
            if (!TextUtils.isEmpty(psedit.getAssignedBy())) {
                mAssignedByEditText.setText(psedit.getAssignedBy(), TextView.BufferType.EDITABLE);
            }
            mRPNameEditText.setKeyListener(null);
            //menuItem.setVisible(false);
        } else {
            //menuItem.setVisible(true);
        }
    }

    public void reviewSelectUser(int requestCode) {
        Intent intent = new Intent(ScheduleScreen.this, ReviewSelectUserScreen.class);
        if (requestCode == REQUEST_CODE_PRESENTER)
            intent.putExtra("role", "presenter");
        else {
            intent.putExtra("role", "producer");
        }
        startActivityForResult(intent, requestCode);
    }

    public void setCopiedData() {
        Intent intent = getIntent();
        Log.d("TAG", intent.getStringExtra("rp_name"));
        mRPNameEditText.setText(intent.getStringExtra("rp_name"));
        mPresenterNameEditText.setText(intent.getStringExtra("presenter"));
        mProducerNameEditText.setText(intent.getStringExtra("producer"));
        mDurationEditText.setText(intent.getStringExtra("duration"));
    }

    public void notifyUpdate(Boolean isSuccess) {
        if (isSuccess){
            Toast.makeText(this, "Update Successfull", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Update failed. Please try again!", Toast.LENGTH_SHORT).show();
        }
    }
}
