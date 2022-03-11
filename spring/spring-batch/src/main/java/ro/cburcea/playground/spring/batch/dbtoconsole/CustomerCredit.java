package ro.cburcea.playground.spring.batch.dbtoconsole;

public class CustomerCredit {

    private int id;
    private String name;
    private Integer credit;

    public CustomerCredit() {
    }

    public CustomerCredit(int id, String name, Integer credit) {
        this.id = id;
        this.name = name;
        this.credit = credit;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "CustomerCredit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", credit=" + credit +
                '}';
    }
}