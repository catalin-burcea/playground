package ro.cburcea.playground.designpatterns.singleton;

import java.util.Arrays;

/**
 * An Enum is singleton by design. All the enum values are initialized only once at the time of class loading.
 * The disadvantage of this approach is that it is a bit inflexible compared to other approaches.
 * The enums don't not allow lazy initialization.
 */
enum EnumSingleton {
    WEEKDAY("Monday", "Tuesday", "Wednesday", "Thursday", "Friday"),
    WEEKEND("Saturday", "Sunday");

    private String[] days;

    EnumSingleton(String... days) {
        System.out.println("Initializing enum with " + Arrays.toString(days));
        this.days = days;
    }

    public String[] getDays() {
        return this.days;
    }

    @Override
    public String toString() {
        return "EnumSingleton{" +
                "days=" + Arrays.toString(days) +
                '}';
    }
}