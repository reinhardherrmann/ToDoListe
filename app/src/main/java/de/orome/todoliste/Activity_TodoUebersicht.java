package de.orome.todoliste;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.orome.todoliste.adapter.listview.ToDoOverviewListAdapter;
import de.orome.todoliste.model.ToDo;

public class Activity_TodoUebersicht extends AppCompatActivity {

    ListView listToDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_uebersicht);

        listToDo=findViewById(R.id.list_ToDo);

        // Datenquelle als String zum Befüllen der ListView anlegen
        List<ToDo> datasource = new ArrayList<>();
        datasource.add(new ToDo("Einkaufen"));
        datasource.add(new ToDo("Kerstin besuchen", Calendar.getInstance()));
        // Adapter für Listview erzeugen und Werte übergeben
        listToDo.setAdapter(new ToDoOverviewListAdapter(this,datasource));

        listToDo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Object element = adapterView.getAdapter().getItem(position);
                //Log.e("Clicked on: ", element.toString());
            }
        });
    }

}
