FROM tomcat:11.0

RUN rm -rf /usr/local/tomcat/webapps/*

COPY ./target/legorest2.war /usr/local/tomcat/webapps/ROOT.war