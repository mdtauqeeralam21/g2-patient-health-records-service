FROM openjdk:17-alpine

EXPOSE 9006

ADD /target/g2-patient-health-records-service.jar g2-patient-health-records-service.jar

ENTRYPOINT [ "java","-jar","/g2-patient-health-records-service.jar"]