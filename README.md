# JChinook

This is a sample application to test out Axon Framework using [chinook database](https://github.com/lerocha/chinook-database).

## How to run the code

1. Install [sdkman](https://sdkman.io/)
2. Go to project root
3. Run `sdk env`
4. Run `cp .env.example .env`
5. Run `docker compose up -d`
6. Run `./gradlew flywayMigrate -i`
7. Run `./gradlew bootRun`
8. Open a browser and point to http://127.0.0.1:8080/api/swagger-ui.html to see Swagger UI
9. Open a browser and point to http://127.0.0.1:8080/api/graphiql to see GraphiQL
10. To run tests `./gradlew test`