FROM eclipse-temurin:23.0.1_11-jdk AS builder
# AS builder only for multi stage docker

WORKDIR /compiledDir

COPY src src
COPY .mvn .mvn
COPY pom.xml .
COPY mvnw .

RUN chmod a+x ./mvnw && ./mvnw package -Dmaven.test.skip=true

# Doing MultiStage, do not need ENTRYPOINT
# If for Single Stage
# Set ENV_VARIABLES
# EXPOSE ${PORT}
# ENTRYPOINT SERVER_PORT=${PORT} java -jar target/<jar file name>

FROM eclipse-temurin:23.0.1_11-jdk

WORKDIR /myapp

# COPY --from=builder /compiledDir/target/<jar file name of compiled jar> <jar name that you want it to be>
COPY --from=builder /compiledDir/target/day18workshopWordDoc-0.0.1-SNAPSHOT.jar day18workshopWordDoc.jar

# Set the environment variables

ENV SPRING_DATA_REDIS_HOST=localhost
ENV SPRING_DATA_REDIS_PORT=6379
ENV SPRING_DATA_REIDS_USERNAME=
ENV SPRING_DATA_REDIS_PASSWORD=

ENV PORT=3000

EXPOSE ${PORT}

# or EXPOSE ${SERVER_PORT}

ENTRYPOINT java -jar <jar name that you want it to be>