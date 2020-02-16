package ro.cburcea.playground.designpatterns.bridge;

/**
 * After defining the types of vehicles, we are not implementing specifications classes, like ProduceBike, AssembleBike, ProduceCar, or  AssembleCar.
 * All we have done is created an assembly line of the workshops in the Vehicle class
 * and each vehicle will join the workshop to perform its manufacturing or repairing processes.
 *
 * To achieve this, we have created the interface WorkShop.
 * This workshop interface will act as a bridge between various types of vehicles and its manufacturing work (implementors).
 */
public abstract class WorkShop {
    public abstract void work(Vehicle vehicle);
}