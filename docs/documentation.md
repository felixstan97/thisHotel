# ThisHotel - Hotel Management API

## Overview

**ThisHotel** is a Spring Boot-based backend application designed for hotel management. The goal of this project is to provide a robust and scalable RESTful API to manage rooms, users, and (in the future) bookings, with a strong focus on security, data validation, and separation of concerns. The project has been developed iteratively, implementing core features for room and user management while laying the groundwork for additional functionalities like booking and cleaning task management.

Built with **Java 17**, **Spring Boot**, **Spring Security**, and **JPA/Hibernate**, the system follows a layered architecture (controller, service, repository) and integrates modern tools such as **Lombok** to reduce boilerplate code and **MapStruct** for object mapping. Security is handled via JWT authentication, with role-based access control (ADMIN, RECEPTIONIST, MANAGER, etc.).

---

## Implemented Features

### 1. Room Management
- **Room Creation (`POST /api/admin/rooms/create-room`)**:
    - REST endpoint to create a new room, restricted to users with the `ROLE_ADMIN` role.
    - Input: `CreateRoomRequestDTO` with fields like `roomNumber`, `roomType`, `bedTypes`, `price`, `floor`, and options such as `isDisabledFriendly`, `hasBalcony`, etc.
    - Validation:
        - Checks for `roomNumber` (non-empty, unique).
        - Limits on `bedTypes` based on room type (e.g., `SINGLE` max 1 bed).
        - Jakarta Validation annotations (`@NotNull`, `@Positive`, etc.).
    - Output: `ApiResponseDTO<RoomResponseDTO>` with essential room details (e.g., `id`, `roomNumber`, `price`).
    - Implementation:
        - `RoomService`: Business logic with database persistence via `RoomRepository`.
        - `RoomValidationService`: Specific validation rules.
        - `RoomMapper`: Mapping between `Room` entity and DTOs.

- **List All Rooms (`GET /api/admin/rooms`)**:
    - REST endpoint to retrieve all rooms, accessible to `ROLE_ADMIN`, `ROLE_RECEPTIONIST`, and `ROLE_MANAGER`.
    - Output: `ApiResponseDTO<List<RoomResponseDTO>>` with essential data (`id`, `roomNumber`, `roomType`, `price`, `floor`, `roomStatus`).
    - Features:
        - Dynamic message: `"Rooms retrieved successfully."` or `"No rooms found."` if the list is empty.
        - Java Streams used to map `List<Room>` to `List<RoomResponseDTO>` with `RoomMapper`.
    - Implementation:
        - `RoomService.getAllRooms()`: Fetches raw data from the repository.
        - Controller: Transforms data and builds the response.

- **Next Steps (Planned)**:
    - `GET /api/admin/rooms/{id}`: Retrieve a specific room by ID with full details (`RoomDetailResponseDTO`).
    - Search by room number or characteristics (e.g., `GET /api/admin/rooms?roomNumber=101`).

### 2. User Management and Security
- **Registration and Authentication**:
    - Public endpoints: `/api/auth/**` (e.g., login, admin registration).
    - **JWT** (JSON Web Token) used for stateless authentication.
    - `UserValidationService`: Validates email and password, with `trim()` to remove whitespace.
- **Roles and Permissions**:
    - Defined roles: `ROLE_ADMIN`, `ROLE_RECEPTIONIST`, `ROLE_MANAGER`, `ROLE_CLEANER`, `ROLE_CLIENT`.
    - Configuration in `SecurityConfig`:
        - `/api/admin/rooms/**`: Accessible to `ADMIN`, `RECEPTIONIST`, `MANAGER`.
        - `/api/bookings/**`: Accessible to `CLIENT`, `RECEPTIONIST`, `MANAGER`, `ADMIN`.
        - `/api/users/**`: Restricted to `ADMIN`.
    - Use of `@PreAuthorize` for fine-grained control (e.g., only `ADMIN` can create rooms).
- **Security**:
    - Authentication via `JwtFilter`.
    - Passwords encrypted with `BCryptPasswordEncoder`.
    - Sessions disabled (`SessionCreationPolicy.STATELESS`).

### 3. Structure and Design
- **Layered Architecture**:
    - **Controller**: Handles HTTP requests, maps DTOs, and prepares responses (`ApiResponseDTO`).
    - **Service**: Contains business logic (e.g., `RoomService`, `UserValidationService`).
    - **Repository**: JPA interface for database access (e.g., `RoomRepository`).
- **DTOs (Data Transfer Objects)**:
    - `CreateRoomRequestDTO`: Input for room creation with validations.
    - `RoomResponseDTO`: Lightweight output for lists (e.g., `id`, `roomNumber`, `roomType`).
    - `ApiResponseDTO<T>`: Generic response structure (`status`, `data`, `message`, `errorCode`).
- **Mapping**:
    - `RoomMapper` (MapStruct): Converts between `Room` entity and DTOs.
- **Lombok**:
    - `@RequiredArgsConstructor`: Dependency injection without manual constructors.
    - `@Data`: Getters, setters, etc., for DTOs.

### 4. Validation and Error Handling
- **Input Validation**:
    - `RoomValidationService`: Specific checks for room creation.
    - `UserValidationService`: Validation for registration and password reset.
    - Jakarta annotations (`@NotBlank`, `@Positive`, etc.).
- **Exceptions**:
    - `InvalidInputException`: For input errors (e.g., "Room number already exists").
    - Descriptive messages with values (e.g., `"Room number '101' cannot be empty."`).
- **Error Responses**:
    - Standard format: `ApiResponseDTO` with `status: "error"`, `message`, and `errorCode`.

---

## Technologies Used
- **Java 17**: Core programming language.
- **Spring Boot**: Application framework.
- **Spring Security**: Authentication and authorization.
- **JPA/Hibernate**: Data persistence.
- **Lombok**: Boilerplate reduction.
- **MapStruct**: Object mapping.
- **H2/PostgreSQL**: Database (configurable).
- **Maven**: Dependency management.

---

## How to Run the Project
1. **Prerequisites**:
    - Java 17+
    - Maven
    - Configured database (e.g., H2 in-memory for testing)
2. **Clone the Repository**:
   ```bash
   git clone <repo-url>
   cd thishotel
3. **Build and Run**:
   ```bash
   mvn clean install
   mvn spring-boot:run
4. **Test Endpoints**:

    - Open Postman.
    - Run the requests to test the API endpoints (e.g., create a room, list all rooms).

**Test with Postman**: Open Postman. Import the collection from postman/ThisHotel_API_Tests.postman_collection.json. Follow this flow:
1. *Register Admin*: Run FirstRegistrationAdminGeneration > Register AMIN to create the initial admin (admin@admin.com, password123). This resets the token variable.
2. *Login Admin*: Run RolesLogin > Login Admin to authenticate and save the JWT in the token variable. The token is applied globally via bearer auth.
3. *Register Roles*: Run RolesRegistration requests (e.g., Register RECEPTIONIST, Register MANAGER, Register CLEANER) using the admin token to create staff.
4. *Login Manager*: Run RolesLogin > Login Manager to authenticate as manager@manager.com and update the token for manager-specific tests.
5. *Create Rooms*: Run CreateRooms requests (e.g., CreateStandardRoom, CreateSuiteRoom) with the admin token to add rooms.
6. *Get Rooms*: Test GetRooms endpoints (GetAllRooms, GetRoomByID) with either admin or manager token to retrieve room data.
   Note: The token is managed globally by the collection’s bearer auth. Login tests for RECEPTIONIST and CLEANER will be added later.


5. **Final Notes**
   This project started as a practical exploration of Spring Boot and REST, with a focus on clean code, security, and scalability.
   Each feature has been implemented with attention to detail, from validations to error messages to RESTful endpoint design.
   Through a collaborative approach, we’ve optimized the codebase step by step, making it ready to grow with new features.

