package ru.momentum.finstrument.platform.model.entity;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Departments")
public class Department {

    @Transient
    @SerializedName("ID")
    private final int id;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final int fid;

    @Column(name = "parent")
    @SerializedName("PARENT")
    private int parentID;

    @Column(name = "Name")
    @SerializedName("NAME")
    private final String name;

    @Column(name = "Company")
    private int companyId;

    public Department(int id, int fid, int parentID, String name, int companyId) {
        this.id = id;
        this.fid = fid;
        this.parentID = parentID;
        this.name = name;
        this.companyId = companyId;
    }

    public int getParentID() {
        return parentID;
    }

    public String getName() {
        return name;
    }


    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getId() {
        return id;
    }

    public int getFid() {
        return fid;
    }
}
