package ro.cburcea.playground.spring.amqp;

public class Foo {

    private String foo;

    public Foo() {
        super();
    }

    public Foo(String foo) {
        this.foo = foo;
    }

    public String getFoo() {
        return this.foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    @Override
    public String toString() {
        return "Foo [foo=" + this.foo + "]";
    }
}