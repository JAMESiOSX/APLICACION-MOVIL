package android.studio.capystorecomputer.ui.evaluacion;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class EvaluacionDAO {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public EvaluacionDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long insertarEvaluacion(int userId, int pregunta1, int pregunta2, int pregunta3) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_USER_ID, userId);
        values.put(DatabaseHelper.COLUMN_PREGUNTA1, pregunta1);
        values.put(DatabaseHelper.COLUMN_PREGUNTA2, pregunta2);
        values.put(DatabaseHelper.COLUMN_PREGUNTA3, pregunta3);
        return database.insert(DatabaseHelper.TABLE_EVALUACIONES, null, values);
    }

    public void cerrar() {
        dbHelper.close();
    }
}
