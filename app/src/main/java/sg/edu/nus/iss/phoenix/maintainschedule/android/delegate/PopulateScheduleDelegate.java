package sg.edu.nus.iss.phoenix.maintainschedule.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import sg.edu.nus.iss.phoenix.maintainschedule.android.controller.MaintainScheduleController;
import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_MAINTAIN_SCHEDULE;

public class PopulateScheduleDelegate extends AsyncTask<String,Void,Boolean> {
    // Tag for logging
    private static final String TAG = ModifyScheduleDelegate.class.getName();

    private final MaintainScheduleController maintainScheduleController;

    public PopulateScheduleDelegate(MaintainScheduleController maintainScheduleController) {
        this.maintainScheduleController = maintainScheduleController;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        Uri builtUri = Uri.parse(PRMS_BASE_URL_MAINTAIN_SCHEDULE).buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri, "populate").buildUpon().build();
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
            json.put("FromYear", params[0]);
            json.put("FromWeek", params[1]);
            json.put("ToYear", params[2]);
            json.put("ToWeek", params[3]);

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
}
