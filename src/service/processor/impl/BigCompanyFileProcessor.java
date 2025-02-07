package service.processor.impl;

import domain.Employee;
import service.processor.FileProcessor;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BigCompanyFileProcessor implements FileProcessor {
    @Override
    public List<Employee> processFile(String filePath) {
        List<Employee> employees= new ArrayList<>();
        validate(filePath);
        try {
            Path path = Paths.get(filePath);
            List<String> lines = Files.readAllLines(path).stream().collect(Collectors.toList());
            if(lines.size()>1){// first line is header
                for (int i = 1; i < lines.size(); i++) {
                    String line = lines.get(i);
                    String[] cells = line.split(",");
                    Employee e = new Employee(Integer.parseInt(cells[0]),cells[1],cells[2],new BigDecimal(cells[3]),fetchManagerId(cells));
                    employees.add(e);
                }
            }
        } catch (IOException exception) {
            throw new IllegalArgumentException("File read exception: " + filePath, exception);
        }
        return employees;
    }

    private Integer fetchManagerId(String[] cells) {
        if(cells.length>4 && cells[4]!=null) {
            return Integer.valueOf(cells[4]);
        }
        return null;
    }

    private void validate(String path) {
        //To do
    }
}
