FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
# pom e wrapper antes do src: mudar código não baixa as dependências de novo
COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw -B dependency:go-offline
COPY src src
RUN ./mvnw -B -DskipTests package

FROM eclipse-temurin:17-jre
WORKDIR /app
RUN useradd --system --no-create-home wave
# wave-*.jar não pega o .jar.original do repackage
COPY --from=build /app/target/wave-*.jar app.jar
USER wave
ENV SPRING_PROFILES_ACTIVE=prod
# MaxRAMPercentage=60: ~307 MB de heap em 512 MB, com folga para metaspace e threads
# UseSerialGC: o GC com menor consumo de memória e de threads
# Xss512k: metade da pilha padrão por thread
# TieredStopAtLevel=1: só o compilador C1, menos CPU e memória no início
ENV JAVA_TOOL_OPTIONS="-XX:MaxRAMPercentage=60 -XX:+UseSerialGC -Xss512k -XX:TieredStopAtLevel=1"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
