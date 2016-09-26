package popkovproject.appforyandex.database;

/**
 * Created by Андрей on 03.07.2016.
 */
public class SingerDbSchema {
    public static final class SingerTable {
        public static final String TABLE_NAME = "singers";
        public static final class Cols {
            public static final String KEY_ID = "_id";
            public static final String KEY_NAME = "name";
            public static final String KEY_TRACKS = "tracks";
            public static final String KEY_ALBUMS = "albums";
            public static final String KEY_GENRES = "genres";
            public static final String KEY_LINK = "link";
            public static final String KEY_DESCRIPTION = "description";
            public static final String KEY_SMALL = "small";
            public static final String KEY_BIG = "big";
        }
    }
}
