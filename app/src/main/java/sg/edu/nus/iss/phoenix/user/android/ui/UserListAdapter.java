package sg.edu.nus.iss.phoenix.user.android.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.user.entity.User;

public class UserListAdapter extends ArrayAdapter<User> {

    public ArrayList<User> mUserArrayList;

    public UserListAdapter(Context context, ArrayList<User> userArrayList){
        super(context, 0, userArrayList);
        mUserArrayList = userArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_user_list, parent, false);
        }

        User user = getItem(position);
        TextView userNameTextView = (TextView) listItemView.findViewById(R.id.list_item_user);
        userNameTextView.setText(user.getName(), TextView.BufferType.NORMAL);
        //userNameTextView.setKeyListener(null);
        return listItemView;
    }
}
