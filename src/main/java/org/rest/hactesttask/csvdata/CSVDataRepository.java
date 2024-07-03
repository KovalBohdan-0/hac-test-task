package org.rest.hactesttask.csvdata;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CSVDataRepository extends JpaRepository<CSVData, Long> {
}