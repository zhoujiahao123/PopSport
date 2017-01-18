package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
<<<<<<< HEAD
<<<<<<< HEAD
    public static void main(String []args) throws Exception{
        Schema schema = new Schema(1,"com.nexuslink");
        addUser(schema);
        new DaoGenerator().generateAll(schema,"app/src/main/java-gen");
=======
=======

>>>>>>> a97d647881641ae8945a6cec92e58fd10ec526cf
    public static void main(String []args){
        Schema schema = new Schema(1,"com.nexuslink");
        createTable(schema);
        try {
            new DaoGenerator().generateAll(schema, "./app/src/main/java-gen");
        } catch (Exception e) {
            e.printStackTrace();
        }

<<<<<<< HEAD
>>>>>>> db6c165b99a10fa2af1fab005d612dfc816881bb
=======
>>>>>>> a97d647881641ae8945a6cec92e58fd10ec526cf
    }
    public static void createTable(Schema schema){
        Entity steps = schema.addEntity("Steps");
        steps.addIdProperty().primaryKey();
        steps.addIntProperty("steps");
        steps.addStringProperty("currentDate");


    }
}
