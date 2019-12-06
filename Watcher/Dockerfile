FROM jboss/wildfly
LABEL source="Java8" app="Medrag Logistics Watcher" version="1.0"
MAINTAINER Stanislav Tretyakov gaffstranger@gmail.com
ADD target/Watcher.war /opt/jboss/wildfly/standalone/deployments/
#ADD src/main/resources/standalone-ee8.xml /opt/jboss/wildfly/standalone/configuration/standalone-ee8.xml
#CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c", "standalone-ee8.xml"]
EXPOSE 8080 5672