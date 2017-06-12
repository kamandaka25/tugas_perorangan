package id.sch.smktelkom_mlg.privateassignment.xirpl236.individu;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kamandaka on 6/12/2017.
 */

public class DBHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mynote";
    private static final String TABLE_NOTE = "note";
    private static final String KEY_ID = "id";
    private static final String KEY_MONEY = "money";
    private static final String KEY_HAL = "hal";
    private static final String KEY_DATE = "date";

    public DBHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_CONTACTS_TABLE = "CREATE TABLE" + TABLE_NOTE + "(" +
                KEY_ID + "INTEGER PRIMARY KEY," +
                KEY_MONEY + "INTEGER," +
                KEY_HAL + "TEXT," +
                KEY_DATE + "TEXT," + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXIST" + TABLE_NOTE);
        onCreate(db);
    }
    public void addNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, note.getId());
        values.put(KEY_MONEY, note.getMoney());
        values.put(KEY_HAL, note.getHal());
        values.put(KEY_DATE, note.getDate());
        db.insert(TABLE_NOTE, null, values);
        db.close();
    }
    public Note getNote(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NOTE, new String[]{KEY_ID, KEY_MONEY, KEY_HAL, KEY_DATE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Note contact = new Note(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),Integer.parseInt(cursor.getString(2)),cursor.getString(3));
        return contact;
    }
    public List<Note> getAllNote(){
        List<Note> noteList = new ArrayList<Note>();
        // Select QUery
        String selectQuery = "SELECT * FROM" + TABLE_NOTE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do{
                Note note = new Note();
                note.setId(Integer.parseInt(cursor.getString(0)));
                note.setHal(cursor.getString(1));
                note.setMoney(Integer.parseInt(cursor.getString(2)));
                note.setDate(cursor.getString(3));
                //add contact list
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        return noteList;
    }
    public int getNoteCount(){
        String countQuery = "SELECT * FROM" + TABLE_NOTE;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
    //update
    public int updateNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MONEY, note.getMoney());
        values.put(KEY_HAL, note.getHal());
        values.put(KEY_DATE, note.getDate());
        //updating now
        return db.update(TABLE_NOTE, values, KEY_ID + " = ? ",
                new String[]{String.valueOf(note.getId())});
    }
    //delete
    public void deleteNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTE, KEY_ID + " = ? ",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }
}
