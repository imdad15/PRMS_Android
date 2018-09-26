package sg.edu.nus.iss.phoenix.user.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import sg.edu.nus.iss.phoenix.user.android.controller.MaintainUserController;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_USERS;

/**
 * Created by rahul on 23-09-2018.
 */

public class DeleteUserDelegate extends AsyncTask<String, Void, String> {
    private static final String TAG = DeleteUserDelegate.class.getName();

    private final MaintainUserController userController;

    public DeleteUserDelegate(MaintainUserController userController) {
        this.userController = userController;
    }

    @Override
    protected String doInBackground(String... params) {
        // Encode the name of radio program in case of the presence of special characters.
        String name = null;
        try {
            name = URLEncoder.encode(params[0], "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.v(TAG, e.getMessage());
            return null;
        }
        Uri builtUri = Uri.parse(PRMS_BASE_URL_USERS).buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri,"delete").buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri, name).buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return null;
        }

        boolean success = false;
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestMethod("DELETE");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            httpURLConnection.setUseCaches (false);
            System.out.println(httpURLConnection.getResponseCode());
            Log.v(TAG, "Http DELETE response " + httpURLConnection.getResponseCode());
            success = true;
        } catch (IOException exception) {
            Log.v(TAG, exception.getMessage());
        } finally {
            if (httpURLConnection != null) httpURLConnection.disconnect();
        }
        return params[0];
    }

    @Override
    protected void onPostExecute(String result) {
        userController.userDeleted(result);

    }

}
