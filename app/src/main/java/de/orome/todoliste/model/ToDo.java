package de.orome.todoliste.model;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Reinhard Herrmann on 31.01.18.
 */

public class ToDo implements Serializable {

    private long id;
    private String   nme_Todo;
    private Calendar dueDate;

    // Konstruktoren
    public ToDo(String nme_Todo) {
        this(nme_Todo,null);
    }

    public ToDo(String nme_Todo, Calendar dueDate) {
        this.nme_Todo = nme_Todo;
        this.dueDate = dueDate;
    }

    // Getter und Setter
    public String getNme_Todo() {
        return nme_Todo;
    }

    public void setNme_Todo(String nme_Todo) {
        this.nme_Todo = nme_Todo;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
