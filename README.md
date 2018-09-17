## Installation

Build and run containers with docker-compose 

```bash
$ docker-compose build
```
then
```bash
$ docker-compose up -d
```
or
```bash
$ docker-compose up -d --build
```

to start the mongodb container only
```bash
$ docker-compose up -d mongodb
```

## Run app
```bash
mvn compile vertx:run
```


## MongoDB commands

```bash
db.getMongo().getDBNames()
db.getUsers()
```

## Sensor sketch and backend sensor transmitter
Sensor reading separators : '\n' <br />
The app uses tags to identify the kind of sensor
