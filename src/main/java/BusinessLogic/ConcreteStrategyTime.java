package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ConcreteStrategyTime implements Strategy{

    @Override
    public void addTask(List<Server> servers, Task t) {
        Server min=servers.get(0);
        int i=0;
        for (Server s:servers)
             {
                 if(s.gettWaiting().get()< min.gettWaiting().get()) {
                     min = s;
                 }
        }
        min.addTask(t);
    }
}
