FROM openjdk:8-jre-alpine

ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    SPRING_PROFILES_ACTIVE=${spring.profiles.active} \
    JHIPSTER_SLEEP=0

# add directly the war
ADD *.war /jhipster-registry.war

VOLUME /tmp
EXPOSE 8761 8762

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/jhipster-registry.war","--spring.cloud.config.server.native.search-locations=classpath:/"]
