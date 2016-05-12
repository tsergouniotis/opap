FROM java:8

VOLUME /tmp

ADD target/hotelgenius-pci.jar app.jar

ADD dockerfiles/run.sh /run.sh

RUN bash -c 'touch /app.jar'

EXPOSE 8003

CMD ["bash","./run.sh"]