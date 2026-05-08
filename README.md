# Metadata Service Scaffold

This project now includes a starter architecture for a file metadata service under the base package `com.fileservice.metadata`.

## Package layout

- `config` - starter Spring configuration classes.
- `controller` - REST endpoint entry points.
- `service` - business and cache service classes.
- `repository` - repository contracts for metadata persistence.
- `entity` - domain entities for file lookup views.
- `dto` - request/response payloads.
- `kafka.event` - Kafka event payloads.
- `kafka.producer` - Kafka producer facade.
- `exception` - custom exceptions.
- `mapper` - mapping helpers.
- `util` - utility helpers.

## Run tests

```powershell
.\mvnw.cmd test
```

## Build jar

```powershell
.\mvnw.cmd clean package
```

## Run locally

```powershell
.\mvnw.cmd spring-boot:run
```

