# Use an official OpenJDK runtime as the base image
FROM openjdk:18-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled application JAR file to the container
COPY target/tool_renter-1.0-SNAPSHOT.jar /app/tool-rental-application.jar

# Copy the tool_types.json file to the container
# COPY tool_types.json .

# Expose the port that the application listens on
EXPOSE 8080

# Set the command to run the application when the container starts
CMD ["java", "-jar", "/app/tool-rental-application.jar"]
