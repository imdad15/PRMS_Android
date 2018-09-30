package sg.edu.nus.iss.phoenix.user.controller;

import android.content.Intent;

import sg.edu.nus.iss.phoenix.authenticate.android.ui.LoginScreen;
import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.radioprogram.android.ui.ReviewSelectProgramScreen;
import sg.edu.nus.iss.phoenix.user.delegate.ReviewSelectUserDelegate;
import sg.edu.nus.iss.phoenix.user.entity.User;
import sg.edu.nus.iss.phoenix.user.entity.Users;
import sg.edu.nus.iss.phoenix.user.ui.ReviewSelectUserScreen;

public class ReviewSelectUserController {

    ReviewSelectUserScreen mReviewSelectUserScreen;
    String role;

    public void startUseCase(String role) {
        this.role = role;
        Intent intent = new Intent(MainController.getApp(), ReviewSelectProgramScreen.class);
        intent.putExtra("role", role);
        MainController.displayScreen(intent);
    }

    public void onDisplay(ReviewSelectUserScreen reviewSelectUserScreen, String role) {
        this.mReviewSelectUserScreen = reviewSelectUserScreen;
        new ReviewSelectUserDelegate(this).execute(role);
        mReviewSelectUserScreen.showLoadingIndicator();
    }

    public void usersRetrieved(Users users) {
        mReviewSelectUserScreen.hideLoadingIndicator();
        mReviewSelectUserScreen.showUsers(users);
    }

    public void setSelectedUser(User user) {
        Intent intent = new Intent(MainController.getApp(), LoginScreen.class);
        intent.putExtra("role", role);
        intent.putExtra("username", user.getName());
        MainController.displayScreen(intent);
    }
}
