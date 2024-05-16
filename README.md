how to run:
gradle clean build bootRun

dependencies:
postgresdb, java 17

docker command for M1/M2 mac ->
docker run --name mentormind -e POSTGRES_PASSWORD=mentormind -e POSTGRES_USER=mentormind -e POSTGRES_DB=mentormind -d  -p 0:5432 arm64v8/postgres

docker command for windows ->
docker run --name mentormind -e POSTGRES_PASSWORD=mentormind -e POSTGRES_USER=mentormind -e POSTGRES_DB=mentormind -d  -p 0:5432 postgres

you need to pull the docker image before (check what image is compatible with your machine and use that as last parameter in the commands given above)

change db credentials for you in application.properties

!!IF YOU WANT DATA TO PERSIST DO NOT CREATE OTHER DOCKER CONTAINERS, REUSE THE SAME ONE
