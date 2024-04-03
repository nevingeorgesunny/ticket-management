# Train Ticket Booking System

## Overview

The Train Ticket Booking System is a Spring Boot-based RESTful service designed to facilitate the purchasing and management of train tickets for a journey from London to France. The application supports seat allocation within two sections of the train (Section A and B), each containing 10 seats, allowing users to book, view, modify, and cancel their tickets.

## How to start the Applications

1. This application requires ```Java 17```
2. run ``` ./gradlew clean build```
3. to start the app  ``` ./gradlew bootRun```


## Getting Started

To use the API, ensure your system can make HTTP requests to `localhost:8080`. The following sections describe the available endpoints and how to interact with them.

### fetch-all-section-ticket

This API will return all the  seats for a particular section , be it available or not

```bash
curl --location 'localhost:8080/api/v1/fetch/A'
```

this will return something like

```json
    {
      "data":
      {
        "seats":
        [
          {
            "seatId": 2,
            "section": "A",
            "ticketId": "38f5568b-59e6-4a69-9b2b-840f4b3d907c",
            "userInfo":
            {
              "userId": "595b44b1-6976-430b-ab63-e8d918b3908f",
              "firstName": "B",
              "lastName": "C",
              "email": "nevin@com.com"
            },
            "isTaken": true
          },
          {
            "seatId": 4,
            "section": "A",
            "isTaken": false
          }
        ]
      },
      "code": "OK"
    }
```
1. `isTaken` states if the seat is taken or not
2. `isTaken` if its true it will retunr the user who has booked it and the corresponding ticket id



###  book-ticket

This will book the ticket for 
1. For a new user
```bash
curl --location 'localhost:8080/api/v1/book-ticket' \
--header 'Content-Type: application/json' \
--data-raw '{
   
    "pricePaid":123,
    "from":"London",
    "to":"France",
    "userInfo":{
        "firstName":"B",
        "lastName":"C",
        "email":"nevin@com.com"
    }
}'

```
2. An existing user


```bash
curl --location 'localhost:8080/api/v1/book-ticket' \
--header 'Content-Type: application/json' \
--data '{
    "userId":"bb5648f7-4bde-4170-8395-19e88e518ee4",
    "pricePaid":123,
    "from":"London",
    "to":"France"
}'
```

this will return something like

```json
    {
      "data": {
        "userId": "c06039f7-85fc-41e5-b132-37c7cd65e330",
        "from": "London",
        "to": "France",
        "pricePaid": 123,
        "ticketId": "dae141f6-d73d-4191-b6d8-55657f8ec0ed",
        "section": "A",
        "seatNumber": 1
      },
      "code": "OK"
    }
```
1. `userId` specifies the userId under which the ticket is created
2. `ticketId` id of the ticket
3. `section` is the section of the seat
4. `seatNumber` is the seatnumber of the seat



###  user-tickets

Fetches all the ticket fo the user

```bash
curl --location 'localhost:8080/api/v1/ticket/c06039f7-85fc-41e5-b132-37c7cd65e330'

```
this will return something like

```json
    {
      "data": {
        "userId": "c06039f7-85fc-41e5-b132-37c7cd65e330",
        "ticketList": [
          {
            "ticketId": "dae141f6-d73d-4191-b6d8-55657f8ec0ed",
            "section": "A",
            "seatNumber": 1
          }
        ]
      },
      "code": "OK"
    }
```



###  update-ticket-value

API to update the seat info or to disable the ticket 


```bash
curl --location --request PUT 'localhost:8080/api/v1/ticket/ddb91223-481d-4230-a05a-5b2b5cf0f35e' \
--header 'Content-Type: application/json' \
--data '{
    "userId":"c06039f7-85fc-41e5-b132-37c7cd65e330",
    "ticketId":"dae141f6-d73d-4191-b6d8-55657f8ec0ed",
    "isEnabled":true,
    "newSection":"A",
    "newSeatNumber":3
}'
```

this will return something like

```json
    {
      "data": true,
      "code": "OK"
    }
```

###  delete-user-ticket

This will delete all the tickets of the user 


```bash
curl --location --request DELETE 'localhost:8080/api/v1/ticket/c06039f7-85fc-41e5-b132-37c7cd65e330' \
--data ''

```

this will return something like

```json
    {
  "data": true,
  "code": "OK"
}
```


# Database Schema for Ticket Booking System

This document outlines the database schema for a ticket booking system. The system consists of three main entities: `UserEntity`, `TicketEntity`, and `SeatEntity`. These entities are mapped to the PostgreSQL database.

#### ** All data is stored in H2  DB so every time the server resetats the data will be lost

## Entities

### UserEntity

Represents a user in the system.

- **Table Name:** `ticket_user`
- **Schema:**

| Column Name | Type     | Nullable | Description           |
|-------------|----------|----------|-----------------------|
| id          | UUID     | No       | Primary Key, Unique ID for the user. |
| first_name  | VARCHAR  | Yes      | User's first name.    |
| last_name   | VARCHAR  | Yes      | User's last name.     |
| email       | VARCHAR  | Yes      | User's email address. |

### TicketEntity

Represents a ticket purchased by a user.

- **Table Name:** `ticket`
- **Schema:**

| Column Name   | Type     | Nullable | Description                                         |
|---------------|----------|----------|-----------------------------------------------------|
| id            | UUID     | No       | Primary Key, Unique ID for the ticket.              |
| user_id       | UUID     | No       | Foreign Key, Links to the `UserEntity` table.       |
| from_location | VARCHAR  | No       | Departure location of the journey.                  |
| to_location   | VARCHAR  | No       | Destination location of the journey.                |
| price_paid    | INTEGER  | No       | The price paid for the ticket.                      |
| seat_id       | BIGINT   | No       | Foreign Key, Links to the `SeatEntity` table.       |
| is_enabled    | BOOLEAN  | No       | Indicates if the ticket is active or canceled.      |

### SeatEntity

Represents a seat allocation for a ticket.

- **Table Name:** `seat`
- **Schema:**

| Column Name | Type     | Nullable | Description                                   |
|-------------|----------|----------|-----------------------------------------------|
| id          | BIGINT   | No       | Primary Key, Unique ID for the seat.          |
| section     | VARCHAR  | No       | The section of the train the seat is in (A/B).|
| seat_number | INTEGER  | No       | The number of the seat within the section.    |
| is_taken    | BOOLEAN  | No       | Indicates if the seat is taken.               |

## Relationships

- Each `TicketEntity` is associated with one `UserEntity` through the `user_id`.
- Each `TicketEntity` is also associated with one `SeatEntity` through the `seat_id`.
- The `SeatEntity` and `TicketEntity` have a one-to-one relationship, where each seat is linked to a specific ticket once allocated.
- The `UserEntity` can have multiple `TicketEntity` records, reflecting multiple ticket purchases.

This schema supports the management of users, their ticket purchases, and the allocation of seats for those tickets within the system.

## To view the DB

1. go to ```http://localhost:8080/h2-console```
2. user JDBC url as ```jdbc:h2:mem:nevin;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE```
3. userName ```sa```
4. password ```password```
