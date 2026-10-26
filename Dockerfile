# ---- Build stage ----
FROM maven:3.9.8-eclipse-temurin-17-alpine AS build
WORKDIR /opt/app

# Copia só o pom primeiro para aproveitar cache de dependências
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre-alpine
WORKDIR /opt/app

COPY --from=build /opt/app/target/*.jar app.jar

EXPOSE 8080

# Nenhum profile e forcado aqui: por padrao a aplicacao roda com o profile "default".
# Para rodar em producao, selecione o profile "prd" no `docker run`:
#   docker run -e SPRING_PROFILES_ACTIVE=prd ...
CMD ["java", "-jar", "app.jar"]