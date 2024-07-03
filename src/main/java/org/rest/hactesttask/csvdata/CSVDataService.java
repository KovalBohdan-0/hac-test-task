package org.rest.hactesttask.csvdata;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class CSVDataService {
    private final CSVDataRepository csvDataRepository;

    public CSVDataService(CSVDataRepository csvDataRepository) {
        this.csvDataRepository = csvDataRepository;
    }

    @Transactional
    public void saveCSVData(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                CSVData csvData = new CSVData();
                csvData.setFirstName(columns[0]);
                csvData.setLastName(columns[1]);
                csvData.setEmail(columns[2]);
                csvData.setPhone(columns[3]);
                csvData.setAge(Integer.parseInt(columns[4]));

                csvDataRepository.save(csvData);
            }
        } catch (IOException e) {
            throw new IOException("Error processing file.");
        }
    }
}
