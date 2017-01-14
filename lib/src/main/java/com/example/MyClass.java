package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
    public static void main(String []args){
        Schema schema = new Schema(1,"com.nexuslink");
        createTable(schema);
        try {
            new DaoGenerator().generateAll(schema, "../PopSport/app/src/main/java-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static void createTable(Schema schema){
        Entity steps = schema.addEntity("Steps");
        steps.addIdProperty().primaryKey();
        steps.addIntProperty("steps");
        steps.addStringProperty("currentDate");


    }
}
