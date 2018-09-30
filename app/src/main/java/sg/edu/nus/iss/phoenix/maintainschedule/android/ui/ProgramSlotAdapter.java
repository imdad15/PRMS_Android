package sg.edu.nus.iss.phoenix.maintainschedule.android.ui;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.maintainschedule.entity.ProgramSlot;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;


public class ProgramSlotAdapter extends ArrayAdapter<ProgramSlot> {
    public ProgramSlotAdapter(@NonNull Context context, ArrayList<ProgramSlot> programSlots) {
        super(context, 0, programSlots);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_program_slot_view, parent, false);
        }
        //    Word currentWord = getItem(position);
        ProgramSlot currentPS = getItem(position);

        EditText radioPMName = (EditText)listItemView.findViewById(R.id.maintain_schedule_program_name_text_view);
        radioPMName.setText(currentPS.getRadioProgramName(), TextView.BufferType.NORMAL);
        radioPMName.setKeyListener(null); // This disables editing.

        EditText radioPMDuration = (EditText)listItemView.findViewById(R.id.maintain_schedule_program_duration_text_view);
        radioPMDuration.setText(currentPS.getDuration(), TextView.BufferType.NORMAL);
        radioPMDuration.setKeyListener(null);

        EditText radioPSPresenter = (EditText)listItemView.findViewById(R.id.maintain_schedule_program_presenter_text_view);
        radioPSPresenter.setText(currentPS.getPresenterName(), TextView.BufferType.NORMAL);
        radioPSPresenter.setKeyListener(null);

        EditText radioPSProducer = (EditText)listItemView.findViewById(R.id.maintain_schedule_program_producer_text_view);
        radioPSProducer.setText(currentPS.getProducerName(), TextView.BufferType.NORMAL);
        radioPSProducer.setKeyListener(null);

        EditText radioPSdate = (EditText)listItemView.findViewById(R.id.maintain_schedule_program_date_text_view);
        radioPSdate.setText(currentPS.getDate(), TextView.BufferType.NORMAL);
        radioPSdate.setKeyListener(null);

        EditText radioPSStartTime = (EditText)listItemView.findViewById(R.id.maintain_schedule_program_start_time_text_view);
        radioPSStartTime.setText(currentPS.getStartTime(), TextView.BufferType.NORMAL);
        radioPSStartTime.setKeyListener(null);

        EditText radioPSAssignedBy = (EditText)listItemView.findViewById(R.id.maintain_schedule_assigned_by_text_view);
        radioPSAssignedBy.setText(currentPS.getAssignedBy(), TextView.BufferType.NORMAL);
        radioPSAssignedBy.setKeyListener(null);


        return listItemView;
    }
}
