# Java Full-Stack Authentication System

A full-stack web application built using **Java 21, Spring Boot, PostgreSQL, JWT, React, and Vite**. The project demonstrates user registration, login, token-based authentication, protected routes, and role-based authorization.

## Features

### Backend
- User registration and login REST APIs
- PostgreSQL database integration
- Password hashing using BCrypt
- JWT-based authentication
- Protected dashboard endpoint
- Role-based access control for admin endpoints
- Request validation and centralized exception handling
- CORS configuration for the frontend
- Automated security and JWT integration tests

### Frontend
- React with Vite and JavaScript
- Login and registration pages
- Protected dashboard
- Axios API integration
- JWT attachment to API requests
- Logout functionality
- Handling of unauthorized API responses

## Tech Stack

| Component | Technology |
|---|---|
| Language | Java 21 |
| Backend | Spring Boot |
| Security | Spring Security, JWT |
| Database | PostgreSQL |
| Build Tool | Maven |
| Frontend | React, Vite, JavaScript |
| API Client | Axios |
| Routing | React Router |
| Testing | JUnit, Spring Boot Test, MockMvc |

## Project Structure

```text
auth-system/
├── src/
│   ├── main/
│   │   ├── java/com/example/auth_system/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dto/auth/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── user/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/example/auth_system/
├── auth-frontend/
│   └── src/
│       ├── pages/
│       ├── services/
│       ├── App.jsx
│       └── main.jsx
├── pom.xml
├── mvnw
├── mvnw.cmd
└── README.md
```

## Prerequisites

Install the following before running the project:

- Java 21
- PostgreSQL
- Node.js and npm
- Git

## Backend Setup

### 1. Create the database

Open PostgreSQL and create a database named `fullstack_auth`, or use the database name configured for your local environment.

```sql
CREATE DATABASE fullstack_auth;
```

If the database already exists, do not create it again.

### 2. Configure environment variables

Configure these variables in your IDE's Spring Boot run configuration or your local environment:

| Variable | Purpose |
|---|---|
| `DB_URL` | PostgreSQL JDBC connection URL |
| `DB_USERNAME` | PostgreSQL username |
| `DB_PASSWORD` | PostgreSQL password |
| `JWT_SECRET_BASE64` | Base64-encoded JWT signing secret |

Use your own local values. **Never commit real passwords or JWT secrets to GitHub.** The JWT secret must decode to at least 32 bytes for this project's HS256 configuration.

Example database URL:

```text
jdbc:postgresql://localhost:5432/fullstack_auth
```

### 3. Run the backend

From the project root, run:

**Windows PowerShell:**

```powershell
.\mvnw.cmd spring-boot:run
```

The backend should start at:

```text
http://localhost:8080
```

The application uses the database configuration in `application.properties`. Hibernate schema auto-update is intended for local development; a production deployment should use a database migration tool.

## Frontend Setup

Open a separate terminal:

```powershell
cd auth-frontend
npm install
```

Create `auth-frontend/.env` with the following local development setting:

```env
VITE_API_URL=http://localhost:8080/api
```

This variable is a public API URL, not a secret. Do not put database credentials or JWT signing secrets in frontend environment variables.

Start the frontend:

```powershell
npm run dev
```

Open the local URL printed by Vite, normally:

```text
http://localhost:5173
```

Restart the Vite development server after changing `.env`.

## API Endpoints

| Method | Endpoint | Access |
|---|---|---|
| POST | `/api/auth/register` | Public |
| POST | `/api/auth/login` | Public |
| GET | `/api/dashboard` | Authenticated |
| GET | `/api/admin/test` | ADMIN role required |

### Authentication Flow

1. A user registers with their name, email, and password.
2. The backend hashes the password before storing it.
3. The user logs in with their credentials.
4. The backend validates the credentials and issues a JWT.
5. The frontend sends the JWT as a Bearer token when calling protected APIs.
6. Spring Security validates the token and checks the user's role where required.
7. Users without a valid token cannot access protected endpoints.

### Authorization Behavior

- Unauthenticated request to the dashboard: `401 Unauthorized`
- Invalid JWT: `401 Unauthorized`
- Authenticated USER accessing the admin endpoint: `403 Forbidden`
- Authorized ADMIN accessing the admin endpoint: `200 OK`

## Running Tests

From the project root:

```powershell
.\mvnw.cmd test
```

The test suite includes security authorization tests and JWT integration tests.

## Security Notes

- Passwords are stored as BCrypt hashes, not plaintext.
- Database credentials and JWT signing secrets should be supplied through environment variables.
- Never commit `.env` files containing secrets.
- The frontend's client-side route protection is for user experience; backend authorization remains the actual security boundary.
- This is a learning project. Before production use, review token storage, HTTPS, XSS protections, database migrations, secret management, and deployment configuration.

## Current Status

The project includes registration, login, JWT authentication, protected API endpoints, role-based authorization, a React frontend, logout handling, and automated backend tests.

## Future Improvements

- Refresh-token flow and improved token lifecycle management
- Database migrations using Flyway
- More frontend and backend tests
- Deployment with HTTPS
- CI workflow for automated builds and tests

## Author

Developed as a Java Full-Stack learning project.
