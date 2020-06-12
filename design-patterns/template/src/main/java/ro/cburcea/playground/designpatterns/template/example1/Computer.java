package ro.cburcea.playground.designpatterns.template.example1;

import java.util.Map;

public class Computer {
    
    private Map<String, String> computerParts;
    
    public Computer(Map<String, String> computerParts) {
        this.computerParts = computerParts;
    }
    
    public Map<String, String> getComputerParts() {
        return computerParts;
    }
}