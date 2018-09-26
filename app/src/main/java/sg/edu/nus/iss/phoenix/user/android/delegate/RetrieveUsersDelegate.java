package sg.edu.nus.iss.phoenix.user.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sg.edu.nus.iss.phoenix.user.android.controller.MaintainUserController;
import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_USERS;

/**
 * Created by rahul on 24-09-2018.
 */

public class RetrieveUsersDelegate extends AsyncTask<String, Void, String> {
    private static final String TAG = RetrieveUsersDelegate.class.getName();

    private MaintainUserController userController = null;

    public RetrieveUsersDelegate(MaintainUserController userController) {
        this.userController = userController;
    }


    @Override
    protected String doInBackground(String... params) {
        Uri builtUri1 = Uri.parse(PRMS_BASE_URL_USERS).buildUpon().build();
        Uri builtUri = Uri.withAppendedPath(builtUri1, params[0]).buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return e.getMessage();
        }

        String jsonResp = null;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) jsonResp = scanner.next();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }

        return jsonResp;
    }

    @Override
    protected void onPostExecute(String result) {
        List<User> users = new ArrayList<>();

        if (result != null && !result.equals("")) {
            try {
                JSONObject reader = new JSONObject(result);
                JSONArray psArray = reader.getJSONArray("userList");
                Log.d("PS list", psArray.toString());
                for (int i = 0; i < psArray.length(); i++) {
                    JSONObject ulJson = psArray.getJSONObject(i);
                    ArrayList<String> roles = new ArrayList<>();
                    String id = ulJson.getString("id");
                    String userName = ulJson.getString("name");
                    String password = null;
                    if(ulJson.has("password")) {
                        password = ulJson.getString("password");
                    }
                    JSONArray rolesArray = ulJson.getJSONArray("roles");
                    String address = null;
                    if(ulJson.has("address")) {
                        address = ulJson.getString("address");
                    }
                    String doj = null;
                    if(ulJson.has("doj")) {
                        doj = ulJson.getString("doj");
                    }

                    String siteLink = null;
                    if(ulJson.has("siteLink")) {
                        siteLink = ulJson.getString("siteLink");
                    }

                    ArrayList<Role> rolesList = new ArrayList<>();
                    Role role = new Role();


                    for(int j = 0; j < rolesArray.length(); j++)
                    {
                        JSONObject roleJson = rolesArray.getJSONObject(j);
                        roles.add(roleJson.getString("role"));

                    }


                    role.setRole(roles.toString());
                    rolesList.add(role);
                    users.add(new User(userName, password,
                            id, rolesList,address,doj,siteLink));
                }
            } catch (JSONException e) {
                Log.v(TAG, e.getMessage());
            }
        } else {
            Log.v(TAG, "JSON response error.");
        }
        Log.v(TAG, "User controller status"+userController);
        Log.v(TAG, "User retreived status"+users.size());
        if (userController != null)
            userController.userRetrieved(users);
    }


}
