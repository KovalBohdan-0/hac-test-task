# Setup instruction

Prerequisites

    Java 17 or later: Ensure you have JDK 17 or later installed.
    Maven: Ensure you have Apache Maven installed.
    PostgreSQL: Ensure you have PostgreSQL installed and running.
    Docker: (Optional, for running Prometheus and Grafana via Docker)

Step-by-Step Instructions
Step 1: Database Setup

    Create a PostgreSQL database:

CREATE DATABASE csv_upload_db;

    Update application.properties with your PostgreSQL configuration:

## PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/csv_upload_db
spring.datasource.username=csv_user
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update

## Prometheus and Micrometer Configuration
management.endpoints.web.exposure.include=prometheus
management.endpoint.prometheus.enabled=true
management.metrics.export.prometheus.enabled=true

Step 2: Build the Project

Navigate to the project directory and build the project using Maven:

mvn clean install

Step 3: Run the Application

You can run the application using Maven or directly using the JAR file:

## Using Maven
mvn spring-boot:run

## Using JAR file
java -jar target/csv-upload-service-0.0.1-SNAPSHOT.jar

Step 4: Accessing the Endpoints

    Health check endpoint:
    
    Endpoint: GET /api/health 

    Upload CSV File:

    Endpoint: POST /api/csv/upload

    Search CSV Data:

    Endpoint: GET /api/csv/search

    Full-text search for CSV Data:

    Endpoint: GET /api/csv/fulltext-search


Step 5: Monitoring with Prometheus and Grafana

If you want to set up Prometheus and Grafana for monitoring, follow these steps:

    Create prometheus.yml configuration file:

global:
  scrape_interval: 15s

scrape_configs:
  - job_name: 'spring-boot'
    metrics_path: '/actuator/prometheus'
    static_configs:
      - targets: ['<ip>:8080']

    Start Prometheus and Grafana using Docker:

## Start Prometheus
docker run -d --name=prometheus -p 9090:9090 -v <path-to-yml>/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus

## Start Grafana
docker run -d --name=grafana -p 3000:3000 grafana/grafana

    Configure Prometheus Data Source in Grafana:
        Open Grafana in your browser: http://localhost:3000
        Log in with the default credentials (admin/admin).
        Add Prometheus as a data source (URL: http://<prometheus-ip>:9090).
