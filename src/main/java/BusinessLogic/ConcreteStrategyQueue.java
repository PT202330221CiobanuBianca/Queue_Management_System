package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy {
    @Override
    public void addTask(List<Server> servers, Task t) {
        Server min=servers.get(0);
        for (Server s:servers)
        {
            if(s.getTasks().size()< min.getTasks().size())
                min=s;
        }
        min.addTask(t);
    }
}
