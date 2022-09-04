FROM openjdk:17-oracle
# RUN ["mvn", "install", "-DskipTests"]
ADD target/stock-sim-app.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]