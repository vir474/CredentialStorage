package encrypto;

/**
 * Created by VIR474 on 8/16/14.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "VirEncrypto.db";
    public static final String TABLE_NAME = "credstorage";
    public static final String COLUMN_CREDID = "credid";
    public static final String COLUMN_CREDNAME = "credname";
    public static final String COLUMN_CREDUNAME = "creduname";
    public static final String COLUMN_CREDPW = "credpw";

    //private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CRED_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_CREDID + " INTEGER PRIMARY KEY," + COLUMN_CREDNAME + " TEXT,"
                + COLUMN_CREDUNAME + " TEXT," + COLUMN_CREDPW + " TEXT" + ")";
        db.execSQL(CREATE_CRED_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public void addCred(Creds cred) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CREDNAME, cred.getName());
        values.put(COLUMN_CREDUNAME, cred.getUserName());
        values.put(COLUMN_CREDPW, cred.getPassword());

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public Creds getCred(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_CREDID,
                COLUMN_CREDNAME, COLUMN_CREDUNAME, COLUMN_CREDPW }, COLUMN_CREDNAME + "=?",
                new String[] { String.valueOf(name) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Creds cred = new Creds(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        // return contact
        return cred;
    }

    // Getting All Creds
    public List<Creds> getAllCreds() {
        List<Creds> credList = new ArrayList<Creds>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Creds cred = new Creds();
                cred.setID(Integer.parseInt(cursor.getString(0)));
                cred.setName(cursor.getString(1));
                cred.setUserName(cursor.getString(2));
                cred.setPassword(cursor.getString(3));

                // Adding cred to list
                credList.add(cred);
            } while (cursor.moveToNext());
        }

        // return cred list
        return credList;
    }

    // Updating single cred
    public int updateCred(Creds cred) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CREDNAME, cred.getName());
        values.put(COLUMN_CREDUNAME, cred.getUserName());
        values.put(COLUMN_CREDPW, cred.getPassword());

        // updating row
        return db.update(TABLE_NAME, values, COLUMN_CREDID + " = ?",
                new String[] { String.valueOf(cred.getID()) });
    }

    // Deleting single cred
    public void deleteContact(Creds cred) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_CREDID + " = ?",
                new String[] { String.valueOf(cred.getID()) });
        db.close();
    }


    // Getting creds Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}