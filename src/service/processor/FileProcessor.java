package service.processor;

import domain.Employee;

import java.util.List;

public interface FileProcessor {

    List<Employee> processFile(String filePath);
}
