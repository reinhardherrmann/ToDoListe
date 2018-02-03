package de.orome.todoliste;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import de.orome.todoliste.model.ToDo;

public class ToDoDetailActivity extends AppCompatActivity {
    public static final String TODOKEY = "TODO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_detail);

        ToDo todo = (ToDo) getIntent().getSerializableExtra(TODOKEY);
        Log.w("todo id",String.valueOf(todo.getId()));
        Log.w("todo Name",todo.getNme_Todo());
    }
}
