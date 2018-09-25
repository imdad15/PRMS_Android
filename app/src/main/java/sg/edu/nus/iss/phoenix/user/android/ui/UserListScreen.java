package sg.edu.nus.iss.phoenix.user.android.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 * Created by rahul on 23-09-2018.
 */

public class UserListScreen extends AppCompatActivity {
    private static final String TAG = UserListScreen.class.getName();
    private ListView mListView;
    private UserAdapter mUserAdapter;
    private User selectedUser = null;
    List<User> userList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);



        ArrayList<User> users = new ArrayList<User>();
        mUserAdapter = new UserAdapter(this, users);

        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlFactory.getUserController().selectCreateUser();
            }
        });

        mListView = (ListView) findViewById(R.id.user_pm_list);
        mListView.setAdapter(mUserAdapter);

        // Setup the item selection listener
        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Log.v(TAG, "Radio program at position " + position + " selected.");
                User user = (User) adapterView.getItemAtPosition(position);
                user.setEdit(Boolean.TRUE);
                // Log.v(TAG, "Radio program name is " + rp.getRadioProgramName());
                selectedUser = user;
                ControlFactory.getUserController().startUseCaseWithSelectedUser(selectedUser);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // your stuff
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setSelection(0);

        ControlFactory.getUserController().onDisplayUserList(this);
    }





  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "View" menu option
            case R.id.action_view:
                if (selectedUser == null) {
                    // Prompt for the selection of a radio program.
                    Toast.makeText(this, "Select a Schedule first! Use arrow keys on emulator", Toast.LENGTH_SHORT).show();
                    Log.v(TAG, "There is no selected Schedule.");
                }
                else {
                    Log.v(TAG, "Viewing Schedule: " + selectedUser.getProgramName() + "...");
                    ControlFactory.getScheduleController().selectSchedule(selectedUser);
                }
        }

        return true;
    }*/

  /*  @Override
    public void onBackPressed() {
        ControlFactory.getProgramController().maintainedProgram();
    } */

    @Override
    protected void onResume() {
        super.onResume();
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setSelection(0);

        ControlFactory.getUserController().onDisplayUserList(this);
    }

    public void showUsers(List<User> users ) {
        mUserAdapter.clear();
        this.userList = users;
        for (int i = 0; i <users.size(); i++) {
            mUserAdapter.add(users.get(i));
        }
    }

}
