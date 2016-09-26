package popkovproject.appforyandex.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.widget.Toast;
import java.util.ArrayList;
import popkovproject.appforyandex.Singer;
import popkovproject.appforyandex.database.SingerDbSchema.SingerTable;


/**
 * Created by Андрей on 08.06.2016.
 */
public class SingerDataBaseAdapter extends DBaseAdapter<Singer> {
    private Context mContext;
    public SingerDataBaseAdapter(Context context) {
        super();
        dbHelper=new DBHelper(context);
        this.mContext =context;
    }
    //используется для записи и обновления строки с помощью ContentValues
    protected   ContentValues getContentValues(Singer singer) {
        ContentValues values = new ContentValues();
        values.put(SingerTable.Cols.KEY_ID, singer.getId());
        values.put(SingerTable.Cols.KEY_NAME, singer.getName());
        values.put(SingerTable.Cols.KEY_TRACKS, singer.getTracks());
        values.put(SingerTable.Cols.KEY_ALBUMS, singer.getAlbums());
        values.put(SingerTable.Cols.KEY_GENRES, singer.getGenres());
        values.put(SingerTable.Cols.KEY_LINK, singer.getLink());
        values.put(SingerTable.Cols.KEY_DESCRIPTION, singer.getDescription());
        values.put(SingerTable.Cols.KEY_SMALL, singer.getSmall());
        values.put(SingerTable.Cols.KEY_BIG, singer.getBig());
        return values;
    }
    //используется для получения строки из Cursor
    protected Singer getRowFromCursor() {
        Singer singer = new Singer();
        singer.setId(userCursor.getInt(userCursor.getColumnIndex(SingerTable.Cols.KEY_ID)));
        singer.setName(userCursor.getString(userCursor.getColumnIndex(SingerTable.Cols.KEY_NAME)));
        singer.setTracks(userCursor.getInt(userCursor.getColumnIndex(SingerTable.Cols.KEY_TRACKS)));
        singer.setAlbums(userCursor.getInt(userCursor.getColumnIndex(SingerTable.Cols.KEY_ALBUMS)));
        singer.setGenres(userCursor.getString(userCursor.getColumnIndex(SingerTable.Cols.KEY_GENRES)));
        singer.setLink(userCursor.getString(userCursor.getColumnIndex(SingerTable.Cols.KEY_LINK)));
        singer.setDescription(userCursor.getString(userCursor.getColumnIndex(SingerTable.Cols.KEY_DESCRIPTION)));
        singer.setSmall(userCursor.getString(userCursor.getColumnIndex(SingerTable.Cols.KEY_SMALL)));
        singer.setBig(userCursor.getString(userCursor.getColumnIndex(SingerTable.Cols.KEY_BIG)));
        return singer;
    }
    //обертка над Cursor
    private void querySingers(String whereClause, String[] whereArgs) {
        userCursor = database.query(
                SingerTable.TABLE_NAME,
                null, // Columns - null выбирает все столбцы
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
    }
    //добавление к бд просмотреного певца
    public void insertRow( Singer singer) {
        // открываем подключение
        database = dbHelper.getWritableDatabase();
        //получаем данные из бд и проверяем нет ли такого ид
        try {

            ContentValues contentValues = getContentValues(singer);
            database.insert(SingerTable.TABLE_NAME, null,contentValues);
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        finally {
            database.close();
        }
    }

    // обновление записи
    public  void updateRow(Singer singer) {
    // открываем подключение
    database = dbHelper.getWritableDatabase();
    //получаем данные из бд и проверяем нет ли такого ид
    try {
        ContentValues contentValues = getContentValues(singer);
        database.update(SingerTable.TABLE_NAME, contentValues,
                SingerTable.Cols.KEY_ID + " = ?",
                new String[]{String.valueOf(singer.getId())});
    } catch (SQLException e) {
        e.printStackTrace();
        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
    }
    finally {
        database.close();
    }

}
    //удаление записи
    public void deleteRow(Singer singer) {
        // открываем подключение
        database = dbHelper.getWritableDatabase();
        //получаем данные из бд и проверяем нет ли такого ид
        try {
            ContentValues contentValues = getContentValues(singer);
            database.delete(SingerTable.TABLE_NAME,
                    SingerTable.Cols.KEY_ID + " = ?",
                    new String[]{String.valueOf(singer.getId())});

        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        finally {
            database.close();
        }
    }

    public ArrayList<Singer> getAllRow() {
        ArrayList<Singer> rows=new ArrayList<>();
        try {
            // открываем подключение
            database = dbHelper.getReadableDatabase();
            //получаем данные из бд и проверяем нет ли такого ид
            userCursor = database.rawQuery("select * from " + SingerTable.TABLE_NAME, null);//с помощью rawQuery
            if (userCursor.moveToFirst()) {
                do {
                    rows.add(getRowFromCursor());
                } while (userCursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        finally {
            database.close();
            userCursor.close();
        }
        return  rows;
    }

    public Singer getRowToId(int id) {
        Singer row = new Singer();
        try {
            // открываем подключение
            database = dbHelper.getReadableDatabase();
            //получаем данные из бд и проверяем нет ли такого ид
            querySingers(SingerTable.Cols.KEY_ID,new String[]{ String.valueOf(id)});
            if (userCursor.moveToFirst()) {
                do {
                    row=getRowFromCursor();
                } while (userCursor.moveToNext());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        finally {
            database.close();
            userCursor.close();
        }
        return  row;
    }

    public void  closeСonnection(){

        dbHelper.close();
    }

}


