package sg.edu.nus.iss.phoenix.maintainschedule.android.ui;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.view.View;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;


public class AnnualListScreen extends AppCompatActivity  implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_list);
        Resources res = getResources();
        String[] year = res.getStringArray(R.array.year);
        Log.v("Length",String.valueOf(year.length));
        for (int i = 0; i < year.length; i++){
            int resID = getResources().getIdentifier(year[i], "id", getPackageName());
            Button button=((Button) findViewById(resID));
            button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Button b = (Button) v;
                String buttonText = b.getText().toString();
                int yearInt = Integer.parseInt(buttonText);
                ControlFactory.getMaintainScheduleController().setYear(yearInt);
                Intent intent = new Intent(AnnualListScreen.this, WeeklyListScreen.class);
                startActivity(intent);
            }
        });
    }

}

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {

    }
}
