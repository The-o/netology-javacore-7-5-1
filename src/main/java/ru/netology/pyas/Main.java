package ru.netology.pyas;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import ru.netology.pyas.employee.Employee;

public class Main {

    public static void main(String[] args) {
        final String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        final String inFile = "data.csv";
        final String outFile = "data.json";

        List<Employee> list = parseCSV(columnMapping, inFile);
        String json = listToJson(list);
        jsonToFile(json, outFile);
    }

    private static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        List<Employee> result;
        
        ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(Employee.class);
        strategy.setColumnMapping(columnMapping);

        try(CSVReader reader = new CSVReader(new FileReader(fileName))) {
            CsvToBean csv = new CsvToBeanBuilder<Employee>(reader)
                .withMappingStrategy(strategy)
                .build();
            result = csv.parse();
        } catch (IOException e) {
            e.printStackTrace();
            result = new ArrayList<Employee>();
        }

        return result;
    }

    private static String listToJson(List<Employee> list) {
        Gson gson = new GsonBuilder().create();
        return gson.toJson(list, list.getClass());
    }

    private static void jsonToFile(String json, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}