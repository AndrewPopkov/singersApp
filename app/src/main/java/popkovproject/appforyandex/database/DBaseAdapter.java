package popkovproject.appforyandex.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


/**
 * Created by Андрей on 03.07.2016.
 */
public abstract class DBaseAdapter<T> {

    protected SQLiteDatabase database;
    protected Cursor userCursor;
    protected SQLiteOpenHelper dbHelper;

    public DBaseAdapter() {

    }
    protected abstract ContentValues getContentValues(T t);
    protected abstract T getRowFromCursor();
    public abstract void insertRow( T t);
    public abstract void updateRow(T t);
    public abstract void deleteRow(T t);
    public abstract ArrayList<T> getAllRow();
    public abstract T getRowToId(int id);
    public abstract void closeСonnection();
}
