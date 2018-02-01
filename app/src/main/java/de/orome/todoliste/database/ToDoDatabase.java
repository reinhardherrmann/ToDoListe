package de.orome.todoliste.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.orome.todoliste.model.ToDo;

/**
 * Created by reinhard on 31.01.18.
 */

public class ToDoDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "ToDo.db";
    private static final int DB_VERSION = 1;
    // Konstanten für Tabelle ToDo ...
    private static final String TABLE_NAME_TODO = "todos";
    private static final String TBL_TODO_COLUMN_ID = "todo_id";
    private static final String TBL_TODO_COLUMN_NAME = "todo_name";
    private static final String TBL_TODO_COLUMN_DUEDATE = "todo_duedate";
    private static final String CREATE_TABLE_TODO = "CREATE TABLE " + TABLE_NAME_TODO
            + " ("
            + TBL_TODO_COLUMN_ID + " INTEGER AUTOINCREMENT PRIMARY KEY, "
            + TBL_TODO_COLUMN_NAME + " TEXT NOT NULL, "
            + TBL_TODO_COLUMN_DUEDATE + " INTEGER DEFAULT NULL"
            + ")";
    private static final String DROP_TABLE_TODO = "DROP TABLE IF EXISTS " + TABLE_NAME_TODO;
    // -------------- Ende Konstanten Table ToDo

    public ToDoDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabelle ToDos erstellen ...
        db.execSQL(CREATE_TABLE_TODO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Tabellen löschen
        db.execSQL(DROP_TABLE_TODO);
        // Datenbank ggf neu aufbauen
        onCreate(db);

    }


    // Funktionen zum Handeln der Daten aus der APP

    public ToDo createToDo(ToDo toDo){
        // Referenz auf Datenbank zum Schreiben holen
        SQLiteDatabase database = this.getWritableDatabase();
        // Werte erstellen, die in die db zu schreiben sind
        ContentValues values = new ContentValues();
        values.put(TBL_TODO_COLUMN_NAME,toDo.getNme_Todo());
        // mit inline If prüfen, ob DueDate null ist ...
        values.put(TBL_TODO_COLUMN_DUEDATE,toDo.getDueDate()==null ? null:toDo.getDueDate().getTimeInMillis()/1000);
        // 'ID nach dem insert ermitteln
        long newID = database.insert(TABLE_NAME_TODO,null,values);
        // DAtenbank schließen
        database.close();
        // ID mit den neuen Daten als ToDo zurückgeben
        return readToDo(newID);
    }

    public ToDo readToDo(final long id){

        ToDo todo = null;
        // Referenz auf Datenbank erzeugen
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(TABLE_NAME_TODO,new String[]{TBL_TODO_COLUMN_ID,
                TBL_TODO_COLUMN_NAME,TBL_TODO_COLUMN_DUEDATE},TBL_TODO_COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)},null,null,null);
        // Cursor auswerten, wenn weder null noch keine Werte erhalten
        if(cursor!=null && cursor.getCount()>0){
            cursor.moveToFirst();
            ToDo todo = new ToDo(cursor.getString(cursor.getColumnIndex(TBL_TODO_COLUMN_NAME)));
            todo.setId(cursor.getLong(cursor.getColumnIndex(TBL_TODO_COLUMN_ID)));

            Calendar calendar = null;
            if(!cursor.isNull(cursor.getColumnIndex(TBL_TODO_COLUMN_DUEDATE))){
                calendar = Calendar.getInstance();
                calendar.setTimeInMillis(cursor.getColumnIndex(TBL_TODO_COLUMN_DUEDATE)*1000);}
        }

        database.close();
        return todo;
    }

    public List<ToDo> readAllToDo(){
        List<ToDo> todoListe = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * from "+ TABLE_NAME_TODO,null);
        // Wenn man sich zeilenweise durch den Cursor bewegen kann, dann cursor mit do while durchlaufen
        if(cursor.moveToFirst()){
            do {
                ToDo todo = readToDo(cursor.getLong(cursor.getColumnIndex(TBL_TODO_COLUMN_ID)));
                if(todo != null){
                    todoListe.add(todo);
                }
            }while(cursor.moveToNext());
        }
        database.close();
        return todoListe;

    }

    public ToDo updateToDo(ToDo toDo){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TBL_TODO_COLUMN_NAME,toDo.getNme_Todo());
        values.put(TBL_TODO_COLUMN_DUEDATE,toDo.getDueDate() == null ? null :toDo.getDueDate().getTimeInMillis()/1000);
        database.update(TABLE_NAME_TODO,values,TBL_TODO_COLUMN_ID + " = ?", new String[]{String.valueOf(toDo.getId())});

        database.close();
        return this.readToDo(toDo.getId());
    }

    public void deleteToDo(ToDo toDo){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME_TODO,TBL_TODO_COLUMN_ID + " = ?", new String[]{String.valueOf(toDo.getId())});
        database.close();

    }

    public void deleteAllToDos(){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL("DELETE FROM " + TABLE_NAME_TODO);

        database.close();

    }

}
