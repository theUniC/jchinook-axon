# JChinook

This is a sample application to test out Axon Framework using [chinook database](https://github.com/lerocha/chinook-database).

## How to run the code

1. Install [sdkman](https://sdkman.io/)
2. Run `sdk env`
3. Run `cp src/main/resources/.env.example src/main/resources/.env`
4. Run `docker compose up -d`
5. Run `./gradlew bootRun`
6. Open a browser and point to http://127.0.0.1:8080/api/swagger-ui.html
7. To run tests `./gradlew test`