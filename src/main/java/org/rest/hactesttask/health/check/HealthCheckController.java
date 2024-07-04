package org.rest.hactesttask.health.check;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Health Check")
@RestController
@RequestMapping("/api/health")
public class HealthCheckController {
    @Operation(summary = "Check the health of the application")
    @GetMapping
    public String healthCheck() {
        return "UP";
    }
}
