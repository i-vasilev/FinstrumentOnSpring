package ru.momentum.finstrument.core.entity;

import com.google.gson.annotations.SerializedName;
import ru.momentum.finstrument.core.entity.Department;

import java.util.List;

public class ListDepartments {
    @SerializedName("result")
    private final List<Department> departments;

    public ListDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Department> getDepartments() {
        return departments;
    }
}
