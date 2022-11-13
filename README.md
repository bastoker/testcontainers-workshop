# testcontainers-workshop

<p align="middle">
    <img src="logo.svg" height="180"/>
    <img src="testcontainers-logo.svg" height="180"/>
</p>    

# "Holiday Planning Tool"

<img src="holiday.jpg" width="500" />

```mermaid
graph TD;
    Vakantieplanner-->PostgreSQL_1;
    Vakantieplanner-->Keycloak;
    Keycloak-->PostgreSQL_2;
```
