package sg.edu.nus.iss.phoenix.maintainschedule.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import sg.edu.nus.iss.phoenix.maintainschedule.android.controller.MaintainScheduleController;
import sg.edu.nus.iss.phoenix.maintainschedule.entity.ProgramSlot;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_MAINTAIN_SCHEDULE;

public class DeleteScheduleDelegate extends AsyncTask<ProgramSlot,Void,Boolean> {

    // Tag for logging
    private static final String TAG = DeleteScheduleDelegate.class.getName();

    private final MaintainScheduleController maintainScheduleController;

    public DeleteScheduleDelegate(MaintainScheduleController maintainScheduleController) {
        this.maintainScheduleController = maintainScheduleController;
    }


/*
    @Override
    protected Boolean doInBackground(String... params) {
        // Encode the name of radio program in case of the presence of special characters.
        String name = null;
        try {
            name = URLEncoder.encode(params[0], "UTF-8");
        } catch (UnsupportedEncodingException e) {
            Log.v(TAG, e.getMessage());
            return new Boolean(false);
        }
        Uri builtUri = Uri.parse(PRMS_BASE_URL_MAINTAIN_SCHEDULE).buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri,"delete").buildUpon().build();
        builtUri = Uri.withAppendedPath(builtUri, name).buildUpon().build();
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
        return new Boolean(success);
    }
*/

        @Override
        protected Boolean doInBackground(ProgramSlot... params) {

            Uri builtUri = Uri.parse(PRMS_BASE_URL_MAINTAIN_SCHEDULE).buildUpon().build();
            builtUri = Uri.withAppendedPath(builtUri,"delete").buildUpon().build();
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
                json.put("programName", params[0].getRadioProgramName());
                json.put("producer", params[0].getProducerName());
                json.put("presenter", params[0].getPresenterName());
                json.put("duration",params[0].getDuration());
                json.put("dateOfProgram",params[0].getDate());
                json.put("startTime",params[0].getStartTime());
                json.put("assignedBy",params[0].getAssignedBy());
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
                dos.write(256);
                Log.v(TAG, "Http DELETE response " + httpURLConnection.getResponseCode());
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
        maintainScheduleController.scheduleDeleted(result.booleanValue());
    }

}
