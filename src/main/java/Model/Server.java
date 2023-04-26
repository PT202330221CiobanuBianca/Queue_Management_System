package Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;


public class Server implements Runnable{
    private BlockingQueue<Task> tasks;
    private AtomicInteger tWaiting;


    public Server() {
        tasks= new LinkedBlockingDeque<>();
        tWaiting=new AtomicInteger(0);

    }

    public void addTask(Task newTask){
            tasks.add(newTask);
        tWaiting.addAndGet(newTask.gettService());
    }
    @Override
    public void run() {
      while(true) {
          if (this.tasks.size() > 0) {
              Task nextTask = null;
              nextTask = tasks.peek();

              if (nextTask != null) {
                  try {
                      Thread.sleep(1000 * nextTask.gettService());
                         tWaiting.addAndGet(-1*nextTask.gettService());
                  } catch (InterruptedException e) {
                      throw new RuntimeException(e);
                  }

                  if (nextTask.gettService() == 1) {
                      tasks.poll();
                  }

              }

          }
      }
    }

    public void setTasks(BlockingQueue<Task> tasks) {
        this.tasks = tasks;
    }

    public void settWaiting(AtomicInteger tWaiting) {
        this.tWaiting = tWaiting;
    }

    public BlockingQueue<Task> getTasks() {
        return tasks;
    }

    public AtomicInteger gettWaiting() {
        return tWaiting;
    }
}
