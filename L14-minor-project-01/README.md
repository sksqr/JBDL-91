# L13 Minor Project 01

## Overview
This project is a Spring Boot based backend for a simple multi-role e-commerce/order system.
It supports:
- Admin operations (company, seller, category management)
- Seller operations (product creation)
- Customer operations (product search, cart/order item add, order view, order submit)

Core business entities:
- User (ADMIN, SELLER, CUSTOMER)
- Company
- Category
- Product
- Order
- OrderItem

## Tech Stack
- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA (Hibernate)
- MySQL (runtime driver)
- Maven (build)
- Lombok
- Jakarta Validation

## Project Structure
- `controller` : REST API endpoints (`AdminController`, `SellerController`, `CustomerController`)
- `service` : business logic (`AdminService`, `SellerService`, `CustomerService`)
- `repo` : JPA repositories
- `entity` : JPA entities and enums
- `dto` : API request/response DTOs

## API Request Flow

### 1. Admin APIs (`/api/admin`)
- `POST /company`
  - Creates company + primary user
  - Sets `company.user_id` as primary user
- `POST /seller`
  - Creates seller user for an existing company
- `POST /category`
  - Creates category
- `GET /sellers`
  - Returns all users with `SELLER` role

Flow:
`Controller -> Service -> Repository -> DB -> Service DTO mapping -> Controller response`

### 2. Seller APIs (`/api/seller`)
- `POST /product`
  - Validates company and category
  - Creates product mapped to company + category

Flow:
`SellerController -> SellerService -> ProductRepository (+ Company/Category lookup) -> DB`

### 3. Customer APIs (`/api/customer`)
- `GET /products?keyword=...`
  - Searches products by keyword in `name` or `description`
- `POST /order-item`
  - Finds user's latest `DRAFT` order
  - If not present, creates new `DRAFT` order
  - Adds or updates order item quantity
  - Recalculates order total
- `GET /order/{id}`
  - Fetches full order details including all items and total
- `PUT /order/{id}/submit`
  - Changes order status from `DRAFT` to `PLACED`

Flow:
`CustomerController -> CustomerService -> Order/Product/User repositories -> DB -> DTO response`

## Order Lifecycle
- `DRAFT` -> `PLACED` -> `ACCEPTED` -> `SHIPPED` -> `OFD`

Current implementation covers transition:
- `DRAFT -> PLACED` via `PUT /api/customer/order/{id}/submit`

## How to Run
1. Configure DB in `src/main/resources/application.properties`
2. Build:
   ```bash
   ./mvnw clean compile
   ```
3. Start:
   ```bash
   ./mvnw spring-boot:run
   ```
