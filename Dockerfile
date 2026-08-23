# Étape 1 : Construction de l'application avec Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copier les fichiers Maven et les sources
COPY pom.xml .
COPY src ./src

# Construire le JAR sans exécuter les tests
RUN mvn clean package -DskipTests

# Étape 2 : Image d'exécution
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copier le JAR généré
COPY --from=build /app/target/*.jar app.jar

# Port utilisé en local.
# Render fournira automatiquement sa propre variable PORT.
EXPOSE 8081

# Lancer Spring Boot
ENTRYPOINT ["java", "-jar", "/app/app.jar"]