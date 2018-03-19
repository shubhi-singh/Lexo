package com.example.android.lexo;

/**
 * Created by coolio1 on 28/1/18.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DictionaryDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dictionary.db";
    private static final int DATABASE_VERSION = 1;
    public DictionaryDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
      final String SQL_CREATE_DICTIONARY_TABLE = "CREATE TABLE " +
              DictionaryContract.Dictionary.TABLE_NAME+" (" +
              DictionaryContract.Dictionary._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
              DictionaryContract.Dictionary.WORD + " TEXT, " +
              DictionaryContract.Dictionary.TIME_STAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
              DictionaryContract.Dictionary.PHONETIC_SPELLING + " TEXT, " +
              DictionaryContract.Dictionary.AUDIO_URL + " TEXT, " +
              DictionaryContract.Dictionary.DESCRIPTION + " TEXT, " +
              DictionaryContract.Dictionary.DEF1 + " TEXT, " +
              DictionaryContract.Dictionary.DEF2 + " TEXT, " +
              DictionaryContract.Dictionary.DEF3 + " TEXT, " +
              DictionaryContract.Dictionary.DEF4 + " TEXT, " +
              DictionaryContract.Dictionary.DEF5 + " TEXT, " +
              DictionaryContract.Dictionary.DEF6 + " TEXT" +
              "); ";
      db.execSQL(SQL_CREATE_DICTIONARY_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
     db.execSQL("DROP TABLE IF EXISTS " + DictionaryContract.Dictionary.TABLE_NAME);
     onCreate(db);
    }
}
