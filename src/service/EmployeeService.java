package service;

import domain.Employee;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface EmployeeService {
    Set<Map.Entry<Employee, Integer>> getLongReportingChainEmps();

    public Set<Map.Entry<Employee, BigDecimal>> getManagersWithLessSal();

    public Set<Map.Entry<Employee,BigDecimal>> getManagersWithMoreSal();
}
