package org.gfg;

public class Principal {

    private String Name;
    private Integer empId;

    private static Principal INSTANCE = new Principal();

    private Principal() {
    }

    public static Principal getInstance(){
        return INSTANCE;
    }

//    public synchronized static Principal getInstance(){
//        if(INSTANCE == null){
//            INSTANCE = new Principal();
//        }
//        return INSTANCE;
//    }

    public void manageSchool(){

    }
}
