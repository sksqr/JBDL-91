package org.gfg.jbdl91;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringIoCDemo {
    public static void main(String[] args) {

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("projectbeans.xml");
        Engine engine1 = applicationContext.getBean("engine1",Engine.class);
        System.out.println(engine1);

        Engine engine2 = applicationContext.getBean("engine2",Engine.class);
        System.out.println(engine1 == engine2);

        Engine engine4 = applicationContext.getBean("engine2",Engine.class);
        System.out.println("engine4 == engine2:"+(engine4 == engine2));


        Engine engine3 = applicationContext.getBean("engine1",Engine.class);
        System.out.println(engine1 == engine3);


        Car car1 = applicationContext.getBean("car1", Car.class);
        System.out.println(car1);
        System.out.println(engine1 == car1.getEngine());


        Engine engine5 = new Engine();
        System.out.println(engine1 == engine5);
    }
}
