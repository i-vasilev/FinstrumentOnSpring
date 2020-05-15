package ru.momentum.finstrument.mvc.model.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Executors")
public class User {

    @Transient
    @SerializedName("ID")
    private int ID;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int fid;

    private int department;

    private boolean isActive;

    public User(int ID, int department, boolean isActive) {
        this.ID = ID;
        this.department = department;
        this.isActive = isActive;
    }

    public int getID() {
        return ID;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setDepartment(int department) {
        this.department = department;
    }
}
