FROM openjdk:27-ea-1-jdk-slim-bookworm
WORKDIR /usr/src/myapp
COPY . .
RUN javac -d . WarSim.java
CMD ["java", "warsim.WarSim"]
