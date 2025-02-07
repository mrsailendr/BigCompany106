package service.impl;

import domain.Employee;
import service.EmployeeService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class BigCompanyEmployeeService implements EmployeeService {

    private List<Employee> employees;
    private Map<Integer, Employee> idEmpployeeMap;
    private Map<Employee,List<Employee>> managerEmpMap;
    private Map<Employee,BigDecimal> managerPercentageMap;

    public BigCompanyEmployeeService(List<Employee> employees){
        this.employees = employees;
        idEmpployeeMap = getIdEmployeeMap();
        managerEmpMap= getManagerEmpMap();
        managerPercentageMap = getManagerPercentage();
    }

    private Map<Employee, List<Employee>> getManagerEmpMap() {
        return employees.stream().filter(e-> Objects.nonNull(e.getManagerId())).collect(Collectors.groupingBy(e->idEmpployeeMap.get(e.getManagerId())));
    }

    private Map<Integer, Employee> getIdEmployeeMap() {
        return employees.stream().collect(Collectors.toMap(Employee::getId,employee -> employee));
    }


    @Override
    public Set<Map.Entry<Employee, Integer>> getLongReportingChainEmps(){
        List<Employee> longReportingChainEmps = new ArrayList<>();
        Map<Employee, Integer> empReportingCount = new HashMap<>();
        for (Employee e:employees) {
            empReportingCount.put(e,getReportingCount(e));
        }
        return empReportingCount.entrySet().stream().filter(e -> e.getValue() > 4).collect(Collectors.toSet());
    }

    private int getReportingCount(Employee e) {
        int count=0;
        while (e.getManagerId()!=null){
            count++;
            e = idEmpployeeMap.get(e.getManagerId());
        }
        return count;
    }

    private Map<Employee,BigDecimal> getManagerPercentage(){
        Map<Employee,BigDecimal> managerPercentage = new HashMap<>();
        for (Map.Entry<Employee,List<Employee>> entry: managerEmpMap.entrySet()){
            BigDecimal sum = entry.getValue().stream().map(e -> e.getSalary()).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal avg = sum.divide(BigDecimal.valueOf(entry.getValue().size()));
            BigDecimal percentage = entry.getKey().getSalary().divide(avg).multiply(new BigDecimal(100));
            managerPercentage.put(entry.getKey(),percentage);
        }
        return managerPercentage;
    }

    //@Override
    public Set<Map.Entry<Employee,BigDecimal>> getManagersWithLessSal(){
        return managerPercentageMap.entrySet().stream().filter(e -> e.getValue().compareTo(new BigDecimal(120)) < 0).collect(Collectors.toSet());
    }

    @Override
    public Set<Map.Entry<Employee,BigDecimal>> getManagersWithMoreSal(){
        return managerPercentageMap.entrySet().stream().filter(e -> e.getValue().compareTo(new BigDecimal(150)) > 0).collect(Collectors.toSet());
    }
}
