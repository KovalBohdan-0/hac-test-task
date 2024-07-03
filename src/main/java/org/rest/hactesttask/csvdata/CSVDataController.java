package org.rest.hactesttask.csvdata;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Tag(name = "CSV Data")
@RestController
@RequestMapping("/api/csv")
public class CSVDataController {
    private final CSVDataService csvDataService;

    public CSVDataController(CSVDataService csvDataService) {
        this.csvDataService = csvDataService;
    }

    @Operation(summary = "Upload a CSV file and save data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File uploaded and data saved successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "500", description = "Error processing file",
                    content = @Content)})
    @PostMapping("/upload")
    public ResponseEntity<String> uploadCSV(@RequestParam("file") MultipartFile file) {
        try {
            csvDataService.saveCSVData(file);
            return ResponseEntity.ok("File uploaded and data saved successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing file.");
        }
    }

    @Operation(summary = "Search for CSV data by name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CSV data found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CSVData.class))
            })
    })
    @GetMapping("/search")
    public ResponseEntity<List<CSVData>> searchCSV(@RequestParam("query") String query) {
        List<CSVData> results = csvDataService.searchByName(query);
        return ResponseEntity.ok(results);
    }
}