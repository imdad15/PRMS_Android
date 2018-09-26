package sg.edu.nus.iss.phoenix.user.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.user.android.controller.MaintainUserController;
import sg.edu.nus.iss.phoenix.user.entity.Role;
import sg.edu.nus.iss.phoenix.user.entity.User;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_USERS;

/**
 * Created by rahul on 23-09-2018.
 */

public class UpdateUserDelegate extends AsyncTask<User, Void, Boolean> {
    private static final String TAG = UpdateUserDelegate.class.getName();

    private final MaintainUserController userController;

    public UpdateUserDelegate(MaintainUserController userController) {
        this.userController = userController;
    }

    @Override
    protected Boolean doInBackground(User... params) {
        Uri builtUri = Uri.parse(PRMS_BASE_URL_USERS).buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri,"update").buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return new Boolean(false);
        }

        JSONObject json = new JSONObject();
        try {
            json.put("id", params[0].getId());
            json.put("name", params[0].getName());
            json.put("password", params[0].getPassword());
            json.put("address", params[0].getAddress());
            json.put("dojStr", params[0].getDateOfJoining());
            json.put("siteLink", params[0].getSiteLink());


            ArrayList<Role> roles = params[0].getRoles();
            String rolestr = "";
            JSONObject rolesJson = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for(int i=0;i<roles.size();i++) {
                //JSONObject obj = new JSONObject();
                //obj.put("role",temprole.getRole());
                if(rolestr!="")
                {
                    rolestr = rolestr + ":";
                }

                rolestr = rolestr + roles.get(i).getRole();
                jsonArray.put(roles.get(i));
            }
            // jsonArray.

            rolesJson.put("roles",jsonArray);
            json.put("roles",rolesJson.getJSONArray("roles"));
            Log.v("rolestr",rolestr);
            json.put("roleArray",rolestr);


        } catch (JSONException e) {
            Log.v(TAG, e.getMessage());
        }

        boolean success = false;
        HttpURLConnection httpURLConnection = null;
        DataOutputStream dos = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            httpURLConnection.setDoOutput(true);
            dos = new DataOutputStream(httpURLConnection.getOutputStream());
            dos.writeUTF(json.toString());
            dos.write(512);
            Log.v(TAG, "Http POST response " + httpURLConnection.getResponseCode());
            success = true;
        } catch (IOException exception) {
            Log.v(TAG, exception.getMessage());
        } finally {
            if (dos != null) {
                try {
                    dos.flush();
                    dos.close();
                } catch (IOException exception) {
                    Log.v(TAG, exception.getMessage());
                }
            }
            if (httpURLConnection != null) httpURLConnection.disconnect();
        }
        return new Boolean(success);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        userController.userUpdated(result.booleanValue());
    }


}
