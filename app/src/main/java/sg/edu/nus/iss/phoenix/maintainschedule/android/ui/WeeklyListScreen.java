package sg.edu.nus.iss.phoenix.maintainschedule.android.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;

public class WeeklyListScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_list);
        Resources res = getResources();
        String[] week_num = res.getStringArray(R.array.weekcount);

        for (int i = 0; i < week_num.length; i++) {

            int resID = getResources().getIdentifier(week_num[i], "id", getPackageName());
            Button button = ((Button) findViewById(resID));
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Button b = (Button) v;
                    String buttonText = b.getText().toString();
                    String[] result = buttonText.split(" ");
                    ControlFactory.getMaintainScheduleController().setWeek(Integer.parseInt(result[1]));
                    if ((getIntent().getAction() != null) && getIntent().getAction().equals("Copy")) {
                        Intent intent = new Intent(WeeklyListScreen.this, ScheduleScreen.class);
                        passDataIfCopy(intent);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(WeeklyListScreen.this, ScheduleListScreen.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        MenuItem itemView = menu.findItem(R.id.action_view);
        MenuItem itemCopy = menu.findItem(R.id.action_copy);
        itemView.setVisible(false);
        itemCopy.setVisible(false);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "View" menu option
            case R.id.action_populate:
                Intent intent = new Intent(this, PopulateScreen.class);
                startActivity(intent);
        }
        return true;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    public void passDataIfCopy(Intent toSendIntent) {
        Intent recievedIntent = getIntent();
        if ((recievedIntent.getAction() != null) && recievedIntent.getAction().equals("Copy")) {
            Log.d("TAG", recievedIntent.getStringExtra("rp_name"));
            toSendIntent.setAction("Copy");
            toSendIntent.putExtra("rp_name", recievedIntent.getStringExtra("rp_name"));
            toSendIntent.putExtra("presenter", recievedIntent.getStringExtra("presenter"));
            toSendIntent.putExtra("producer", recievedIntent.getStringExtra("producer"));
            toSendIntent.putExtra("duration", recievedIntent.getStringExtra("duration"));
        }
    }

}
