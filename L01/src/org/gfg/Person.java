package org.gfg;

public class Person {

    private String name;
    protected Integer age;

    public Person() {

        System.out.println("Executing Default Constructor of Person");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        // add code for validation, access,
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public void walk(){
        System.out.println("Person "+name+" is walking");
    }
}
