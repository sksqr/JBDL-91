public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Sleeping for 5 sec");
                try {
                    System.out.println(Thread.currentThread().getState().name());
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName());
            }
        });


        System.out.println(th.getState().name());
        th.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(th.getState().name());
        System.out.println("Done");
    }
}