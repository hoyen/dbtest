package com.braviot.dbtest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by HY001 on 2015/6/15.
 */
public class MyDBHelper extends SQLiteOpenHelper{

    public static  final String TABLEAAA_NAME = "aaa";

    public static final String KEY_ID = "_id";
    public static final String LOCK_NAME_COLUMN = "lockname";
    public static final String LOCK_ID_COLUMN = "lockid";
    public static final String LOCK_MANAGER = "manager";
    // �ϥΤW���ŧi���ܼƫإߪ�檺SQL���O
    public static final String CREATE_TABLE_AAA =
            "CREATE TABLE " + TABLEAAA_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    LOCK_NAME_COLUMN + " TEXT, " +
                    LOCK_MANAGER + " INTEGER, " +
                    LOCK_ID_COLUMN + " INTEGER)";

    //=========     The second table
    public static  final String TABLE_NAME = "ddd";

    public static final String NAME_COLUMN = "name";
    public static final String AGE_COLUMN = "age";
    // �ϥΤW���ŧi���ܼƫإߪ�檺SQL���O
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME_COLUMN + " TEXT, " +
                    AGE_COLUMN + " INTEGER)";

    // ��Ʈw�W��
    public static final String DATABASE_NAME = "dss.db";
    // ��Ʈw�����A��Ƶ��c���ܪ��ɭԭn���o�ӼƦr�A�q�`�O�[�@
    public static final int VERSION = 1;
    // ��Ʈw����A�T�w������ܼ�
    private static SQLiteDatabase database;

    // �غc�l�A�b�@�몺���γ����ݭn�ק�
    public MyDBHelper(Context context, String name, CursorFactory factory,
                      int version) {
        super(context, name, factory, version);
    }

    // �ݭn��Ʈw������I�s�o�Ӥ�k�A�o�Ӥ�k�b�@�몺���γ����ݭn�ק�
    public static SQLiteDatabase getDatabase(Context context) {
        if (database == null || !database.isOpen()) {
            database = new MyDBHelper(context, DATABASE_NAME,
                    null, VERSION).getWritableDatabase();
        }

        return database;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_AAA);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLEAAA_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}


