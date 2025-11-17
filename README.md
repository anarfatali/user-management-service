# User Management Service

A Spring Boot microservice for managing users with Kafka event-driven messaging and PostgreSQL database.

---

## Setup & Run Instructions

### Prerequisites
- Docker & Docker Compose installed
- Java 21 (for local development)
- Gradle (for building locally)

### Run with Docker Compose

1. Clone the repository:

```bash
git clone <https://github.com/anarfatali/user-management-service.git>
cd <user-management-service>
````

2. Build and start the services:

```bash
docker compose up --build
```

This will start:

* PostgreSQL on port `5432`
* Kafka + Zookeeper on ports `9092` (host) and `29092` (internal)
* User Management Service on port `8080`

3. To stop services:

```bash
docker compose down
```

### Environment Variables

```text
SPRING_DATASOURCE_URL=jdbc:postgresql://postgres_user_management_service:5432/user-management-service
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=1234

KAFKA_BOOTSTRAP_SERVERS=kafka:29092
KAFKA_CONSUMER_GROUP=user-management-service-group
KAFKA_TOPIC_USER_CREATED=user.created
KAFKA_TOPIC_USER_UPDATED=user.updated
KAFKA_TOPIC_USER_DELETED=user.deleted

LOG_LEVEL=DEBUG
KAFKA_LOG_LEVEL=WARN
```

---

## Example API Calls

### Create User

```bash
# CREATE user (returns 201 Created)
curl -i -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Anar Fətəliyev",
    "email": "anar@example.com",
    "phone": "+994501231234"
  }'
# Expected: HTTP/1.1 201 Created
# Note: Producer will send user.created event after DB commit.
```

### Get All Users

```bash
# GET all users (pageable + sort + example filter)
# page (0-based), size, sort=name,desc and filtering by name
curl -i -X GET "http://localhost:8080/api/v1/users?page=0&size=20&sort=name,desc&name=Anar"
# Expected: HTTP/1.1 200 OK
# Response: JSON page with content, totalElements, totalPages, etc.
```


### Get User by ID

```bash
# GET user by id
curl -i -X GET http://localhost:8080/api/v1/users/1
# Expected: HTTP/1.1 200 OK
# Response: single UserResponse JSON
```

### Update User

```bash
# UPDATE user (returns 200 OK)
curl -i -X PUT http://localhost:8080/api/v1/users/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "AnarFatali",
    "email": "anar.new@example.com",
    "phone": "501239999"
  }'
# Expected: HTTP/1.1 200 OK
# Note: user.updated event should be emitted after commit.
```

### Delete User

```bash
# DELETE user (returns 204 No Content)
curl -i -X DELETE http://localhost:8080/api/v1/users/1
# Expected: HTTP/1.1 204 No Content
# Note: user.deleted event should be emitted after commit.
```

---

## Kafka Events

The service produces events for each user operation:

| Event Type   | Topic Name     |
| ------------ | -------------- |
| User Created | `user.created` |
| User Updated | `user.updated` |
| User Deleted | `user.deleted` |


---

## Deployed Base URL

```text
https://anarfatali.alakx.com/swagger-ui/index.html or
use https://anarfatali.alakx.com/api/v1/users for http requests
```

---

## Logging

* Application logs can be viewed in the container logs:

```bash
docker compose logs -f app
```

* Kafka logs:

```bash
docker compose logs -f kafka
```

---