package org.gfg;

import java.util.function.Function;

public class FunctionDemo {


    public static void main(String[] args) {
        Function<Person,Student> register = (person) -> {
            Student student = new Student(1,person.getName(),99);
            return student;
        };


        Function<Student,IdCard> issueCard = (student) -> {
          IdCard card = new IdCard();
          return card;
        };

        Person person = new Person("Ravi",34);

        IdCard idCard = register.andThen(issueCard).apply(person);


    }

}

class IdCard{

}

