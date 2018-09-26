package sg.edu.nus.iss.phoenix.user.android.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.core.android.controller.ControlFactory;
import sg.edu.nus.iss.phoenix.user.entity.User;

/**
 * Created by rahul on 25-09-2018.
 */

public class UserAdapter extends ArrayAdapter<User> {
    private boolean mClickable = true;

    public UserAdapter(@NonNull Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    public UserAdapter(@NonNull Context context, ArrayList<User> users, boolean clickable) {
        super(context, 0, users);
        mClickable = clickable;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.listitem_user, parent, false);
        }
        //    Word currentWord = getItem(position);
        User currentUser = getItem(position);


        TextView userName = (TextView)listItemView.findViewById(R.id.username);
        userName.setText(currentUser.getName(), TextView.BufferType.NORMAL);
        userName.setKeyListener(null); // This disables editing.
        userName.setHint(currentUser.getId());
        setClickListener(position,userName);

        return listItemView;
    }

    public void setClickListener(final int position, final View view)
    {
        View.OnClickListener clickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("Clicked","Clicked");
                if (mClickable) {

                    User user = new User();
                    user.setId(String.valueOf(((TextView) view).getHint()));
                    ControlFactory.getUserController().startUseCaseWithSelectedUser(user);
                }
            }
        };
        view.setOnClickListener(clickListener);

    }
}
