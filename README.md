# uek223_MultiUserApp_backend
### Noel, Masha, Mischa

## Description
This is the backend for the MultiUserApp. It is a RESTful API that is used to store and retrieve data from a database.
In this Repository, we will implement the Event Entity for the Project.

## Installation

1. `git clone https://github.com/mimasala/uek223_MultiUserApp_backend.git`
2. open the project in your IDE
3. run the project


## Our own Features

- [x] Event Entity
  - [x] CRUD Operations
- [ ] Recommendation System for users

## Usage
We have implemented Swagger, where you can browse the API and test it.
Additionally, we have a seperate [documentation](https://docs.google.com/document/d/1pb5MifRRxSE2RfPzKkzWbbY9kzEW65t68-RpkPGK6Tc/edit?usp=sharing)

## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request


----

## Starter Project Spring Boot

### Docker command
```
docker run --name postgres_db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres
```

### Troubleshooting

```
org.postgresql.util.PSQLException: ERROR: relation "role_authority" does not exist
```
Simply restart the application. Hibernate sometimes does not initialize the tables fast enough an causes thios error. restarting the application fixes this.