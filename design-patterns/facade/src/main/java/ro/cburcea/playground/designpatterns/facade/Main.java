package ro.cburcea.playground.designpatterns.facade;

public class Main {

    public static void main(String[] args) {
        CarEngineFacade carEngineFacade = new CarEngineFacade();
        carEngineFacade.startEngine();
        carEngineFacade.stopEngine();
    }
}
