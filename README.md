# Migrating Spring application to Quarku

## Docker Compose

```shell
docker-compose up -d
```

## Spring Application

Building native application locally:

```shell
cd product-service-spring
./mvnw clean native:compile -Pnative
```

Building native application with Buildpacks:

```shell
cd product-service-spring
./mvnw spring-boot:build-image -Pnative
```

## Quarkus Spring Application

Building native application locally:

```shell
cd product-service-quarkus-spring
./mvnw clean package -Dnative
```



## Comparison

|Type|Size|Time|
|---|---|---|
|Spring JAR|69MB|4.325 seconds|
|Spring Native|188M|3.691 seconds|
|Quarkus Spring JAR|703B|2.110 seconds|
|Quarkus Spring Native|110M|0.074 seconds|

