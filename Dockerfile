FROM williamyeh/java8:latest
ADD nika-search-1.0.jar nika-search-1.0.jar
EXPOSE 8880 5005
CMD ["java","-jar","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005","nika-search-1.0.jar"]
