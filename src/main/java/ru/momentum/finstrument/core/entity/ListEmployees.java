package ru.momentum.finstrument.core.entity;

import com.google.gson.annotations.SerializedName;
import ru.momentum.finstrument.core.entity.Employee;

import java.util.List;

public class ListEmployees {
    @SerializedName("result")
    private final List<Employee> employees;

    public ListEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
