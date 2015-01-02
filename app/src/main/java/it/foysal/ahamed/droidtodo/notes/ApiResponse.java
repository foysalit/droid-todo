package it.foysal.ahamed.droidtodo.notes;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by foysal on 02/01/15.
 */
public class ApiResponse {
    @Expose
    public Boolean error;

    @Expose
    public List<Note> data;
}
