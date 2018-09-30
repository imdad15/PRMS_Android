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
import java.util.Scanner;

import sg.edu.nus.iss.phoenix.user.android.controller.ReviewSelectUserController;
import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;
import sg.edu.nus.iss.phoenix.user.entity.Users;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_USER_LIST;

public class ReviewSelectUserDelegate extends AsyncTask<String, Void, Users> {

    private static final String TAG = ReviewSelectUserDelegate.class.getName();
    private ReviewSelectUserController mReviewSelectUserController;

    public ReviewSelectUserDelegate(ReviewSelectUserController reviewSelectUserController) {
        mReviewSelectUserController = reviewSelectUserController;
    }

    @Override
    protected Users doInBackground(String... params) {
        Uri builtUri = Uri.parse(PRMS_BASE_URL_USER_LIST).buildUpon()
                .appendQueryParameter("role", params[0]).build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return null;
        }

        String response = null;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) response = scanner.next();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
        if (response == null)
            return null;

        try {
            return parseResponse(response);
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(Users users) {
        if (mReviewSelectUserController != null) {
            mReviewSelectUserController.usersRetrieved(users);
        }
    }

    private Users parseResponse(String response) throws JSONException {
        JSONObject jsonResponse = new JSONObject(response);
        JSONArray userList = jsonResponse.getJSONArray("userList");
        Users users = new Users();
        users.setUserList(new ArrayList<User>());

        for (int i = 0; i < userList.length(); i++) {

            ArrayList<Role> roleList = new ArrayList<>();
            JSONArray jsonRoles = userList.getJSONObject(i).getJSONArray("roles");
            if (jsonRoles != null) {
                for (int j = 0; j < jsonRoles.length(); j++) {
                    roleList.add(new Role(jsonRoles.getString(j)));
                }
            }
            users.getUserList().add(new User(userList.getJSONObject(i).getString("name"), roleList));
        }
        return users;
    }

}
