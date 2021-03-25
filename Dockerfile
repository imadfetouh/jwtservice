FROM openjdk:11

COPY /jwtservice/target/jwt.jar /home/jwt.jar

ENTRYPOINT ["java", "-jar", "/home/jwt.jar"]