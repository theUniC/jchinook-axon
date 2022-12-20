# JChinook

This is a sample application to test out Axon Framework using [chinook database](https://github.com/lerocha/chinook-database).

## How to run the code

1. Install [sdkman](https://sdkman.io/)
2. Go to project root
3. Run `sdk env`
4. Run `cp .env.example .env`
6. Run `docker compose up -d`
7. Run `./gradlew flywayMigrate -i`
8. Run `./gradlew bootRun`
9. Open a browser and point to http://127.0.0.1:8080/api/swagger-ui.html
10. To run tests `./gradlew test`