#!/bin/bash
#

# TODO
#reset
#docker stop $(docker ps | grep weather-dashboard | awk '{print $1}')
#docker rm $(docker ps -a | grep weather-dashboard | awk '{print $1}')
#docker rmi -f $(docker images |grep weather-dashboard | awk '{print $3}')
#
#rm -Rf ./frontend/weather-dashboard/dist
#cd frontend/weather-dashboard && ng build --prod
#cd -
#cd backend && mvn clean install
#cd -
#cd docker && docker-compose up -d --build