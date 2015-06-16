package com.braviot.dbtest;

/**
 * Created by HY001 on 2015/6/15.
 */
public class UserItem {
    String name;
    int age;
    int id;

    public UserItem( String name, int age){
        this.age = age;
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}
