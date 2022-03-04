package ro.cburcea.playground.designpatterns.bridge;

import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle {

    // assembly line for the workshops
    protected List<WorkShop> workshops = new ArrayList<>();

    public Vehicle() {
        super();
    }

    public boolean joinWorkshop(WorkShop workshop) {
        return workshops.add(workshop);
    }

    public abstract void manufacture();

    public abstract int minWorkTime();
}