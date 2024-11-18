# Hexagonal Architecture in Java Tutorial

[![Build](https://github.com/SvenWoltmann/hexagonal-architecture-java/actions/workflows/build.yml/badge.svg)](https://github.com/SvenWoltmann/hexagonal-architecture-java/actions/workflows/build.yml)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_hexagonal-architecture-java&metric=coverage)](https://sonarcloud.io/dashboard?id=SvenWoltmann_hexagonal-architecture-java)
[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_hexagonal-architecture-java&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=SvenWoltmann_hexagonal-architecture-java)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_hexagonal-architecture-java&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=SvenWoltmann_hexagonal-architecture-java)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=SvenWoltmann_hexagonal-architecture-java&metric=security_rating)](https://sonarcloud.io/dashboard?id=SvenWoltmann_hexagonal-architecture-java)

This repository contains a sample Java REST application implemented according to hexagonal architecture.

It is part of the HappyCoders tutorial series on Hexagonal Architecture:
* [Part 1: Hexagonal Architecture - What Is It? Why Should You Use It?](https://www.happycoders.eu/software-craftsmanship/hexagonal-architecture/).
* [Part 2: Hexagonal Architecture with Java - Tutorial](https://www.happycoders.eu/software-craftsmanship/hexagonal-architecture-java/).
* [Part 3: Ports and Adapters Java Tutorial: Adding a Database Adapter](https://www.happycoders.eu/software-craftsmanship/ports-and-adapters-java-tutorial-db/).
* [Part 4: Hexagonal Architecture with Quarkus - Tutorial](https://www.happycoders.eu/software-craftsmanship/hexagonal-architecture-quarkus/).
* [Part 5: Hexagonal Architecture with Spring Boot - Tutorial](https://www.happycoders.eu/software-craftsmanship/hexagonal-architecture-spring-boot/).

# Branches

## `main`

In the `main` branch, you'll find the application implemented without an application framework. It's only using:
* [RESTEasy](https://resteasy.dev/) (implementing [Jakarta RESTful Web Services](https://jakarta.ee/specifications/restful-ws/)),
* [Hibernate](https://hibernate.org/) (implementing [Jakarta Persistence API](https://jakarta.ee/specifications/persistence/)), and
* [Undertow](https://undertow.io/) as a lightweight web server.

## `without-jpa-adapters`

In the `without-jpa-adapters` branch, you'll find the application implemented without an application framework and without JPA adapters. It's only using RESTEasy and Undertow.

## `with-quarkus`

In the `with-quarkus` branch, you'll find an implementation using [Quarkus](https://quarkus.io/) as application framework.

## `with-spring`

In the `with-quarkus` branch, you'll find an implementation using [Spring](https://spring.io/) as application framework.

# Architecture Overview

The source code is separated into four modules:
* `model` - contains the domain model
* `application` - contains the domain services and the ports of the hexagon
* `adapters` - contains the REST, in-memory and JPA adapters
* `boostrap` - contains the configuration and bootstrapping logic

The following diagram shows the hexagonal architecture of the application along with the source code modules:

![Hexagonal Architecture Modules](doc/hexagonal-architecture-modules.png)

The `model` module is not represented as a hexagon because it is not defined by the Hexagonal Architecture. Hexagonal Architecture leaves open what happens inside the application hexagon. 

# How to Run the Application

You can run the application in Quarkus dev mode with the following command:

```shell
mvn test-compile quarkus:dev
```

You can use one of the following VM options to select a persistence mechanism:

* `-Dpersistence=inmemory` to select the in-memory persistence option (default)
* `-Dpersistence=mysql` to select the MySQL option

For example, to run the application in MySQL mode, enter:

```shell
mvn test-compile quarkus:dev -Dpersistence=mysql
```

In dev mode, Quarkus will automatically start a MySQL database using Docker, 
and it will automatically create all database tables.

# Example Curl Commands

The following `curl` commands assume that you have installed `jq`, a tool for pretty-printing JSON strings.

## Find Products

The following queries return one and two results, respectively:

```shell
curl localhost:8080/products/?query=plastic | jq
curl localhost:8080/products/?query=monitor | jq
```

The response of the second query looks like this:
```json
[
  {
    "id": "K3SR7PBX",
    "name": "27-Inch Curved Computer Monitor",
    "price": {
      "currency": "EUR",
      "amount": 159.99
    },
    "itemsInStock": 24081
  },
  {
    "id": "Q3W43CNC",
    "name": "Dual Monitor Desk Mount",
    "price": {
      "currency": "EUR",
      "amount": 119.9
    },
    "itemsInStock": 1079
  }
]
```

## Get a Cart

To show the cart of user 61157 (this cart is empty when you begin):

```shell
curl localhost:8080/carts/61157 | jq
```

The response should look like this:

```json
{
  "lineItems": [],
  "numberOfItems": 0,
  "subTotal": null
}
```

## Adding Products to a Cart

Each of the following commands adds a product to the cart and returns the contents of the cart after the product is added (note that on Windows, you have to replace the single quotes with double quotes):

```shell
curl -X POST 'localhost:8080/carts/61157/line-items?productId=TTKQ8NJZ&quantity=20' | jq
curl -X POST 'localhost:8080/carts/61157/line-items?productId=K3SR7PBX&quantity=2' | jq
curl -X POST 'localhost:8080/carts/61157/line-items?productId=Q3W43CNC&quantity=1' | jq
curl -X POST 'localhost:8080/carts/61157/line-items?productId=WM3BPG3E&quantity=3' | jq
```

After executing two of the four commands, you can see that the cart contains the two products. You also see the total number of items and the sub-total:

```json
{
  "lineItems": [
    {
      "productId": "TTKQ8NJZ",
      "productName": "Plastic Sheeting",
      "price": {
        "currency": "EUR",
        "amount": 42.99
      },
      "quantity": 20
    },
    {
      "productId": "K3SR7PBX",
      "productName": "27-Inch Curved Computer Monitor",
      "price": {
        "currency": "EUR",
        "amount": 159.99
      },
      "quantity": 2
    }
  ],
  "numberOfItems": 22,
  "subTotal": {
    "currency": "EUR",
    "amount": 1179.78
  }
}
```

This will increase the number of plastic sheetings to 40:
```shell
curl -X POST 'localhost:8080/carts/61157/line-items?productId=TTKQ8NJZ&quantity=20' | jq
```

### Producing an Error Message

Trying to add another 20 plastic sheetings will result in error message saying that there are only 55 items in stock:

```shell
curl -X POST 'localhost:8080/carts/61157/line-items?productId=TTKQ8NJZ&quantity=20' | jq
```

This is how the error response looks like:
```json
{
  "httpStatus": 400,
  "errorMessage": "Only 55 items in stock"
}
```

## Emptying the Cart

To empty the cart, send a DELETE command to its URL:

```shell
curl -X DELETE localhost:8080/carts/61157
```

To verify it's empty:
```shell
curl localhost:8080/carts/61157 | jq
```

You'll see an empty cart again.

## <br>Additional Resources

### <br>Java Versions PDF Cheat Sheet

**Stay up-to-date** with the latest Java features with [this PDF Cheat Sheet](https://www.happycoders.eu/java-versions/)!

[<img src="/img/Java_Versions_PDF_Cheat_Sheet_Mockup_936.png" alt="Java Versions PDF Cheat Sheet Mockup" style="width: 468px; max-width: 100%;">](https://www.happycoders.eu/java-versions/)

* Avoid lengthy research with this **concise overview of all Java versions up to Java 23**.
* **Discover the innovative features** of each new Java version, summarized on a single page.
* **Impress your team** with your up-to-date knowledge of the latest Java version.

👉 [Download the Java Versions PDF](https://www.happycoders.eu/java-versions/)<br>

_(Hier geht's zur deutschen Version &rarr; [Java-Versionen PDF](https://www.happycoders.eu/de/java-versionen/))_


### <br>The Big O Cheat Sheet

With this [1-page PDF cheat sheet](https://www.happycoders.eu/big-o-cheat-sheet/), you'll always have the **7 most important complexity classes** at a glance.

[<img src="/img/big-o-cheat-sheet-pdf-en-transp_936.png" alt="Big O PDF Cheat Sheet Mockup" style="width: 468px; max-width: 100%;">](https://www.happycoders.eu/big-o-cheat-sheet/)

* **Always choose the most efficient data structures** and thus increase the performance of your applications.
* **Be prepared for technical interviews** and confidently present your algorithm knowledge.
* **Become a sought-after problem solver** and be known for systematically tackling complex problems.

👉 [Download the Big O Cheat Sheet](https://www.happycoders.eu/big-o-cheat-sheet/)<br>

_(Hier geht's zur deutschen Version &rarr; [O-Notation Cheat Sheet](https://www.happycoders.eu/de/o-notation-cheat-sheet/))_


### <br>HappyCoders Newsletter
👉 Want to level up your Java skills?
Sign up for the [HappyCoders newsletter](http://www.happycoders.eu/newsletter/) and get regular tips on programming, algorithms, and data structures!

_(Hier geht's zur deutschen Version &rarr; [HappyCoders-Newsletter deutsch](https://www.happycoders.eu/de/newsletter/))_


### <br>🇩🇪 An alle Java-Programmierer, die durch fundierte Kenntnisse über Datenstrukturen besseren Code schreiben wollen

Trage dich jetzt auf die [Warteliste](https://www.happycoders.eu/de/mastering-data-structures-warteliste/) von „Mastering Data Structures in Java“ ein, und erhalte das beste Angebot!

[<img src="/img/mastering-data-structures-product-mockup-cropped-1600.png" alt="Mastering Data Structures Mockup" style="width: 640px; max-width: 100%;">](https://www.happycoders.eu/de/mastering-data-structures-warteliste/)

👉 [Zur Warteliste](https://www.happycoders.eu/de/mastering-data-structures-warteliste/)
