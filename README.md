# testcontainers-workshop

<p align="middle">
    <img src="logo.svg" height="180"/>
    <img src="testcontainers-logo.svg" height="180"/>
</p>    

## "Holiday Planning Tool"

During this workshop we will work with the fictional Holiday Planning Tool.
This is a web application that can be used to schedule your time off work with your colleagues.

Because we all deserve some time off, don't we?

<img src="holiday.jpg" width="500" />

## High level architecture
The architecture of the Holiday Planning Tool, or HPT, is as follows:


```mermaid
graph TD;
    HPT_UI-->HPT_Backend
    HPT_backend-->PostgreSQL_hpt;
    HPT_backend-->Keycloak;
    Keycloak-->PostgreSQL_keycloak;
```

The authentication part is implemented by using Keycloak. Keycloak itself uses a PostgreSQL database.
Our own application state is persisted using a separate PostgreSQL database server.

The UI only communicates with the HPT-backend and with Keycloak for the login flow.

