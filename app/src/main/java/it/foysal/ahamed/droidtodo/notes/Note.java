package it.foysal.ahamed.droidtodo.notes;

import com.google.gson.annotations.Expose;

/**
 * Created by foysal on 02/01/15.
 */
public class Note {
    @Expose
    public Integer id;

    @Expose
    public String title;

    @Expose
    public String note;
}
