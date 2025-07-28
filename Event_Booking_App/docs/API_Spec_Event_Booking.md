# Event Booking App API docs #

## Auth API ##
### 1. **Registration** ### 
- Endpoint: **POST /api/auth/register**
- Method: POST
- Auth: No
- Request Body:

```
{
    "fullName": "Jane Doe",
    "email": "jane@example.com",
    "password": "password123"
}
```

- Response 200:

```
{
    "success": true,
    "message": "Registration successful",
    "data": {
        "userId": 1,
        "fullName": "Jane Doe",
        "email": "jane@example.com"
    }
}
```

- Validation Error 422:

```
{
    "success": false,
    "message": "Validation failed",
    "errors": [
        {"field": "fullName", "message": "Full name is required" },
        {"field": "email", "message": "Invalid email format" },
        {"field": "password", "message": "Password must be at least 8 characters and contain letters and numbers" }
    ]
}
```

- Note:
    - Email format must be valid.
    - Password must be at least 8 characters, contains letters and numbers.

### 2. **Login** ###
- Endpoint: **POST /api/auth/login**
- Method: POST
- Auth: No
- Request Body:

```
{
    "email": "user@example.com",
    "password": "password123"
}
```

- Response 200:

```
{
    "success": true,
    "message": "Login successful",
    "data":{
        "accessToken": "eyJhbGciOiJIUzI1NilsInR5cCI6IkpXVCJ9...",
        "expire": "2025-07-31T23:59:59Z"
    }
}
```

- Validation Error 422:

```
{
    "success": false,
    "message": "Validation failed",
    "errors": [
        {"field": "email", "message": "Invalid email format" },
        {"field": "password", "message": "Password is requred" }
    ]
}
```

- Response - 401 Unauthorized

```
{
    "success": false,
    "message": "Invalid email or password"
}
```

- Note:
    - Email format must be valid.

## Events ##

### 1. **Create events** ###
- Endpoint: **POST /api/events**
- Method: POST
- Auth: Bearer Token, ADMIN permission
- Request Body:

```
{
    "title": "Art Exhibition",
    "dateTime": "2027-05-12T10:00:00",
    "location": "Modern Art Gallery, New York",
    "price": 25.00,
    "description": "Lorem ipsum dolor sit amet...",
    "imageUrl": "https://cdn.example.com/events/art_exhibition.jpg"
}
```

- Response 201:

```
{
    "success": true,
    "message": "Event created successfully",
    "data": {
        "id": 123,
        "title": "Art Exhibition",
        "dateTime": "2025-05-12T10:00:00",
        "location": "Modern Art Gallery, New York",
        "price": 25.00,
        "description": "Lorem ipsum dolor sit amet...",
        "imageUrl": "https://cdn.example.com/events/art_exhibition.jpg"
    }
}
```

- Validation Error 422:

```
{
    "success": false,
    "message": "Validation failed",
    "errors": [
        {"field": "title", "message": "Title is required" },
        {"field": "price", "message": "Price must be >= 0"}
    ]
}
```

- Note:
    - Date & Time must be in the future.
    - Price must be >=0.
    - Description must be less than 1000 characters

### 2. **Update an event** ###
- Endpoint: **PUT /api/events/{id}**
- Method: PUT
- Auth: Bearer Token, ADMIN permission
- Request Body:

```
{
    "title": "Art Exhibition (Updated)",
    "dateTime": "2027-05-12T11:00:00",
    "location": "Modern Art Gallery, New York",
    "price": 30.00,
    "description": "Updated description",
    "imageUrl": "https://cdn.example.com/events/art_exhibition.jpg"
}
```

- Response 200:

```
{
    "success": true,
    "message": "Event update successfully",
    "data": {
        "id": 123,
        "title": "Art Exhibition (Updated)",
        "dateTime": "2027-05-12T11:00:00",
        "location": "Modern Art Gallery, New York",
        "price": 30.00,
        "description": "Updated description",
        "imageUrl": "https://cdn.example.com/events/art_exhibition.jpg"
    }
}
```

- Validation Error 422:

```
{
    "success": false,
    "message": "Validation failed",
    "errors": [
        {"field": "dateTime", "message": "Date & Time must be in the future." }
    ]
}
```

- Response - Not Found - 404

```
{
    "success": false,
    "message": "Event not found"
}
```

- Note:
    - Event must be existed.

### 3. **Delete an event** ###
- Endpoint: **DELETE /api/events/{id}**
- Method: DELETE
- Auth: Bearer Token, ADMIN permission

- Response 204 No Content

- Response - Not Found - 404

```
{
    "success": false,
    "message": "Event not found"
}
```

- Note:
    - Event must be existed.

### 4. **Get events list** ###
- Endpoint: **GET /api/events**
- Method: GET
- Auth: USER permission

- Response 200:

```
{
    "success": true,
    "message": "Events fetched successfully",
    "data": [
        {
        "id": 101,
        "title": "Art Exhibition",
        "dateTime": "2025-05-12T10:00:00",
        "location": "Modern Art Gallery, New York",
        "price": 25.00,
        "description": "Lorem ipsum dolor sit amet...",
        "imageUrl": "https://cdn.example.com/events/art_exhibition.jpg"
        }
    ]
}
```

- Validation Error 422:

```
{
    "success": false,
    "message": "Validation failed",
    "errors": [
        {"field": "type", "message": "Invalid type value" },
        {"field": "page", "message": "Page must be a positive integer"},
        {"field": "size", "message": "Size must be a positive integer"}
    ]
}
```

### 5. **Get an event details** ###
- Endpoint: **GET /api/events/{id}**
- Method: GET
- Auth: Bearer Token

- Response 200:

```
{
    "success": true,
    "message": "Event detail fetched successfully",
    "data": {
        "id": 101,
        "title": "Art Exhibition",
        "dateTime": "2025-05-12T10:00:00",
        "location": "Modern Art Gallery, New York",
        "price": 25.00,
        "description": "Lorem ipsum dolor sit amet...",
        "imageUrl": "https://cdn.example.com/events/art_exhibition.jpg"
    }
}
```

- Response - Not Found 404

```
{
    "success": false,
    "message": "Event not found"
}
```

## Bookings ##
- Endpoint: **POST /api/bookings**
- Method: POST
- Auth: Bearer Token
- Request Body:

```
{
    "eventId:" 101,
    "quantity:" 2
}
```

- Response 201 - Created:

```
{
    "success": true,
    "message": "Booking successful",
    "data": {
        "bookingId": 501,
        "event": {
            "id": 101,
            "title": "Art Exhibition",
            "dateTime": "2025-05-12T10:00:00",
            "location": "Modern Art Gallery, New York"
            "quantity": 2,
            "totalPrice": 50.00
        }
    }
}
```

- Validation Error 422:

```
{
    "success": false,
    "message": "Validation failed",
    "errors": [
        {"field": "eventId", "message": "Event not found" },
        {"field": "quantity", "message": "Quantity must be at least 1" }
    ]
}
```

- Note:
    - Event must be existed.
    - Quantity must be at least 1.

## Tickets ##
- Endpoint: **GET /api/tickets**
- Method: GET
- Auth: Bearer Token

- Response 200:

```
{
    "success": true,
    "message": "Tickets fetched successfully",
    "data": [
        {
            "ticketId": 1001,
            "event": {
                "id": 101,
                "title": "Art Exhibition",
                "dateTime": "2025-05-12T10:00:00",
                "location": "Modern Art Gallery, New York"
            },
            "quantity": 1,
            "status": "PAID"
        }
    ]
}
```

- Response - Unauthorized 401

```
{
    "success": false,
    "messaage": "Unauthorized - Please login to access this resource"
}
```

- Note:
    - Must log-in to use this API

## Payments ##
- Endpoint: **POST /api/payments**
- Method: POST
- Auth: Bearer Token
- Request Body:

```
{
    "bookingId": 2,
    "cardNumber": "1234 5678 9012 3456",
    "expiry": "12/25",
    "cvv": "123"
}
```

- Response 200:

```
{
    "success": true,
    "message": "Payment successful",
    "data": {
        "paymentId": 1001,
        "bookingId": 501,
        "amount": 50.00,
        "status": "PAID"
    }
}
```

- Validation Error 422:

```
{
    "success": false,
    "message": "Validation failed",
    "errors": [
        {"field": "bookingId", "message": "Booking not found or already paid" },
        {"field": "cardNumber", "message": "Invalid card number" },
        {"field": "expiry", "message": "Invalid expiry format" },
        {"field": "cvv", "message": "Invalid CVV" }
    ]
}
```

- Note:
    - Booking must be existed, not been paid.
    - Card number must follow the format.
    - CVV must follow the format.
    - Expiry date must be valid.

## Update Reminders ##
- Endpoint: **PUT /api/users/reminders**
- Method: PUT
- Auth: Bearer Token
- Request Body:

```
{
    "eventReminder": true
}
```

- Response 200:

```
{
    "success": true,
    "message": "Reminder settings updated successfully",
    "data": {
        "eventReminder": true,
    }
}
```

- Validation Error 422:

```
{
    "success": false,
    "message": "Validation failed",
    "errors": [
        {"field": "eventReminder", "message": "Must be true or false" }
    ]
}
```

## Update Profile ##
- Endpoint: **PUT /api/users/reminders**
- Method: PUT
- Auth: Bearer Token
- Request Body:

```
{
    "fullName": "Jane Doe",
    "avatar": "https://cdn.example.com/avatars/user123.jpg"
}
```

- Response 200:

```
{
    "success": true,
    "message": "Profile updated successfully",
    "data": {
        "userId": 1,
        "fullName": "Jane Doe",
        "email": "jane@example.com",
        "avatar": "https://cdn.example.com/avatars/user123.jpg"
    }
}
```

- Validation Error 422:

```
{
    "success": false,
    "message": "Validation failed",
    "errors": [
        {"field": "fullName", "message": "Full name is required" },
        {"field": "avatar", "message": "Invalid avatar URL" }
    ]
}
```

- Note
    - Email format must be valid.
