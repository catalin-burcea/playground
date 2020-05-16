package ro.cburcea.playground.designpatterns.factory.abstractfactory;

public interface AbstractFactory<T> {
    T create(String type);
}