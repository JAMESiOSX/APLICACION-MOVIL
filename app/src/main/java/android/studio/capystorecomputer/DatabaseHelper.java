package android.studio.capystorecomputer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DigitalStoreDB";
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Creación de tablas necesarias en la base de datos
        db.execSQL("CREATE TABLE IF NOT EXISTS pedidos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "fecha TEXT," +
                "detalle TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Actualización de la base de datos (en este caso, se borran las tablas existentes y se vuelven a crear)
        db.execSQL("DROP TABLE IF EXISTS pedidos");
        onCreate(db);
    }

    // Otros métodos para manejar la base de datos según tus necesidades
}
