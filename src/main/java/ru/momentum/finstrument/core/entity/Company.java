package ru.momentum.finstrument.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Companies")
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(name = "address")
    private String address;

    @Column(name = "user")
    private User user;

    public Company(String address) {
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }
}
