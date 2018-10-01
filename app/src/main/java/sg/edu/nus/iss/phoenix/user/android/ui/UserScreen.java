package sg.edu.nus.iss.phoenix.user.android.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;


/**
 * Created by rahul on 25-09-2018.
 */

public class UserScreen extends AppCompatActivity {
    private static final String DATE_FORMAT = "dd MMM yyyy";

    List<User> userList;
    EditText userIdText;
    EditText usernameText;
    TextView addressLabel;
    EditText addressText;
    EditText passwordText;
    TextView dojLabel;
    EditText dojText;
    /* ImageView imageview;*/
    TextView siteLinkLabel;
    EditText siteLinkText;

    CheckBox showPassword;


    String roleHolder = "";

    boolean isEdit;

    SimpleDateFormat mDateFormatter;


    ListView userRolesView;

    SparseBooleanArray sparseBooleanArray;

    String[] roles = null;

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_screen);
        userRolesView = (ListView) findViewById(R.id.rolesView);

        roles = getResources().getStringArray(R.array.user_roles);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,
                        android.R.layout.simple_list_item_multiple_choice,
                        android.R.id.text1, roles);


        userRolesView.setAdapter(adapter);
        userIdText = (EditText) findViewById(R.id.user_id_text);
        usernameText = (EditText) findViewById(R.id.user_name_text);
        addressLabel = (TextView) findViewById(R.id.addresslabel);
        addressText = (EditText) findViewById(R.id.address_text);
        passwordText = (EditText) findViewById(R.id.password_text);
        dojLabel = (TextView) findViewById(R.id.dojlabel);
        dojText = (EditText) findViewById(R.id.doj_edit_text_view);
        siteLinkLabel = (TextView) findViewById(R.id.siteLinklabel);
        siteLinkText = (EditText) findViewById(R.id.sitelink_text);
        showPassword = (CheckBox) findViewById(R.id.showpassword);

        mDateFormatter = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        setListeners();
    }

    public void setListeners() {
        userRolesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                sparseBooleanArray = userRolesView.getCheckedItemPositions();
                roleHolder = "";
                int i = 0;

                while (i < sparseBooleanArray.size()) {
                    if (sparseBooleanArray.valueAt(i)) {
                        if (roleHolder != "") {
                            roleHolder = roleHolder + ",";
                        }
                        roleHolder += roles[sparseBooleanArray.keyAt(i)].toLowerCase() + "";
                    }
                    i++;
                }

                roleHolder = roleHolder.replaceAll("(,)*$", "");

                if (roleHolder.contains("presenter")) {
                    addressLabel.setVisibility(View.VISIBLE);
                    addressText.setVisibility(View.VISIBLE);
                    dojLabel.setVisibility(View.VISIBLE);
                    dojText.setVisibility(View.VISIBLE);
                    siteLinkText.setVisibility(View.VISIBLE);
                    siteLinkLabel.setVisibility(View.VISIBLE);
                } else if (roleHolder.contains("producer")) {
                    addressLabel.setVisibility(View.VISIBLE);
                    addressText.setVisibility(View.VISIBLE);
                    dojLabel.setVisibility(View.VISIBLE);
                    dojText.setVisibility(View.VISIBLE);
                    siteLinkText.setVisibility(View.GONE);
                    siteLinkLabel.setVisibility(View.GONE);

                } else {
                    addressText.setVisibility(View.GONE);
                    addressLabel.setVisibility(View.GONE);
                    dojText.setVisibility(View.GONE);
                    dojLabel.setVisibility(View.GONE);
                    siteLinkText.setVisibility(View.GONE);
                    siteLinkLabel.setVisibility(View.GONE);
                }
            }
        });

        View.OnClickListener checkboxListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v instanceof CheckBox) {
                    if (((CheckBox) v).isChecked()) {
                        passwordText.setInputType(InputType.TYPE_CLASS_TEXT);
                        passwordText.setEnabled(Boolean.TRUE);
                    } else {
                        passwordText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        passwordText.setEnabled(Boolean.FALSE);
                    }
                }
            }
        };
        showPassword.setOnClickListener(checkboxListener);

    }


    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    private void setUserRoles() {
        if (userList.size() > 0) {
            User user = userList.get(0);

            usernameText.setText(user.getName());
            userIdText.setText(user.getId());
            passwordText.setText(user.getPassword());
            dojText.setText(user.getDateOfJoining());
            addressText.setText(user.getAddress());
            siteLinkText.setText(user.getSiteLink());

            Log.v("User List data", user.getRoles().toString());
            if (user.getRoles() != null) {
                roleHolder = "";
                for (int i = 0; i < roles.length; i++) {
                    if (user.getRoles().get(0).getRole().contains(roles[i].toLowerCase())) {
                        userRolesView.setItemChecked(i, true);

                        if (roleHolder != "") {
                            roleHolder = roleHolder + ",";
                        }
                        roleHolder += roles[i].toLowerCase();
                    }
                }
            }
            if (roleHolder.contains("presenter")) {
                addressLabel.setVisibility(View.VISIBLE);
                addressText.setVisibility(View.VISIBLE);
                dojLabel.setVisibility(View.VISIBLE);
                dojText.setVisibility(View.VISIBLE);
                siteLinkText.setVisibility(View.VISIBLE);
                siteLinkLabel.setVisibility(View.VISIBLE);
            } else if (roleHolder.contains("producer")) {
                addressLabel.setVisibility(View.VISIBLE);
                addressText.setVisibility(View.VISIBLE);
                dojLabel.setVisibility(View.VISIBLE);
                dojText.setVisibility(View.VISIBLE);
                siteLinkText.setVisibility(View.GONE);
                siteLinkLabel.setVisibility(View.GONE);

            } else {
                addressText.setVisibility(View.GONE);
                addressLabel.setVisibility(View.GONE);
                dojText.setVisibility(View.GONE);
                dojLabel.setVisibility(View.GONE);
                siteLinkText.setVisibility(View.GONE);
                siteLinkLabel.setVisibility(View.GONE);
            }

        }

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        dojText.setOnFocusChangeListener(mDateFocusListener);
        ControlFactory.getUserController().onDisplayUser(this);
    }


    public void showUser(List<User> users) {
        this.userList = users;
        setUserRoles();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save radio program.
                if (validate()) {
                    if (userList != null && userList.size() > 0 && isEdit()) {

                        ControlFactory.getUserController().updateUser(getUserData());
                        finish();
                    } else {
                        ControlFactory.getUserController().createUser(getUserData());
                        finish();
                    }
                }
                break;

            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                ControlFactory.getUserController().deleteUser(getUserData());
                finish();
                break;

            // Respond to a click on the "Cancel" menu option
            case R.id.action_cancel:
                finish();
                break;
        }

        return true;
    }

    private DatePickerDialog getDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                dojText.setText(mDateFormatter.format(newDate.getTime()));
                ControlFactory.getMainController().hideKeyboard(UserScreen.this);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        return dialog;
    }

    private View.OnFocusChangeListener mDateFocusListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                getDialog().show();
            }
        }
    };

    public User getUserData() {
        ArrayList<Role> roles = new ArrayList<>();
        roleHolder = roleHolder.replaceAll("(,)*$", "");
        Log.v("ROLE HOLDER", roleHolder);
        String[] roleholders = roleHolder.split(",");
        for (int i = 0; i < roleholders.length; i++) {
            Role role = new Role();
            role.setRole(roleholders[i]);
            roles.add(role);
        }
        User user = new User();
        user.setId(String.valueOf(userIdText.getText()));
        user.setName(String.valueOf(usernameText.getText()));
        user.setPassword(String.valueOf(passwordText.getText()));
        user.setAddress(String.valueOf(addressText.getText()));
        user.setDateOfJoining(String.valueOf(dojText.getText()));
        user.setRoles(roles);
        user.setSiteLink(String.valueOf(siteLinkText.getText()));

        return user;
    }

    public boolean validate() {

        if (roleHolder == "") {
            Toast.makeText(UserScreen.this, "Atleast one role should be selected", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;

    }

}
