# Order Management System API

## Kühne+Nagel Back-End Java Engineer Internship Assessment
This project uses Spring Boot, Spring Data JPA, Hibernate, Liquibase, Lombok, PostgreSQL, and Docker.

Check `build.gradle` for more specific info.

## Running the Application

### Database

You are required to use **Docker** for the database to run.

Creating and running the container with the right configuration is done by running this command in your preferred CLI.

```bash
docker run --name kn_order_management_system_api -e POSTGRES_PASSWORD=mysecretpassword -p 5432:5432 -d postgres
```

If you wish for whatever reason to drop all data and return to the initial data, 
uncomment `spring.liquibase.drop-first=true` in `application.properties` in the resources folder and run the application. 

You might want to comment that line back out if you want to keep any data saved in there.

### Unit Tests

Running unit tests can be done by running this command.

```bash
./gradlew test
```

or if for whatever reason you would like to re-run the tests after they are passed, run the following command.

```bash
./gradlew clean test
```

### Application

To run the application itself, you run the following command.

```bash
./gradlew bootRun
```

---

# Endpoints

## Customer

### GET: /api/customers

Returns list of all customers.

Example of response:

```json
[
  {
    "id": 1,
    "fullName": "Hardy Baldrey",
    "email": "hbaldrey0@earthlink.net",
    "telephone": "967-875-6011",
    "registrationCode": "xhruZ8e2aiE3C5xaSGZjFGFt8xm",
    "orders": []
  },
  {
    "id": 2,
    "fullName": "Adolphe Brundale",
    "email": "abrundale1@miibeian.gov.cn",
    "telephone": "537-690-4697",
    "registrationCode": "1xwuEc5Gx0d3UqIy70pybNR8TYW",
    "orders": [
      {
        "id": 14,
        "customerId": 2,
        "submissionDate": "2021-06-07T21:00:00.000+00:00",
        "orderLines": [
          {
            "id": 34,
            "orderId": 14,
            "product": {

              "id": 1,
              "name": "Cake - Cake Sheet Macaroon",
              "skuCode": "544201160321",
              "unitPrice": 15
            },
            "quantity": 3
          },
          {
            "id": 14,
            "orderId": 14,
            "product": {
              "id": 5,
              "name": "Sugar - Sweet N Low, Individual",
              "skuCode": "843591714405",
              "unitPrice": 1
            },
            "quantity": 1
          }
        ]
      }
    ]
```

### GET: /api/customers/{id}

Returns specific customer by ID.

Example of response:

```json
{
  "id": 1,
  "fullName": "Hardy Baldrey",
  "email": "hbaldrey0@earthlink.net",
  "telephone": "967-875-6011",
  "registrationCode": "xhruZ8e2aiE3C5xaSGZjFGFt8xm",
  "orders": []
}
```

### POST: /api/customers

Creates customer. Also redirects to **/api/customers/{id}**.

Example of request:

```json
{
  "fullName": "Hardy Baldrey",
  "email": "hbaldrey0@earthlink.net",
  "telephone": "967-875-6011",
  "registrationCode": "xhruZ8e2aiE3C5xaSGZjFGFt8xm"
}
```

Expected response:

```json
{
  "id": 11,
  "fullName": "Hardy Baldrey",
  "email": "hbaldrey0@earthlink.net",
  "telephone": "967-875-6011",
  "registrationCode": "xhruZ8e2aiE3C5xaSGZjFGFt8xm",
  "orders": []
}
```
---

## Product

### GET: /api/products

Returns list of all products.

Example of response:

```json
[
  {
    "id": 1,
    "name": "Cake - Cake Sheet Macaroon",
    "skuCode": "544201160321",
    "unitPrice": 15
  },
  {
    "id": 2,
    "name": "Mayonnaise",
    "skuCode": "719548945490",
    "unitPrice": 3
  }
]
```

### GET: /api/products/{id}

Returns specific product by ID.

Example of response:

```json
{
  "id": 1,
  "name": "Cake - Cake Sheet Macaroon",
  "skuCode": "544201160321",
  "unitPrice": 15
}
```

### POST: /api/products

Creates product. Also redirects to **/api/products/{id}**.

Example of request:

```json
{
  "name": "Cake - Cake Sheet Macaroon",
  "skuCode": "544201160321",
  "unitPrice": 15
}
```

Expected response:

```json
{
  "id": 35,
  "name": "Cake - Cake Sheet Macaroon",
  "skuCode": "544201160321",
  "unitPrice": 15
}
```

### PUT: /api/products

Updates product. Also redirects to **/api/products/{id}**.

Example of request:

```json
{
  "id": 35,
  "name": "Cake - Cake Sheet Macaroon",
  "skuCode": "544201160321",
  "unitPrice": 12
}
```

Expected response:

```json
{
  "id": 35,
  "name": "Cake - Cake Sheet Macaroon",
  "skuCode": "544201160321",
  "unitPrice": 12
}
```
---

## Order

For the sake of readability and keeping the document short, only the first, PUT, and POST examples will contain order
lines.

### GET: /api/orders

Returns list of all orders.

Example of response:

```json
[
  {
    "id": 1,
    "customerId": 5,
    "submissionDate": "2021-04-03T21:00:00.000+00:00",
    "orderLines": [
      {
        "id": 1,
        "orderId": 1,
        "product": {
          "id": 3,
          "name": "Limes",
          "skuCode": "644965877982",
          "unitPrice": 0.5
        },
        "quantity": 3
      },
      {
        "id": 21,
        "orderId": 1,
        "product": {
          "id": 1,
          "name": "Cake - Cake Sheet Macaroon",
          "skuCode": "544201160321",
          "unitPrice": 15
        },
        "quantity": 9
      }
    ]
  },
  {
    "id": 2,
    "customerId": 3,
    "submissionDate": "2020-11-28T22:00:00.000+00:00",
    "orderLines": [
      {
        "id": 2,
        "orderId": 2,
        "product": {
          "id": 3,
          "name": "Limes",
          "skuCode": "644965877982",
          "unitPrice": 0.5
        },
        "quantity": 10
      },
      {
        "id": 22,
        "orderId": 2,
        "product": {
          "id": 4,
          "name": "Pepper - Jalapeno",
          "skuCode": "167965933260",
          "unitPrice": 1
        },
        "quantity": 4
      }
    ]
  }
]
```

### GET: /api/orders/{id}

Returns specific order by ID.

Example of response:

```json
{
  "id": 1,
  "customerId": 5,
  "submissionDate": "2021-04-03T21:00:00.000+00:00",
  "orderLines": [
    ...
  ]
}
```

### GET: /api/orders/product/{id}

Returns list of orders that contain a specific product using product's ID.

In an example below, the product ID was 5.

Example of response:

```json
[
  {
    "id": 19,
    "customerId": 6,
    "submissionDate": "2021-08-20T21:00:00.000+00:00",
    "orderLines": [
      ...
    ]
  },
  {
    "id": 17,
    "customerId": 6,
    "submissionDate": "2020-12-30T22:00:00.000+00:00",
    "orderLines": [
      ...
    ]
  }
]
```

### GET: /api/orders/customer/{id}

Returns list of orders of specific customer using customer's ID.

Example response:

```json
[
  {
    "id": 14,
    "customerId": 2,
    "submissionDate": "2021-06-07T21:00:00.000+00:00",
    "orderLines": [
      ...
    ]
  },
  {
    "id": 18,
    "customerId": 2,
    "submissionDate": "2021-05-02T21:00:00.000+00:00",
    "orderLines": [
      ...
    ]
  }
]
```

### GET: /api/orders/date/{date}

Returns list of orders placed on a specific date.

Date format is the following: **yyyy-MM-dd**.

For example: **2021-04-04**.

Example of response:

```json
[
  {
    "id": 1,
    "customerId": 5,
    "submissionDate": "2021-04-03T21:00:00.000+00:00",
    "orderLines": [
      {
        "id": 1,
        "orderId": 1,
        "product": {
          "id": 3,
          "name": "Limes",
          "skuCode": "644965877982",
          "unitPrice": 0.5
        },
        "quantity": 3
      },
      {
        "id": 21,
        "orderId": 1,
        "product": {
          "id": 1,
          "name": "Cake - Cake Sheet Macaroon",
          "skuCode": "544201160321",
          "unitPrice": 15
        },
        "quantity": 9
      }
    ]
  }
]
```

Notice, the **submissionDate** field being in GMT+0. For requests, convert date to **Easter European Time**, more
specifically, the **Tallinn time**.

### POST: /api/orders

Creates an order.

Example of request:

```json
{
  "customerId": 5,
  "submissionDate": "2021-04-04",
  "orderLines": [
    {
      "product": {
        "id": 3,
        "name": "Limes",
        "skuCode": "644965877982",
        "unitPrice": 0.5
      },
      "quantity": 3
    },
    {
      "product": {
        "id": 1,
        "name": "Cake - Cake Sheet Macaroon",
        "skuCode": "544201160321",
        "unitPrice": 15
      },
      "quantity": 9
    }
  ]
}
```

Expected response:

```json
{
  "id": 1,
  "customerId": 5,
  "submissionDate": "2021-04-04T00:00:00.000+00:00",
  "orderLines": [
    {
      "id": 1,
      "orderId": 1,
      "product": {
        "id": 3,
        "name": "Limes",
        "skuCode": "644965877982",
        "unitPrice": 0.5
      },
      "quantity": 3
    },
    {
      "id": 21,
      "orderId": 1,
      "product": {
        "id": 1,
        "name": "Cake - Cake Sheet Macaroon",
        "skuCode": "544201160321",
        "unitPrice": 15
      },
      "quantity": 9
    }
  ]
}
```

Notice, that the response time is in **Tallinn time** here.

### PUT: /api/orders

Updates an order.

Example of request:

```json
{
  "customerId": 5,
  "submissionDate": "2021-04-04",
  "orderLines": [
    {
      "product": {
        "id": 3,
        "name": "Limes",
        "skuCode": "644965877982",
        "unitPrice": 0.5
      },
      "quantity": 5
    }
  ]
}
```

Expected response:

```json
{
  "id": 1,
  "customerId": 5,
  "submissionDate": "2021-04-04T00:00:00.000+00:00",
  "orderLines": [
    {
      "id": 1,
      "orderId": 1,
      "product": {
        "id": 3,
        "name": "Limes",
        "skuCode": "644965877982",
        "unitPrice": 0.5
      },
      "quantity": 3
    }
  ]
}
```