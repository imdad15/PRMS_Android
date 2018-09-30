package sg.edu.nus.iss.phoenix.maintainschedule.android.delegate;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sg.edu.nus.iss.phoenix.maintainschedule.android.controller.MaintainScheduleController;
import sg.edu.nus.iss.phoenix.maintainschedule.android.controller.ReviewSelectMaintainScheduleController;
import sg.edu.nus.iss.phoenix.maintainschedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.radioprogram.android.controller.ProgramController;
import sg.edu.nus.iss.phoenix.radioprogram.android.controller.ReviewSelectProgramController;
import sg.edu.nus.iss.phoenix.radioprogram.android.delegate.RetrieveProgramsDelegate;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_MAINTAIN_SCHEDULE;
import static sg.edu.nus.iss.phoenix.core.android.delegate.DelegateHelper.PRMS_BASE_URL_RADIO_PROGRAM;

public class RetrieveScheduleDelegate extends  AsyncTask<String,Void,String> {

    // Tag for logging
    private static final String TAG = RetrieveProgramsDelegate.class.getName();

    private MaintainScheduleController maintainScheduleController = null;
    private ReviewSelectMaintainScheduleController reviewSelectMaintainScheduleController = null;

   public RetrieveScheduleDelegate(MaintainScheduleController maintainScheduleController) {
        this.reviewSelectMaintainScheduleController = null;
        this.maintainScheduleController = maintainScheduleController;

    }

    public RetrieveScheduleDelegate(ReviewSelectMaintainScheduleController reviewSelectMaintainScheduleController) {
        this.maintainScheduleController = null;
        this.reviewSelectMaintainScheduleController = reviewSelectMaintainScheduleController;
    }


    @Override
    protected String doInBackground(String... params) {
       String path = "list"+params[0]+params[1];
        Uri builtUri1 = Uri.parse(PRMS_BASE_URL_MAINTAIN_SCHEDULE).buildUpon().build();
        Uri builtUri = Uri.withAppendedPath(builtUri1, path).buildUpon().build();
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
        List<ProgramSlot> programSlots = new ArrayList<>();

        if (result != null && !result.equals("")) {
            try {
                JSONObject reader = new JSONObject(result);
                JSONArray psArray = reader.getJSONArray("psList");

                for (int i = 0; i < psArray.length(); i++) {
                    JSONObject psJson = psArray.getJSONObject(i);
                    String radioProgramName = psJson.getString("radioProgramName");
                    String radioProgramDuration = psJson.getString("radioProgramDuration");
                    String radioProgramPresenter = psJson.getString("radioProgramPresenter");
                    String radioProgramProducer = psJson.getString("radioProgramProducer");
                    String date = psJson.getString("date");
                    String startTime = psJson.getString("startTime");
                    String assignedBy = psJson.getString("assignedBy");


                    programSlots.add(new ProgramSlot( radioProgramName, radioProgramPresenter, radioProgramProducer,
                             radioProgramDuration, date, startTime, assignedBy));
                }
            } catch (JSONException e) {
                Log.v(TAG, e.getMessage());
            }
        } else {
            Log.v(TAG, "JSON response error.");
        }

        if (maintainScheduleController != null)
            maintainScheduleController.schedulesRetrieved(programSlots);
        else if (reviewSelectMaintainScheduleController != null)
            reviewSelectMaintainScheduleController.schedulesRetrieved(programSlots);
    }

}
