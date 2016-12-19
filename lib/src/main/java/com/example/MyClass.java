package com.example;

import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
    public static void mian(String []args){
        Schema schema = new Schema(1,"com.umeng.soexample");

    }
    public static void addUser(Schema schema){
        Entity user = schema.addEntity("User");
        user.addIdProperty().primaryKey();
        user.addStringProperty("uid");
        user.addStringProperty("screen_name");
        user.addStringProperty("gender");
    }
}
