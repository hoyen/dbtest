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

    // 刪除參數指定編號的資料
    public boolean delete(int id){
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KEY_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLE_NAME, where , null) > 0;
    }

    // 讀取所有記事資料
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

    // 取得指定編號的資料物件
    public UserItem get(long id) {
        // 準備回傳結果用的物件
        UserItem item = null;
        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;
        // 執行查詢
        Cursor result = db.query(
                TABLE_NAME, null, where, null, null, null, null, null);

        // 如果有查詢結果
        if (result.moveToFirst()) {
            // 讀取包裝一筆資料的物件
            item = getRecord(result);
        }

        // 關閉Cursor物件
        result.close();
        // 回傳結果
        return item;
    }

    // 把Cursor目前的資料包裝為物件
    public UserItem getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        UserItem result = new UserItem("", 10);

        result.setId(cursor.getInt(0));
        result.setName(cursor.getString(1));
        result.setAge(cursor.getInt(2));

        // 回傳結果
        return result;
    }

    // 取得資料數量
    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }
}
