package BusinessLogic;

import Model.Server;
import Model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {

    private List<Server> servers;
    private int maxServers;
    private int maxTasksPerServer;
    private Strategy strategie;

    public Scheduler(int maxServers, int maxTasksPerServer) {
        this.maxServers = maxServers;
        this.maxTasksPerServer = maxTasksPerServer;
        servers=new ArrayList<>();
        for(int i=0;i<maxServers;i++)
        {
            Server newServer= new Server();
            servers.add(newServer);
            Thread newThread= new Thread(newServer);
            newThread.start();
        }
    }

    public void changeStrategy(SelectionPolicy policy)
    {
        if(policy==SelectionPolicy.SHORTEST_QUEUE){
            strategie=new ConcreteStrategyQueue();
        }
        if(policy==SelectionPolicy.SHORTEST_TIME){
            strategie=new ConcreteStrategyTime();
        }
    }

    public void dispatchTask(Task t)
    {
        strategie.addTask(servers, t);
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public void setMaxServers(int maxServers) {
        this.maxServers = maxServers;
    }

    public void setMaxTasksPerServer(int maxTasksPerServer) {
        this.maxTasksPerServer = maxTasksPerServer;
    }

    public void setStrategie(Strategy strategie) {
        this.strategie = strategie;
    }

    public List<Server> getServers() {
        return servers;
    }

    public int getMaxServers() {
        return maxServers;
    }

    public int getMaxTasksPerServer() {
        return maxTasksPerServer;
    }

    public Strategy getStrategie() {
        return strategie;
    }
}
