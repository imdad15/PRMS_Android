package sg.edu.nus.iss.phoenix.maintainschedule.android.controller;

import android.content.ContentResolver;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.maintainschedule.android.delegate.CreateScheduleDelegate;
import sg.edu.nus.iss.phoenix.maintainschedule.android.delegate.DeleteScheduleDelegate;
import sg.edu.nus.iss.phoenix.maintainschedule.android.delegate.ModifyScheduleDelegate;
import sg.edu.nus.iss.phoenix.maintainschedule.android.delegate.PopulateScheduleDelegate;
import sg.edu.nus.iss.phoenix.maintainschedule.android.delegate.RetrieveScheduleDelegate;
import sg.edu.nus.iss.phoenix.maintainschedule.android.ui.ScheduleListScreen;
import sg.edu.nus.iss.phoenix.maintainschedule.android.ui.ScheduleScreen;
import sg.edu.nus.iss.phoenix.maintainschedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.maintainschedule.android.ui.AnnualListScreen;

public class MaintainScheduleController {

    // Tag for logging.
    private static final String TAG = MaintainScheduleController.class.getName();

    private ScheduleListScreen scheduleListScreen;
    private ScheduleScreen scheduleScreen;
    private ProgramSlot psedit = null;
    private int year_num =0;
    private int week_num=0;
    boolean mCopy = false;

    public void startUseCase() {
        psedit = null;
        Intent intent = new Intent(MainController.getApp(), AnnualListScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplayScheduleList(ScheduleListScreen scheduleListScreen) {
        this.scheduleListScreen = scheduleListScreen;
        int yearNum= ControlFactory.getMaintainScheduleController().getYear();
        int weekNum= ControlFactory.getMaintainScheduleController().getWeek();
        new RetrieveScheduleDelegate(this).execute(String.valueOf(yearNum),String.valueOf(weekNum));
    }

    public void schedulesRetrieved(List<ProgramSlot> programSlots) {
        scheduleListScreen.showSchedules(programSlots);
    }

    public void selectCreateSchedule() {
        psedit = null;
        Intent intent = new Intent(MainController.getApp(), ScheduleScreen.class);
        MainController.displayScreen(intent);
    }

   public void selectEditProgramSlot(ProgramSlot programSlot) {
        psedit = programSlot;
        Log.v(TAG, "Editing radio program: " + programSlot.getRadioProgramName() + "...");

        Intent intent = new Intent(MainController.getApp(), ScheduleScreen.class);

        MainController.displayScreen(intent);
    }

    public void onDisplayProgram(ScheduleScreen maintainScheduleScreen) {
        this.scheduleScreen = maintainScheduleScreen;
        String action = maintainScheduleScreen.getIntent().getAction();
        if(!TextUtils.isEmpty(action) && action.equals("Copy")){
            maintainScheduleScreen.setCopiedData();
        } else {
            if (psedit == null)
                maintainScheduleScreen.createProgramSlot();
            else
                scheduleScreen.editSchedule(psedit);
        }
    }



    public void selectModifySchedule(ProgramSlot ps) {
        new ModifyScheduleDelegate(this).execute(ps);
    }

    public void selectDeleteSchedule(ProgramSlot ps) {
        new DeleteScheduleDelegate(this).execute(ps);
    }

    public void selectPopulateSchedule(String fromYear,String fromWeek,String toYear, String toWeek){
        new PopulateScheduleDelegate(this).execute(fromYear,fromWeek,toYear,toWeek);
    }

    public void scheduleDeleted(boolean success) {
        // Go back to Schedule List screen with refreshed programs.
        startUseCase();
    }

    public void scheduleModified(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUseCase();
    }

    public void selectCreateSchedule(ProgramSlot ps) {
        new CreateScheduleDelegate(this).execute(ps);
    }

    public void scheduleCreated(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        startUseCase();
    }

    public void selectCancelCreateEditSchedule() {
        // Go back to Annual List screen with refreshed programs.
        startUseCase();
    }

    public void selectCancelCreatePopulateSchedule(){
        startUseCase();
    }


    public void maintainedProgram() {
        ControlFactory.getMainController().maintainedProgram();
    }

    public int getYear(){
        return year_num;
    }

    public void setYear(int year){
        year_num = year;
    }

    public int getWeek(){
        return week_num;
    }


    public void setWeek(int week){
        week_num = week;
    }

    public void isCopyOperation(boolean val){
        mCopy=val;
    }




}
