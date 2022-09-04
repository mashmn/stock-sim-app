# Backend Alteryx Homework - Stock Market Simulator #

## Useful Commands
```shell
docker-compose down
mvn clean install -DskipTests
docker-compose up
```

## To upload csv
```shell
curl -v -F file=@file.csv http://localhost:8084/stocksim/v1/portfolio/uploadcsv
```

## To reset environment
```shell
docker rm stock-sim-app

docker rmi stock-sim-app:1.0
```

#3 To access postgres
docker exec -it stock-sim-postgres bash
psql -U postgres

CREATE DATABASE stocksimdb;
\c stocksimdb;

\l

\d

\d+ stock;

docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' stock-sim-postgres

```# stock-sim-app
