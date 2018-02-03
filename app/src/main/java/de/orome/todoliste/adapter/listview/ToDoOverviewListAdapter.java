package de.orome.todoliste.adapter.listview;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import de.orome.todoliste.R;
import de.orome.todoliste.database.ToDoDatabase;
import de.orome.todoliste.model.ToDo;

/**
 * Created by reinhard on 31.01.18.
 */

public class ToDoOverviewListAdapter extends CursorAdapter {   //extends ArrayAdapter<ToDo> {

    public ToDoOverviewListAdapter(Context context, Cursor cursor){
        super(context,cursor,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.todo_overview_listitem,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView)view.findViewById(R.id.txt_NmeToDo)).setText(cursor.getString(cursor.getColumnIndex(ToDoDatabase.TBL_TODO_COLUMN_NAME)));
        TextView dueDate = view.findViewById(R.id.txt_DueDate);
        if(cursor.isNull(cursor.getColumnIndex(ToDoDatabase.TBL_TODO_COLUMN_DUEDATE))){
            dueDate.setVisibility(View.GONE);
        }else{
            dueDate.setVisibility(View.VISIBLE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(cursor.getInt(cursor.getColumnIndex(ToDoDatabase.TBL_TODO_COLUMN_DUEDATE))*1000);
            dueDate.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        }
    }


    /** Code des Arrayadapters wird nicht mehr benötigt

        public ToDoOverviewListAdapter(final Context context, final List<ToDo> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        ToDo currentToDo = getItem(position);
        View view = convertView;
        if (view ==null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.todo_overview_listitem,parent,false);
        }
        ((TextView)view.findViewById(R.id.txt_NmeToDo)).setText(currentToDo.getNme_Todo());
        TextView dueDate = view.findViewById(R.id.txt_DueDate);
        if(currentToDo.getDueDate()==null){
            dueDate.setVisibility(View.GONE);
        }else{
            dueDate.setVisibility(View.VISIBLE);
            dueDate.setText(String.valueOf(currentToDo.getDueDate().get(Calendar.YEAR)));
        }
        return view;
    }*/
}
