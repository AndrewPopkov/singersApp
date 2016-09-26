package popkovproject.appforyandex.database;

/**
 * Created by Андрей on 24.04.2016.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static popkovproject.appforyandex.database.SingerDbSchema.SingerTable.Cols.KEY_ALBUMS;
import static popkovproject.appforyandex.database.SingerDbSchema.SingerTable.Cols.KEY_BIG;
import static popkovproject.appforyandex.database.SingerDbSchema.SingerTable.Cols.KEY_DESCRIPTION;
import static popkovproject.appforyandex.database.SingerDbSchema.SingerTable.Cols.KEY_GENRES;
import static popkovproject.appforyandex.database.SingerDbSchema.SingerTable.Cols.KEY_ID;
import static popkovproject.appforyandex.database.SingerDbSchema.SingerTable.Cols.KEY_LINK;
import static popkovproject.appforyandex.database.SingerDbSchema.SingerTable.Cols.KEY_NAME;
import static popkovproject.appforyandex.database.SingerDbSchema.SingerTable.Cols.KEY_SMALL;
import static popkovproject.appforyandex.database.SingerDbSchema.SingerTable.Cols.KEY_TRACKS;
import static popkovproject.appforyandex.database.SingerDbSchema.SingerTable.TABLE_NAME;

public class DBHelper  extends SQLiteOpenHelper{//классдля работы с бд

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "singersDb";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(" + KEY_ID
                + " integer primary key," + KEY_NAME + " text," + KEY_TRACKS + " integer, " +
                KEY_ALBUMS + " integer,"+ KEY_GENRES + " text,"+ KEY_LINK + " text,"+
                KEY_DESCRIPTION + " text,"+ KEY_SMALL + " text,"+ KEY_BIG + " text"+ ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);

    }
}

