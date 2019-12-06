FROM tomcat:9.0.29-jdk8-openjdk
LABEL source="Java8" app="Medrag Logistics Primary App" version="1.0"
MAINTAINER Stanislav Tretyakov gaffstranger@gmail.com
ADD ["./web/target/MedragLogistics.war", "/usr/local/tomcat/webapps/ROOT.war"]
RUN rm -rf /usr/local/tomcat/webapps/ROOT
VOLUME /usr/local/tomcat/logs
EXPOSE 8080 3306 5672