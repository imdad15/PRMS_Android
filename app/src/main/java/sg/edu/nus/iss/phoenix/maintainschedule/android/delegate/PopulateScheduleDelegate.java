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

        String path = "populate"+"?"+"fromYear="+params[0]+"&fromWeek="+params[1]+"&toYear="+params[2]+"&toWeek="+params[3];
        Uri builtUri = Uri.parse(PRMS_BASE_URL_MAINTAIN_SCHEDULE).buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri, path).buildUpon().build();
        Log.v(TAG, builtUri.toString());
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.v(TAG, e.getMessage());
            return new Boolean(false);
        }


        boolean success = false;
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setInstanceFollowRedirects(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=utf8");
            httpURLConnection.setDoOutput(true);

            Log.v(TAG, "Http POST response " + httpURLConnection.getResponseCode());
            success = true;
        } catch (IOException exception) {
            Log.v(TAG, exception.getMessage());
        } finally {
                if (httpURLConnection != null) httpURLConnection.disconnect();
        }
        return new Boolean(success);
    }

}
