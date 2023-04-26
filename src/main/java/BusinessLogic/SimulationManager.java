 package BusinessLogic;

import Model.Server;
import Model.Task;
import UI.SimulationForm;

import java.awt.print.PrinterException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class SimulationManager implements  Runnable {

    public int timeLimit;  //max processing time-read from UI
    public int maxProcessingTime;
    public int minProcessingTime;

    public int minArrivalTime;

    public int maxArrivalTime;

    public int numberOfServers;

    public int numberOfClients;

    public SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;

    //entity responsible with queue management and client distribution
    private Scheduler scheduler;

    //frame for displaying simulation
    private SimulationForm simulationForm;

    //pool of tasks (client shopping in the store)
    private List<Task> generatedTasks;


    public SimulationManager(int numberOfClients, int numberOfServers, int simulationTime, int minProcessingTime, int maxProcessingTime, int minArrivalTime, int maxArrivalTime, SimulationForm sim) {
        this.numberOfClients = numberOfClients;
        this.numberOfServers = numberOfServers;
        this.timeLimit = simulationTime;
        this.minProcessingTime = minProcessingTime;
        this.maxProcessingTime = maxProcessingTime;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.simulationForm = sim;
        scheduler = new Scheduler(numberOfServers, numberOfClients);
        scheduler.changeStrategy(selectionPolicy);
        generatedTasks = Collections.synchronizedList(new ArrayList<>());
        generateNRandomTasks();
    }

    private void generateNRandomTasks() {
        //generate N random tasks;
        //random processing time
        //minProcessingTime<processingTime<maxProcessingTime
        //random arrivalTime
        for (int i = 0; i < numberOfClients; i++) {
            Random rand = new Random();
            int arrive = rand.nextInt(minArrivalTime, maxArrivalTime);
            int service = rand.nextInt(minProcessingTime, maxProcessingTime);
            Task task = new Task(i, arrive, service);
            generatedTasks.add(task);
        }
        //sort list with respect to arrivalTime
        generatedTasks.sort(Comparator.comparing(Task::gettArrival));
    }


    @Override
    public void run() {
        int currentTime = 0;
        try (FileWriter writer = new FileWriter("prezentare.txt")) {
            PrintWriter out = new PrintWriter(writer, true);
            while (currentTime < timeLimit) {
                System.out.println("Time:" + currentTime);
                System.out.println("Waiting clients:");
                out.println("Time:" + currentTime);
                out.println("Waiting clients:");
                simulationForm.getTextArea1().append("Time:" + currentTime+"\n");
                simulationForm.getTextArea1().append("Waiting clients:\n");
                synchronized (generatedTasks) {
                    Iterator<Task> it = generatedTasks.iterator();
                    while (it.hasNext()) {
                        Task t = it.next();
                        if (t.gettArrival() == currentTime) {
                            scheduler.dispatchTask(t);
                            it.remove();

                        }
                        if (t.gettArrival() != currentTime) {
                            System.out.println("(" + t.getID() + "," + t.gettArrival() + "," + t.gettService() + ")");
                            out.println("(" + t.getID() + "," + t.gettArrival() + "," + t.gettService() + ")");
                            simulationForm.getTextArea1().append("(" + t.getID() + "," + t.gettArrival() + "," + t.gettService() + ")\n");
                        }
                    }
                }
                for (int i = 0; i < numberOfServers; i++) {
                    int j = i + 1;
                    if (scheduler.getServers().get(i).getTasks().size() == 0) {
                        System.out.println("Queue " + j + ":closed");
                        out.println("Queue " + j + ":closed");
                        simulationForm.getTextArea1().append("Queue " + j + ":closed\n");
                    } else {
                        System.out.println("Queue " + j + ":");
                        System.out.println("Waiting time:" + scheduler.getServers().get(i).gettWaiting().get());
                        out.println("Queue " + j + ":");
                        out.println("Waiting time:" + scheduler.getServers().get(i).gettWaiting().get());
                        simulationForm.getTextArea1().append("Queue " + j + ":\n");
                        simulationForm.getTextArea1().append("Waiting time:" + scheduler.getServers().get(i).gettWaiting().get()+"\n");

                        for (Task t : scheduler.getServers().get(i).getTasks()) {
                            System.out.println("(" + t.getID() + "," + t.gettArrival() + "," + t.gettService() + ")");
                            out.println("(" + t.getID() + "," + t.gettArrival() + "," + t.gettService() + ")");
                            simulationForm.getTextArea1().append("(" + t.getID() + "," + t.gettArrival() + "," + t.gettService() + ")\n");
                            scheduler.getServers().get(i).gettWaiting().decrementAndGet();
                             t.settService(t.gettService() - 1);
                            if(t.gettService()<=0)
                                scheduler.getServers().get(i).getTasks().remove(t);
                            //if (scheduler.getServers().get(i).gettWaiting().get() != 0)

                        }
                    }

                }

                currentTime++;
                //wait an interval of 1 second
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println();
                out.println();
                simulationForm.getTextArea1().append("\n");

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}