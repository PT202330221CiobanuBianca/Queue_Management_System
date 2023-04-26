package Model;

public class Task {
    private Integer ID;
    private Integer tArrival;
    private Integer tService;

    private Integer tService2;

    public Task(Integer ID, Integer tArrival, Integer tService) {
        this.ID = ID;
        this.tArrival = tArrival;
        this.tService = tService;
        this.tService2 = tService;
    }

    public Integer getID() {
        return ID;
    }

    public Integer gettArrival() {
        return tArrival;
    }

    public Integer gettService() {
        return tService;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public void settArrival(Integer tArrival) {
        this.tArrival = tArrival;
    }

    public void settService(Integer tService) {
        this.tService = tService;
    }

    public Integer gettService2() {
        return tService2;
    }

    public void settService2(Integer tService2) {
        this.tService2 = tService2;
    }
}
