package com.braviot.dbtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HY001 on 2015/6/16.
 */
public class LockDAO {

    private SQLiteDatabase db;
    public static final int VERSION = 1;
    public static final String LOCK_NAME_COLUMN = "lockname";
    public static final String LOCK_ID_COLUMN = "lockid";
    public static final String LOCK_MANAGER = "manager";
    public static final String KEY_ID = "_id";
    public static  final String TABLEAAA_NAME = "aaa";


    public LockDAO(Context context){
        db = MyDBHelper.getDatabase(context);
    }

    public void close(){
        db.close();
    }

    public LockItem insert(LockItem llo){
        ContentValues cv = new ContentValues();

        cv.put(LOCK_NAME_COLUMN, llo.getLockName());
        cv.put(LOCK_ID_COLUMN, llo.getLockID());
        cv.put(LOCK_MANAGER, llo.getMasterCode());

        long id = db.insert(TABLEAAA_NAME, null, cv);

        llo.setId((int) id);

        return llo;
    }

    public boolean update(LockItem llo){
        ContentValues cv = new ContentValues();

        cv.put(LOCK_NAME_COLUMN, llo.getLockName());
        cv.put(LOCK_ID_COLUMN, llo.getLockID());
        cv.put(LOCK_MANAGER, llo.getMasterCode());

        String where = KEY_ID + "=" + llo.getId();
        long id = db.insert(TABLEAAA_NAME, null, cv);

        llo.setId((int)id);

        return db.update(TABLEAAA_NAME, cv, where, null) > 0;
    }

    // �R���Ѽƫ��w�s�������
    public boolean delete(int id){
        // �]�w���󬰽s���A�榡���u���W��=��ơv
        String where = KEY_ID + "=" + id;
        // �R�����w�s����ƨæ^�ǧR���O�_���\
        return db.delete(TABLEAAA_NAME, where , null) > 0;
    }

    // Ū���Ҧ��O�Ƹ��
    public List<LockItem> getAll() {
        List<LockItem> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLEAAA_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    // ���o���w�s������ƪ���
    public LockItem get(long id) {
        // �ǳƦ^�ǵ��G�Ϊ�����
        LockItem item = null;
        // �ϥνs�����d�߱���
        String where = KEY_ID + "=" + id;
        // ����d��
        Cursor result = db.query(
                TABLEAAA_NAME, null, where, null, null, null, null, null);

        // �p�G���d�ߵ��G
        if (result.moveToFirst()) {
            // Ū���]�ˤ@����ƪ�����
            item = getRecord(result);
        }

        // ����Cursor����
        result.close();
        // �^�ǵ��G
        return item;
    }

    // ��Cursor�ثe����ƥ]�ˬ�����
    public LockItem getRecord(Cursor cursor) {
        // �ǳƦ^�ǵ��G�Ϊ�����
        LockItem result = new LockItem("");

        result.setId(cursor.getInt(0));
        result.setLockName(cursor.getString(1));
        result.setMasterCode(cursor.getInt(2));
        result.setLockID(cursor.getInt(3));

        // �^�ǵ��G
        return result;
    }

    // ���o��Ƽƶq
    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLEAAA_NAME, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }
}
