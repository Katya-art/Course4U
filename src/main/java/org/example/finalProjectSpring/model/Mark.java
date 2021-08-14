package org.example.finalProjectSpring.model;

import javax.persistence.*;

/**
 * Simple JavaBean object that represents student's mark.
 *
 * @author Kateryna Kravchenko
 * @version 1.0
 */

@Entity
@Table(name = "marks")
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
