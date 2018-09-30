package sg.edu.nus.iss.phoenix.maintainschedule.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import sg.edu.nus.iss.phoenix.maintainschedule.android.ui.ScheduleScreen;
import sg.edu.nus.iss.phoenix.maintainschedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

public class ScheduleScreen extends AppCompatActivity  {
    // Tag for logging
    private static final String TAG = ScheduleScreen.class.getName();

    private EditText mRPNameEditText;
    private EditText mProducerNameEditText;
    private EditText mPresenterNameEditText;
    private EditText mRPDescEditText;
    private EditText mDurationEditText;
    private EditText mDateEditText;
    private EditText mStartTimeEditText;
    private EditText mAssignedByEditText;
    private Button selectRadioProgramButton;
    private Button selectPresenterButton;
    private Button selectProducerButton;


    private ProgramSlot psedit = null;
    KeyListener mRPNameEditTextKeyListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_slot);
        ControlFactory.getMainController().selectedSchedule(null);
        Log.v("ScheduleScreen:onCreate","Log2");

        // Find all relevant views that we will need to read user input from
        mRPNameEditText = (EditText) findViewById(R.id.maintain_schedule_program_name_text_view);

        mDurationEditText = (EditText) findViewById(R.id.maintain_schedule_program_duration_text_view);
        mProducerNameEditText=(EditText) findViewById(R.id.maintain_schedule_program_producer_text_view);
        mPresenterNameEditText=(EditText) findViewById(R.id.maintain_schedule_program_presenter_text_view);;
        mDateEditText=(EditText) findViewById(R.id.maintain_schedule_program_date_text_view);;
        mStartTimeEditText=(EditText) findViewById(R.id.maintain_schedule_program_start_time_text_view);;
        mAssignedByEditText=(EditText) findViewById(R.id.maintain_schedule_assigned_by_text_view);;

        // Keep the KeyListener for name EditText so as to enable editing after disabling it.
        mRPNameEditTextKeyListener = mRPNameEditText.getKeyListener();

        selectRadioProgramButton =(Button)findViewById(R.id.selectRadioProgram);
        selectPresenterButton=(Button)findViewById(R.id.selectPresenter);
        selectProducerButton=(Button)findViewById(R.id.selectProducer);


        selectRadioProgramButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlFactory.getReviewSelectProgramController().startUseCase();
                RadioProgram rp = ControlFactory.getMainController().selectedProgram();
                if (rp != null) {
                    mRPNameEditText.setText(rp.getRadioProgramName());
                    mDurationEditText.setText(rp.getRadioProgramDuration());
                    mRPNameEditText.setFocusable(false);
                    mDurationEditText.setFocusable(false);

                }

            }
        });

        selectPresenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        //        ControlFactory.getReviewSelectPresenterProducer().startUseCase();
        //        Presenter presenter = ControlFactory.getMainController().selectedPresenter();
        //        if (presenter != null) {
        //            mPresenterNameEditText.setText(presenter.getPresenterName());
        //            mPresenterNameEditText.setFocusable(false);
         //       }
            }


        });

        selectProducerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
        //    ControlFactory.getReviewSelectPresenterProducer().startUseCase();
        //    Producer producer = ControlFactory.getMainController().selectedProducer();
        //    if (producer != null) {
        //        mProducerNameEditText.setText(producer.getProducerName());
        //        mProducerNameEditText.setFocusable(false);
        //    }


        }
        });



    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ControlFactory.getMaintainScheduleController().onDisplayProgram(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
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
                    Log.v(TAG, "Saving Program Slot " + mRPNameEditText.getText().toString() + "...");
                    int yearNum = ControlFactory.getMaintainScheduleController().getYear();
                    int weekNum = ControlFactory.getMaintainScheduleController().getWeek();
                    ProgramSlot ps = new ProgramSlot(mRPNameEditText.getText().toString(), mPresenterNameEditText.getText().toString(),
                            mProducerNameEditText.getText().toString(), mDurationEditText.getText().toString(),
                            mDateEditText.getText().toString(), mStartTimeEditText.getText().toString(),
                            mAssignedByEditText.getText().toString());
                    ControlFactory.getMaintainScheduleController().selectCreateSchedule(ps); //This calls the create delegate
                } else { // Edited.
                    Log.v(TAG, "Saving radio program " + psedit.getRadioProgramName() + "...");
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

            case R.id.action_copy:
                Log.v(TAG, "Copying Program Slot: ");
                ControlFactory.getMaintainScheduleController().isCopyOperation(true);
                ControlFactory.getReviewSelectMaintainScheduleController().startUseCase();
                ProgramSlot ps = ControlFactory.getMainController().selectedSchedule();
                ControlFactory.getMaintainScheduleController().isCopyOperation(false);

                if (ps != null) {
                    mRPNameEditText.setText(ps.getRadioProgramName(), TextView.BufferType.EDITABLE);
                    mDurationEditText.setText(ps.getDuration(), TextView.BufferType.EDITABLE);
                    mProducerNameEditText.setText(ps.getProducerName(), TextView.BufferType.EDITABLE);
                    mPresenterNameEditText.setText(ps.getPresenterName(), TextView.BufferType.EDITABLE);
                    mRPNameEditText.setKeyListener(null);

                    mRPNameEditText.setFocusable(false);
                    mDurationEditText.setFocusable(false);
                    mProducerNameEditText.setFocusable(false);
                    mPresenterNameEditText.setFocusable(false);

                    selectRadioProgramButton.setEnabled(false);;
                    selectPresenterButton.setEnabled(false);;
                    selectProducerButton.setEnabled(false);;

                }
                return true;


        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Log.v(TAG, "Canceling creating/editing program slots...");
        ControlFactory.getMaintainScheduleController().selectCancelCreateEditSchedule();
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
        if (psedit != null) {
            mRPNameEditText.setText(psedit.getRadioProgramName(), TextView.BufferType.EDITABLE);
            mDurationEditText.setText(psedit.getDuration(), TextView.BufferType.EDITABLE);
            mProducerNameEditText.setText(psedit.getProducerName(),TextView.BufferType.EDITABLE);
            mPresenterNameEditText.setText(psedit.getPresenterName(),TextView.BufferType.EDITABLE);
            mRPNameEditText.setKeyListener(null);

        }
    }


}