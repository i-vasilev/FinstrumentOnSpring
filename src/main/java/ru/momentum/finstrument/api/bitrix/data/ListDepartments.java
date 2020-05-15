package ru.momentum.finstrument.api.bitrix.data;

import com.google.gson.annotations.SerializedName;
import ru.momentum.finstrument.mvc.model.entity.Department;

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
