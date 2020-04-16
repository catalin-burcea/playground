package ro.cburcea.playground.springcloud.netflix.archaius.jdbc;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Properties {

    @Id
    private String key;
    private String value;

}