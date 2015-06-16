package com.braviot.dbtest;

/**
 * Created by HY001 on 2015/6/16.
 */
public class LockItem {
    String lockName;
    int lockID;
    int masterCode;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLockName() {
        return lockName;
    }

    public int getLockID() {
        return lockID;
    }

    public int getMasterCode() {
        return masterCode;
    }

    public void setLockName(String lockName) {
        this.lockName = lockName;
    }

    public void setLockID(int lockID) {
        this.lockID = lockID;
    }

    public LockItem(String lockName) {
        this.lockName = lockName;
    }

    public void setMasterCode(int masterCode) {

        this.masterCode = masterCode;
    }
}
