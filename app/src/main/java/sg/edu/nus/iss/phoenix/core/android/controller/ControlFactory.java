package sg.edu.nus.iss.phoenix.core.android.controller;

import sg.edu.nus.iss.phoenix.authenticate.android.controller.LoginController;
import sg.edu.nus.iss.phoenix.maintainschedule.android.controller.ReviewSelectMaintainScheduleController;
import sg.edu.nus.iss.phoenix.radioprogram.android.controller.ProgramController;
import sg.edu.nus.iss.phoenix.radioprogram.android.controller.ReviewSelectProgramController;
import sg.edu.nus.iss.phoenix.user.android.controller.ReviewSelectUserController;
import  sg.edu.nus.iss.phoenix.maintainschedule.android.controller.MaintainScheduleController;
import sg.edu.nus.iss.phoenix.user.android.controller.MaintainUserController;

public class ControlFactory {
    private static MainController mainController = null;
    private static LoginController loginController = null;
    private static ProgramController programController = null;
    private static ReviewSelectProgramController reviewSelectProgramController = null;
    private static MaintainUserController userController = null;
    private static MaintainScheduleController maintainScheduleController = null;
    private static ReviewSelectMaintainScheduleController reviewSelectMaintainScheduleController=null;
    private static ReviewSelectUserController reviewSelectUserController = null;

    public static MainController getMainController() {
        if (mainController == null) mainController = new MainController();
        return mainController;
    }

    public static LoginController getLoginController() {
        if (loginController == null) loginController = new LoginController();
        return loginController;
    }

    public static ProgramController getProgramController() {
        if (programController == null) programController = new ProgramController();
        return programController;
    }

    public static ReviewSelectProgramController getReviewSelectProgramController() {
        if (reviewSelectProgramController == null)
            reviewSelectProgramController = new ReviewSelectProgramController();
        return reviewSelectProgramController;
    }

    public static MaintainUserController getUserController() {
        if (userController == null) userController = new MaintainUserController();
        return userController;
    }

    public static ReviewSelectUserController getReviewSelectPresenterProducerController() {
        if (reviewSelectUserController == null)
            reviewSelectUserController = new ReviewSelectUserController();
        return reviewSelectUserController;
    }

    public static MaintainScheduleController getMaintainScheduleController(){
        if(maintainScheduleController== null)
            maintainScheduleController=new MaintainScheduleController();
        return maintainScheduleController;
    }

    public static ReviewSelectMaintainScheduleController getReviewSelectMaintainScheduleController(){
        if(reviewSelectMaintainScheduleController== null)
            reviewSelectMaintainScheduleController=new ReviewSelectMaintainScheduleController();
        return reviewSelectMaintainScheduleController;
    }
}

