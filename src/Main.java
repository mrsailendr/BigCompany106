import domain.Employee;
import service.EmployeeService;
import service.impl.BigCompanyEmployeeService;
import service.processor.FileProcessor;
import service.processor.impl.BigCompanyFileProcessor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        System.out.println("Enter file path: ");
        String filePath = new Scanner(System.in).nextLine();

        FileProcessor fileProcessor = new BigCompanyFileProcessor();
        List<Employee> employees = fileProcessor.processFile(filePath.trim());

        EmployeeService service = new BigCompanyEmployeeService(employees);

        Set<Map.Entry<Employee, BigDecimal>> managersWithMoreSal = service.getManagersWithMoreSal();
        if (!managersWithMoreSal.isEmpty()) {
            System.out.println("Employees with more avg salary, id: percentage");
            managersWithMoreSal.stream().forEach(e -> System.out.println(e.getKey().getId() + " : " + e.getValue().toString()));
        }

        Set<Map.Entry<Employee, BigDecimal>> managersWithLessSal = service.getManagersWithLessSal();
        if(!managersWithLessSal.isEmpty()) {
            System.out.println("Employees with less avg salary, id: percentage");
            managersWithLessSal.stream().forEach(e -> System.out.println(e.getKey().getId() + " : " + e.getValue().toString()));
        }

        Set<Map.Entry<Employee, Integer>> longReportingChainEmps = service.getLongReportingChainEmps();
        if(!longReportingChainEmps.isEmpty()) {
            System.out.println("Employees with more manager chain, id: manager chain count");
            longReportingChainEmps.stream().forEach(e -> System.out.println(e.getKey().getId() + " : " + e.getValue().toString()));
        }
    }
}