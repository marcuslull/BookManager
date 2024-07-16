## DB SCHEMA

### Books
ID PK - LONG  
Title - VARCHAR  
Pages - INTEGER  
Number in series - INTEGER  
Finished - BOOLEAN  
Media - VARCHAR  
Rating ID FK - LONG  
Author IDs FK - LONG  
Genre IDs FK - LONG  
Series ID FK - LONG  

### Authors
ID PK - LONG  
Name - VARCHAR  
Book IDs FK - LONG  
Series IDs FK - LONG  

### Genres
ID PK - LONG  
Genre - VARCHAR  

### Series
ID PK - LONG  
Name - VARCHAR  
Size - INTEGER  
Finished - BOOLEAN  
Book IDs FK - LONG  
Author IDs FK - LONG  
Genre IDs FK - LONG  

### Comments
ID PK - LONG  
Title - VARCHAR  
Comment - VARCHAR  

### Rating
ID PK - LONG  
Rating - INTEGER  


## ENTITIES





Concept: Book Management API with Advanced Search (Focus on Well-designed REST API)

Functionality:

Users can Create, Read, Update, and Delete bookEntities through the API.
Implement functionalities for searching bookEntities based on various criteria:
Title (fuzzy search optional)
Author (fuzzy search optional)
Genre (with pre-defined genres or user-defined)
Publication date range
Availability (available/borrowed)
Backend Technologies:

Java 17 (or a recent version)
Spring Boot for rapid development and autoconfiguration
Spring Data JPA for interacting with the H2 database
Lombok for reducing boilerplate code (optional)
REST API Design:

Follow RESTful principles strictly:
Use appropriate HTTP methods for CRUD operations (POST, GET, PUT, DELETE)
Use descriptive URLs to represent resources (e.g., /bookEntities, /bookEntities/{id})
Implement proper data validation and informative error handling with HTTP status codes.
Use JSON for data exchange with the API.
Implement HATEOAS to provide links to related resources in response messages. This allows clients to discover available actions and navigate the API dynamically.
H2 Database:

Leverage H2's in-memory database for development and testing.
Configure H2 console access for easy database management.
Portfolio Showcase:

Document your API using tools like Swagger or OpenAPI.
Write unit and integration tests to ensure code quality focusing on API functionality.
Benefits:

This project showcases your expertise in designing a well-structured, informative, and user-friendly REST API using Java and Spring Boot.
Advanced search functionality demonstrates your ability to handle complex queries effectively within the API.
Unit and integration tests highlight your focus on building robust and reliable APIs.
Further Enhancements (API Focus):

Implement HATEOAS with pagination for handling large datasets of bookEntities.
Introduce versioning for your API to handle future updates.
Add filtering capabilities to search results based on additional criteria (e.g., minimum rating).



