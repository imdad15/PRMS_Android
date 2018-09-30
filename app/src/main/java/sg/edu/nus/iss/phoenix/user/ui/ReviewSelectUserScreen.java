package sg.edu.nus.iss.phoenix.user.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.user.entity.User;
import sg.edu.nus.iss.phoenix.user.entity.Users;

public class ReviewSelectUserScreen extends AppCompatActivity {

    ListView mUserListview;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private User selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_select_user);
        mUserListview = (ListView) findViewById(R.id.listview_user);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        String role = "producer";//getIntent().getStringExtra("role");
        ControlFactory.getReviewSelectPresenterProducerController().onDisplay(this,role);
    }

    public void showUsers(Users users){
        if(users==null || users.getUserList()==null || users.getUserList().isEmpty()){
            showErrorMessage();
            return;
        }
        ArrayList<User> userList = (ArrayList<User>) users.getUserList();
        mUserListview.setAdapter(new UserListAdapter(this, userList));
        mUserListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                User user = (User) adapterView.getItemAtPosition(position);
                selectedUser = user;
                ControlFactory.getReviewSelectPresenterProducerController().setSelectedUser(user);
            }
        });

    }

    public void showLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }

    public void hideLoadingIndicator() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
    }

    // Show the error and hide the loader.
    public void showErrorMessage() {
        hideLoadingIndicator();
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        // LoginScreen does not currently support Back button.
        return;
    }
}
