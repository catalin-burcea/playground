package ro.cburcea.playground.java11.utils;

public class Outer {

    public void outerPublic() {
    }

    private void outerPrivate() {
    }

    public class Inner {

        public void innerPublic() {
            outerPrivate();
        }
    }
}