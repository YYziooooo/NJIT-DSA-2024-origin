package oy.tol.tra;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

public class DailyTasks {

   private QueueInterface<String> dailyTaskQueue = null;
   private Timer timer = null;
   private static final int TASK_DELAY_IN_SECONDS = 1 * 1000;

   private DailyTasks() {
   }

   /**
    * Execute from the command line:  <code>java -jar target/04-queue-1.0-SNAPSHOT-jar-with-dependencies.jar</code>
    * @param args Not used.
    */
   public static void main(String[] args) {
      DailyTasks tasks = new DailyTasks();
      tasks.run();
   }

   private void run() {
      try {
         // Create a queue for daily tasks
         dailyTaskQueue = new QueueImplementation<>();

         // Read the tasks for today
         readTasks();

         // Create Java Timer object
         timer = new Timer();

         // Schedule the timer at fixed rate with a new TimerTask
         timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
               // Check if there are tasks in the queue
               if (!dailyTaskQueue.isEmpty()) {
                  // If yes, print the task from the queue, dequeueing it
                  String task = dailyTaskQueue.dequeue();
                  System.out.println(task);
               } else {
                  // If not, cancel the timer
                  timer.cancel();
               }
            }
         }, TASK_DELAY_IN_SECONDS, TASK_DELAY_IN_SECONDS);
      } catch (IOException e) {
         System.out.println("Something went wrong :( " + e.getLocalizedMessage());
      }
   }

   private void readTasks() throws IOException {
      // Read tasks from file
      InputStreamReader isr = new InputStreamReader(getClass().getClassLoader().getResourceAsStream("DailyTasks.txt"));
      BufferedReader reader = new BufferedReader(isr);
      String line;
      while ((line = reader.readLine()) != null) {
         // Enqueue the task to the Queue implementation
         dailyTaskQueue.enqueue(line);
      }
      reader.close();

      // Print out the number of tasks in the queue
      System.out.println("Number of tasks in the queue: " + dailyTaskQueue.size());
   }
}
