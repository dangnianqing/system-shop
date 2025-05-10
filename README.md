# System Shop Management

A Spring Boot application for managing users, roles, menus, and permissions with JWT authentication.

## Technologies Used

- Spring Boot 2.7.0
- Spring Security
- JWT Authentication
- MyBatis
- MySQL
- Redis
- Maven

## Features

- User authentication with JWT
- Role-based access control
- Menu management
- User management
- Role management

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven
- MySQL
- Redis

### Database Setup

1. Create a MySQL database named `system_shop`
2. Run the SQL scripts in the `src/main/resources/db` directory to create the necessary tables

### Configuration

1. Update the database configuration in `src/main/resources/application.yml`
2. Update the Redis configuration if needed
3. Update the JWT secret key in the application properties

### Running the Application

```bash
mvn spring-boot:run
```

The application will start on port 8080.

## API Endpoints

### Authentication

- POST `/api/auth/login` - Login
- POST `/api/auth/logout` - Logout

### User Management

- GET `/api/users` - Get all users
- GET `/api/users/{id}` - Get user by ID
- POST `/api/users` - Create user
- PUT `/api/users/{id}` - Update user
- DELETE `/api/users/{id}` - Delete user

### Role Management

- GET `/api/roles` - Get all roles
- GET `/api/roles/{id}` - Get role by ID
- POST `/api/roles` - Create role
- PUT `/api/roles/{id}` - Update role
- DELETE `/api/roles/{id}` - Delete role

### Menu Management

- GET `/api/menus` - Get all menus
- GET `/api/menus/{id}` - Get menu by ID
- POST `/api/menus` - Create menu
- PUT `/api/menus/{id}` - Update menu
- DELETE `/api/menus/{id}` - Delete menu

## Security

The application uses JWT (JSON Web Token) for authentication. To access protected endpoints:

1. Login using the `/api/auth/login` endpoint
2. Include the JWT token in the Authorization header of subsequent requests:
   ```
   Authorization: Bearer <token>
   ```

## License

This project is licensed under the MIT License. 