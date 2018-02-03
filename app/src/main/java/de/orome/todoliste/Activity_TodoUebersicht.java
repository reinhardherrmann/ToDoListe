package de.orome.todoliste;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import de.orome.todoliste.adapter.listview.ToDoOverviewListAdapter;
import de.orome.todoliste.database.ToDoDatabase;
import de.orome.todoliste.model.ToDo;

public class Activity_TodoUebersicht extends AppCompatActivity {

    private ListView listToDo;
    //private List<ToDo> datasource;
    private ToDoOverviewListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_uebersicht);

        this.listToDo=findViewById(R.id.list_ToDo);

        Button btnCreate = (Button)findViewById(R.id.btn_create);
        Button btnDeleteAll = (Button)findViewById(R.id.btn_delete_all);
        Button btnDeleteFirst = (Button)findViewById(R.id.btn_delete_first);
        Button btnUpdateFirst = (Button)findViewById(R.id.btn_update_first);

        // Adapter für Listview erzeugen und Werte übergeben
        this.adapter = new ToDoOverviewListAdapter(this,ToDoDatabase.getInstance(this).getAllToDosAsCursor());
        this.listToDo.setAdapter(adapter);

        this.listToDo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Object element = adapterView.getAdapter().getItem(position);
                Log.e("Clicked on: ", element.toString());
            }
        });



        // OnClicklistener für die Buttons definieren
        if (btnCreate !=null){
            btnCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // zwei vorgefertigte ToDos einfügen
                    ToDoDatabase database = ToDoDatabase.getInstance(Activity_TodoUebersicht.this);
                    database.createToDo(new ToDo("Einkaufen"));
                    //database.createToDo(new ToDo("Schrottplatz besuchen", Calendar.getInstance()));
                    database.createToDo(new ToDo("Schrottplatz besuchen",Calendar.getInstance()));
                    // Liste aktualisieren
                    refreshListView();
                }
            });
        }

        if (btnUpdateFirst !=null){
            btnUpdateFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToDoDatabase database = ToDoDatabase.getInstance(Activity_TodoUebersicht.this);
                    ToDo firstToDo = database.getFirstToDo();
                    if (firstToDo != null){
                        Random r = new Random();
                        firstToDo.setNme_Todo(String.valueOf(r.nextInt()));
                        database.updateToDo(firstToDo);
                        refreshListView();
                    }

                }
            });
        }

        if (btnDeleteAll !=null){
            btnDeleteAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToDoDatabase database = ToDoDatabase.getInstance(Activity_TodoUebersicht.this);
                    database.deleteAllToDos();
                    refreshListView();
                }
            });
        }

        if (btnDeleteFirst !=null){
            btnDeleteFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToDoDatabase database = ToDoDatabase.getInstance(Activity_TodoUebersicht.this);
                    ToDo firstToDo = database.getFirstToDo();
                    if(firstToDo != null){
                        database.deleteToDo(firstToDo);
                        refreshListView();
                    }
                }
            });
        }


    }

    private void refreshListView(){
        adapter.changeCursor(ToDoDatabase.getInstance(this).getAllToDosAsCursor());
    }



}
