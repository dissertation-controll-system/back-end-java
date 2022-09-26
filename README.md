# Getting Started

## Run locally
**Build**
```shell
./mvnw clean install
```
Skip tests
```shell
./mvnw clean install -DskiptTests=true
```

**Run**
```shell
./mvnw spring-boot:run
```
With demo data
```shell
./mvnw spring-boot:run -Dspring-boot.run.jvmArguments="-DLIQUIBASE_CONTEXTS=demo"
```