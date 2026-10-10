# Claims Management Application

A complete Spring Boot 3.x application built with Java 17+, H2 file-based persistent database, Spring Data JPA, Thymeleaf, Bootstrap 5, and file upload/storage capabilities.

## Features
- **Claim Attributes Managed**: Claim ID, Claimant, Policy Number, Policy Issuer, Age of Claimant, Claim Reason, Status, and Supporting Document (File Upload/Download).
- **CRUD Operations**: Create, Read, Update, Delete claims via a clean Bootstrap web interface.
- **Persistent Storage**: H2 database configured in file mode (`./data/claimsdb`) so data persists across restarts.
- **File Storage**: Evidence files are saved locally on disk (`./uploaded-files`).

## Running the Application

1. Ensure you have **Java 17+** and **Maven** installed.
2. Run the application using Maven:
   ```bash
   mvn spring-boot:run
   ```
3. Open your browser and navigate to:
   - **Web UI**: [http://localhost:8080/claims](http://localhost:8080/claims)
   - **H2 Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
     - JDBC URL: `jdbc:h2:file:./data/claimsdb`
     - User Name: `sa`
     - Password: *(leave blank)*
