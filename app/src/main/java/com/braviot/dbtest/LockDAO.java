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

    // 刪除參數指定編號的資料
    public boolean delete(int id){
        // 設定條件為編號，格式為「欄位名稱=資料」
        String where = KEY_ID + "=" + id;
        // 刪除指定編號資料並回傳刪除是否成功
        return db.delete(TABLEAAA_NAME, where , null) > 0;
    }

    // 讀取所有記事資料
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

    // 取得指定編號的資料物件
    public LockItem get(long id) {
        // 準備回傳結果用的物件
        LockItem item = null;
        // 使用編號為查詢條件
        String where = KEY_ID + "=" + id;
        // 執行查詢
        Cursor result = db.query(
                TABLEAAA_NAME, null, where, null, null, null, null, null);

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
    public LockItem getRecord(Cursor cursor) {
        // 準備回傳結果用的物件
        LockItem result = new LockItem("");

        result.setId(cursor.getInt(0));
        result.setLockName(cursor.getString(1));
        result.setMasterCode(cursor.getInt(2));
        result.setLockID(cursor.getInt(3));

        // 回傳結果
        return result;
    }

    // 取得資料數量
    public int getCount() {
        int result = 0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLEAAA_NAME, null);

        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }

        return result;
    }
}
