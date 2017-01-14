package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
    public static void main(String []args) throws Exception{
        Schema schema = new Schema(1,"com.nexuslink");
        addUser(schema);
        new DaoGenerator().generateAll(schema,"app/src/main/java-gen");
    }
    public static void addUser(Schema schema){
        Entity user = schema.addEntity("User");
        user.addIdProperty().primaryKey();
        user.addStringProperty("uid");
        user.addStringProperty("screen_name");
        user.addStringProperty("gender");
    }
}
