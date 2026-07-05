# Étape 1 : Construction de l'application avec Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copier les fichiers pom.xml et sources
COPY pom.xml .
COPY src ./src

# Construire le JAR sans exécuter les tests
RUN mvn clean package -DskipTests

# Étape 2 : Image d'exécution légère avec JDK 17
FROM eclipse-temurin:17-jdk-alpine

# Répertoire de travail dans le container
WORKDIR /app

# Copier le JAR depuis l'étape de build
COPY --from=build /app/target/commandes-0.0.1-SNAPSHOT.jar app.jar

# Expose le port 8081 (comme dans ton application.properties)
EXPOSE 8081

# Définir la variable d'environnement pour le port (utile notamment sur Render)
ENV PORT=8081

# Commande pour lancer l'application
ENTRYPOINT ["java", "-Dserver.port=${PORT}", "-jar", "/app/app.jar"]