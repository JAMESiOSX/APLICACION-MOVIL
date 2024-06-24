package android.studio.capystorecomputer.ui.evaluacion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "evaluaciones.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_EVALUACIONES = "evaluaciones";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_PREGUNTA1 = "pregunta1";
    public static final String COLUMN_PREGUNTA2 = "pregunta2";
    public static final String COLUMN_PREGUNTA3 = "pregunta3";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_EVALUACIONES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " INTEGER, " +
                    COLUMN_PREGUNTA1 + " INTEGER, " +
                    COLUMN_PREGUNTA2 + " INTEGER, " +
                    COLUMN_PREGUNTA3 + " INTEGER);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVALUACIONES);
        onCreate(db);
    }
}
