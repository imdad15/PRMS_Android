package sg.edu.nus.iss.phoenix.user.android.controller;

import android.content.Intent;
import android.widget.Toast;
import android.util.Log;

import java.util.List;

import sg.edu.nus.iss.phoenix.core.android.controller.MainController;
import sg.edu.nus.iss.phoenix.user.android.delegate.CreateUserDelegate;
import sg.edu.nus.iss.phoenix.user.android.delegate.DeleteUserDelegate;
import sg.edu.nus.iss.phoenix.user.android.delegate.RetrieveUsersDelegate;
import sg.edu.nus.iss.phoenix.user.android.delegate.UpdateUserDelegate;
import sg.edu.nus.iss.phoenix.user.android.ui.MaintainUserScreen;
import sg.edu.nus.iss.phoenix.user.android.ui.UserListScreen;
import sg.edu.nus.iss.phoenix.user.android.ui.UserScreen;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 * Created by rahul on 23-09-2018.
 */

public class MaintainUserController {
    private static final String TAG = MaintainUserController.class.getName();

    private UserListScreen userListScreen;
    private UserScreen userScreen;
    private User user;
    private User selectedUser;

    /**
     * It is used to display the User List Screen.
     */

    public void startUseCase() {
        this.user = null;
        selectedUser = null;
        Intent intent = new Intent(MainController.getApp(), UserListScreen.class);
        MainController.displayScreen(intent);
    }


    /**
     * It is a use case to pass the details of specific user to the screen.
     * @param selectedUser
     */

    public void startUseCaseWithSelectedUser(User selectedUser) {
        this.user = null;
        this.selectedUser = selectedUser;
        setSelectedUser(selectedUser);
        selectCreateUser();
    }


    /**
     * It is used to display a list of users on the User List screen.
     * @param userListScreen
     */

    public void onDisplayUserList(UserListScreen userListScreen) {
        this.userListScreen = userListScreen;
        new RetrieveUsersDelegate(this).execute("all");
    }

    public void selectUsers() {
        user = null;
        Intent intent = new Intent(MainController.getApp(), UserListScreen.class);
        MainController.displayScreen(intent);
    }

    /**
     * It is used to display the parameters for creating the user.
     */

    public void selectCreateUser() {
        user = null;
        Intent intent = new Intent(MainController.getApp(), UserScreen.class);
        MainController.displayScreen(intent);
    }

    /**
     * It is used to select a specific user from the list of users available.
     * @param user
     */
    public void setSelectedUser(User user) {
        new RetrieveUsersDelegate(this).execute("get/"+user.getId());
    }


    /**
     * It is used to pass the user details to the user screen.
     * @param userScreen
     */
    public void onDisplayUser(UserScreen userScreen) {
        this.userScreen = userScreen;
        //this.userScreen.setUserList();
    }


    /**
     * It is used to delete a specific user
     * @param user
     */

    public void deleteUser(User user)
    {
        new DeleteUserDelegate(this).execute(user.getId());
    }

    /**
     * It is used to retreive the response of updating the details of the User
     * @param success
     */
    public void userUpdated(boolean success) {
        // Go back to ProgramList screen with refreshed programs
        Toast.makeText(userListScreen, "User updated successfully", Toast.LENGTH_LONG).show();
    }

    /**
     * It is used to update the details of the existing user
     * @param user
     */
    public void updateUser(User user) {

        new UpdateUserDelegate(this).execute(user);
    }


    /**
     * It is used to create a new User
     * @param user
     */
    public void createUser(User user) {
        // Go back to ProgramList screen with refreshed programs.
        new CreateUserDelegate(this).execute(user);

    }


    /**
     * It is used to retrieve the response of creation of the user.
     * @param success
     */

    public void userCreated(boolean success) {
        // Go back to ProgramList screen with refreshed programs.
        Toast.makeText(userListScreen, "User created successfully", Toast.LENGTH_LONG).show();

    }

    /**
     *  It passes the id of the user as an argument to delete the user  through webservice call.
     * @param id
     */
    public void userDeleted(String id) {
        // Go back to ProgramList screen with refreshed programs.
        Toast.makeText(userListScreen, "User "+id+" deleted successfully", Toast.LENGTH_LONG).show();

    }

    /**
     * It retrieves the list of users.
     * @param users
     */
    public void userRetrieved(List<User> users) {
        userListScreen.showUsers(users);
        Log.v("User screen status", String.valueOf(userScreen));
        if(userScreen!=null)
        {
            userScreen.showUser(users);
            userScreen.setEdit(Boolean.TRUE);
        }
    }


}
