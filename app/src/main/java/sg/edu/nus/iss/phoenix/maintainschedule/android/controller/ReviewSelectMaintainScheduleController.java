package sg.edu.nus.iss.phoenix.maintainschedule.android.controller;

import android.content.Intent;
import android.util.Log;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.maintainschedule.android.delegate.RetrieveScheduleDelegate;
import sg.edu.nus.iss.phoenix.maintainschedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.radioprogram.android.controller.ReviewSelectProgramController;
import sg.edu.nus.iss.phoenix.radioprogram.android.delegate.RetrieveProgramsDelegate;
import sg.edu.nus.iss.phoenix.maintainschedule.android.ui.ReviewSelectScheduleScreen;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

public class ReviewSelectMaintainScheduleController {
    // Tag for logging.
    private static final String TAG = ReviewSelectMaintainScheduleController.class.getName();

    private ReviewSelectScheduleScreen reviewSelectScheduleScreen;
    private ProgramSlot psSelected = null;
    private int year_num =0;
    private int week_num=0;

    public void startUseCase() {
        psSelected = null;
        Intent intent = new Intent(MainController.getApp(), ReviewSelectScheduleScreen.class);
        MainController.displayScreen(intent);
    }

    public void onDisplay(ReviewSelectScheduleScreen reviewSelectScheduleScreen) {
        this.reviewSelectScheduleScreen = reviewSelectScheduleScreen;
        int yearNum= ControlFactory.getReviewSelectMaintainScheduleController().getYear();
        int weekNum= ControlFactory.getReviewSelectMaintainScheduleController().getWeek();
        new RetrieveScheduleDelegate(this).execute(String.valueOf(yearNum),String.valueOf(weekNum));
    }

    public void schedulesRetrieved(List<ProgramSlot> programSlots) {
        reviewSelectScheduleScreen.showSchedules(programSlots);
    }

    public void selectSchedule(ProgramSlot programSlot) {
        psSelected = programSlot;
        Log.v(TAG, "Selected Program Slot: " + programSlot.getRadioProgramName() + ".");
        // To call the base use case controller with the selected radio program.
        // At present, call the MainController instead.
        ControlFactory.getMainController().selectedSchedule(psSelected);
    }

    public void selectCancel() {
        psSelected = null;
        Log.v(TAG, "Cancelled the selection of program slot.");
        // To call the base use case controller without selection;
        // At present, call the MainController instead.
        ControlFactory.getMainController().selectedSchedule(psSelected);
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


}
