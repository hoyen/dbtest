package com.braviot.dbtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HY001 on 2015/6/15.
 */
public class UserDAO {

    private SQLiteDatabase db;
    public static final int VERSION = 1;

    public static final String NAME_COLUMN = "name";
    public static final String AGE_COLUMN = "age";
    public static  final String TABLE_NAME = "ddd";
    public static final String KEY_ID = "_id";

    public UserDAO(Context context){
        db = MyDBHelper.getDatabase(context);
    }

    public void close(){
        db.close();
    }

    public UserItem insert(UserItem uu){
        ContentValues cv = new ContentValues();

        cv.put(NAME_COLUMN, uu.getName());
        cv.put(AGE_COLUMN, uu.getAge());

        long id = db.insert(TABLE_NAME, null, cv);

        uu.setId((int) id);

        return uu;
    }

    public boolean update(UserItem uu){
        ContentValues cv = new ContentValues();

        cv.put(NAME_COLUMN, uu.getName());
        cv.put(AGE_COLUMN, uu.getAge());

        String where = KEY_ID + "=" + uu.getId();
        long id = db.insert(TABLE_NAME, null, cv);

        uu.setId((int)id);

        return db.update(TABLE_NAME, cv, where, null) > 0;
    }

    // �R���Ѽƫ��w�s�������
    public boolean delete(int id){
        // �]�w���󬰽s���A�榡���u���W��=��ơv
        String where = KEY_ID + "=" + id;
        // �R�����w�s����ƨæ^�ǧR���O�_���\
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    // Ū���Ҧ��O�Ƹ��
    public List<UserItem> getAll() {
        List<UserItem> result = new ArrayList<>();
        Cursor cursor = db.query(
                TABLE_NAME, null, null, null, null, null, null, null);

        while (cursor.moveToNext()) {
            result.add(getRecord(cursor));
        }

        cursor.close();
        return result;
    }

    // ���o���w�s������ƪ���
    public UserItem get(long id) {
        // �ǳƦ^�ǵ��G�Ϊ�����
        UserItem item = null;
        // �ϥνs�����d�߱���
        String where = KEY_ID + "=" + id;
        // ����d��
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

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
    public UserItem getRecord(Cursor cursor) {
        // �ǳƦ^�ǵ��G�Ϊ�����
        UserItem result = new UserItem("", 10);

        result.setId(cursor.getInt(0));
        result.setName(cursor.getString(1));
        result.setAge(cursor.getInt(2));

        // �^�ǵ��G
        return result;
    }

    // ���o��Ƽƶq
    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }
}
