import java.util.LinkedList;

public class PAndC {
    public static void main(String[] args) throws InterruptedException {
        final PC pc=new PC();

        //creating producer thread
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //creating consumer thread
        //creating producer thread
        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //start both threads
        t1.start();
        t2.start();

        //t1 finishes before t2
        t1.join();
        t2.join();



    }
}


 class PC{
     // Create a list shared by producer and consumer
     // Size of list is 2.
     LinkedList<Integer> list = new LinkedList<>();
     int capacity = 2;

     //funciton called by produer thread

     public void produce() throws InterruptedException {
         int value=0;
         while(true){
             synchronized (this){
                 // producer thread waits while list
                 // is full
                 while(list.size()==capacity)
                     wait();

                 System.out.println("producer produced-"+value);

                 //to insert the jobs in the list
                 list.add(value++);

                 // notifies the consumer thread that
                 // now it can start consuming
                 notify();
                 // makes the working of program easier
                 // to  understand
                 Thread.sleep(1000);



             }
         }



     }


     // Function called by consumer thread
     public void consume() throws InterruptedException
     {
         while (true) {
             synchronized (this)
             {
                 // consumer thread waits while list
                 // is empty
                 while (list.size() == 0)
                     wait();

                 // to retrieve the ifrst job in the list
                 int val = list.removeFirst();

                 System.out.println("Consumer consumed-"
                         + val);

                 // Wake up producer thread
                 notify();

                 // and sleep
                 Thread.sleep(1000);
             }
         }
     }




}
