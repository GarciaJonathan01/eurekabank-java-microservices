# EurekaBank Java Microservices

Java SOAP and RESTful variants of EurekaBank with the backend split into focused banking services.

## Overview

This version decomposes the banking backend into authentication, accounts, operations, and transfer services while retaining console, desktop, web, and Android clients.

## Architectures

- Java SOAP microservices
- Java RESTful microservices

## Applications

- Console clients
- Java desktop clients
- JSP/Servlet web clients
- Android/Kotlin mobile clients
- SOAP and REST backends split into authentication, accounts, operations, transfer, and compatibility services

## Technologies

- Java, Maven, Jakarta EE, JAX-WS and JAX-RS
- Kotlin, Android and Gradle
- JSP and Servlets
- MySQL and SQL scripts

## Project Structure

- `01.SOAP_JAVA_EUREKABANK_GR05/`
- `03.RESTFUL_JAVA_EUREKABANK_GR05/`

Each architecture contains its clients and a server directory with the individual service modules.

## Features

- Authentication service
- Account and balance service
- Deposit and withdrawal operations
- Transfer service
- Movement queries
- Clients demonstrating both SOAP and REST integrations

## Configuration

Database services read `DB_URL`, `DB_USER`, and `DB_PASSWORD` from the environment. Use `.env.example` only as a safe reference for required variable names.

## Running the Project

Provision the database using the included SQL scripts. Build and start the required service modules from their `pom.xml` directories, then configure and run the desired client. Android clients should be opened using Android Studio and their included Gradle projects.

## Academic Context

This group academic project explores microservice decomposition and compares SOAP with RESTful communication in a banking domain.
