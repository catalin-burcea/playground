package ro.cburcea.playground.designpatterns.state.postoffice;

public interface PackageState {
 
    void next(Package pkg);
    void prev(Package pkg);
    void printStatus();
}