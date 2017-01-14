package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
<<<<<<< HEAD
    public static void main(String []args){
        Schema schema = new Schema(1,"com.nexuslink");
        createTable(schema);
        try {
            new DaoGenerator().generateAll(schema, "../PopSport/app/src/main/java-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }

=======
    public static void main(String []args) throws Exception{
        Schema schema = new Schema(1,"com.nexuslink");
        addUser(schema);
        new DaoGenerator().generateAll(schema,"app/src/main/java-gen");
>>>>>>> 754fa4f10e2ec1296c2ca4beaa393b94cbeede4b
    }
    public static void createTable(Schema schema){
        Entity steps = schema.addEntity("Steps");
        steps.addIdProperty().primaryKey();
        steps.addIntProperty("steps");
        steps.addStringProperty("currentDate");


    }
}
