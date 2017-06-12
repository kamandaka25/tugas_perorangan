package id.sch.smktelkom_mlg.privateassignment.xirpl236.individu;

/**
 * Created by Kamandaka on 6/12/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import id.sch.smktelkom_mlg.privateassignment.xirpl236.individu.Noteview;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;public

class DatabaseAccess {
    private SQLiteDatabase database;
    private DatabaseOpenHelper openHelper;
    private static volatile DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static synchronized DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public void save(Noteview noteview) {
        ContentValues values = new ContentValues();
        values.put("date", noteview.getTime());
        values.put("memo", noteview.getText());
        database.insert(DatabaseOpenHelper.TABLE, null, values);
    }

    public void update(Noteview noteview) {
        ContentValues values = new ContentValues();
        values.put("date", new Date().getTime());
        values.put("memo", noteview.getText());
        String date = Long.toString(noteview.getTime());
        database.update(DatabaseOpenHelper.TABLE, values, "date = ?", new String[]{date});
    }

    public void delete(Noteview noteview) {
        String date = Long.toString(noteview.getTime());
        database.delete(DatabaseOpenHelper.TABLE, "date = ?", new String[]{date});
    }

    public List getAllMemos() {
        List memos = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * From memo ORDER BY date DESC", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            long time = cursor.getLong(0);
            String text = cursor.getString(1);
            memos.add(new Noteview(time, text));
            cursor.moveToNext();
        }
        cursor.close();
        return memos;
    }
}