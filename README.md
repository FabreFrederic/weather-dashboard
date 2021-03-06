master branch : [![Build Status](https://travis-ci.org/FabreFrederic/weather-dashboard.svg?branch=master)](https://travis-ci.org/FabreFrederic/weather-dashboard)
<br />
develop branch : [![Build Status](https://travis-ci.org/FabreFrederic/weather-dashboard.svg?branch=develop)](https://travis-ci.org/FabreFrederic/weather-dashboard)
<br />

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

Open the the mongo shell
```bash
mongo
```
```bash
> show dbs
admin              0.000GB
config             0.000GB
local              0.000GB
weather-dashboard  0.000GB
> use weather-dashboard
switched to db weather-dashboard
> show collections
weather.dashboard.collection
> db.weather.dashboard.collection.find()
{ "_id" : "5c262b11644c62438050dbe7", "id" : null, "date" : "2018-12-28T13:54:25.033Z", "value" : "4", "sensorEnvironment" : "AIR" }
{ "_id" : "5c262b11644c62438050dbe8", "id" : null, "date" : "2018-12-28T13:54:25.033Z", "value" : "6", "sensorEnvironment" : "WATER" }
{ "_id" : "5c262b19644c62438050dbe9", "id" : null, "date" : "2018-12-28T13:54:33.337Z", "value" : "1", "sensorEnvironment" : "WATER" }
{ "_id" : "5c262b19644c62438050dbea", "id" : null, "date" : "2018-12-28T13:54:33.337Z", "value" : "5", "sensorEnvironment" : "AIR" }
{ "_id" : "5c262b1d644c62438050dbeb", "id" : null, "date" : "2018-12-28T13:54:37.050Z", "value" : "1", "sensorEnvironment" : "WATER" }
```

## Sensor sketch and backend sensor transmitter
Sensor reading separators : '\n' <br />
The app uses tags to identify the kind of sensor

## Build to install weather dashboard in production
```bash
cd weather-dashboard/frontend/weather-dashboard
npm install
ng build --prod
cd docker
docker-compose up --build


```