package org.rest.hactesttask.csvdata;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class CSVDataService {
    private final CSVDataRepository csvDataRepository;
    private final EntityManager entityManager;

    public CSVDataService(CSVDataRepository csvDataRepository, EntityManager entityManager) {
        this.csvDataRepository = csvDataRepository;
        this.entityManager = entityManager;
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
                csvData.setBio(columns[3]);
                csvData.setAge(Integer.parseInt(columns[4]));

                csvDataRepository.save(csvData);
            }
        } catch (IOException e) {
            throw new IOException("Error processing file.");
        }
    }

    public List<CSVData> searchByName(String query) {
        return csvDataRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query);
    }

    public List<CSVData> fullTextSearch(String query) {
        return Search.session(entityManager).search(CSVData.class)
                .where(f -> f.match()
                        .fields("firstName", "lastName", "email", "bio")
                        .matching(query)
                        .fuzzy())
                .fetchHits(20);
    }
}
