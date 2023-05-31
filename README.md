# Hexagonal Architecture in Java Tutorial

This repository contains a sample Java REST application implemented according to hexagonal architecture.

It is part of the HappyCoders tutorial series [Hexagonal Architecture - What Is It? Why Should You Use It?](https://www.happycoders.eu/software-craftsmanship/hexagonal-architecture/).

In the main branch, you'll find the application implemented without an application framework. It's only using:
* [RESTEasy](https://resteasy.dev/) (implementing [Jakarta RESTful Web Services](https://jakarta.ee/specifications/restful-ws/)),
* [Hibernate](https://hibernate.org/) (implementing [Jakarta Persistence API](https://jakarta.ee/specifications/persistence/)), and
* [Undertow](https://undertow.io/) as a lightweight web server.

There will soon be two additional branches with implementations using [Spring](https://spring.io/) and [Quarkus](https://quarkus.io/) as application frameworks.

# Architecture Overview

The source code is separated into four modules:
* `model` - contains the domain model
* `application` - contains the domain services and the ports of the hexagon
* `adapters` - contains the REST, in-memory and JPA adapters
* `boostrap` - contains the configuration and bootstrapping logic

The following diagram shows the hexagonal architecture of the application along with the source code modules:

![Hexagonal Architecture Modules](doc/hexagonal-architecture-modules.png)

The `model` module is not represented as a hexagon because it is not defined by the Hexagonal Architecture. Hexagonal Architecture leaves open what happens inside the application hexagon. 
