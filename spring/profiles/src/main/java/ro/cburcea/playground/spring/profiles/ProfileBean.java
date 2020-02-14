package ro.cburcea.playground.spring.profiles;

public class ProfileBean {

    private String name;

    ProfileBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
