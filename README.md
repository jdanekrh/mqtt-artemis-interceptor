# mqtt-artemis-interceptor

Message interceptor class for the ActiveMQ Artemis message broker and Python scripts to send and receive a MQTT message.

This README may seem unnecessarily chatty because I am using it to make notes for myself.

## Purpose

Investigating http://stackoverflow.com/questions/38101899/intercepting-mqtt-messages-in-artemis

Playing with upcoming Gradle features, namely support for build scripts written in Kotlin

## Build

### Gradle 3.0

The Java part requires unreleased Gradle 3.0 to build. As of now, Gradle 3.0 is available only as prerelease or nightly development builds. Install the newest Gradle release and run the following command to get it and use it. See https://github.com/gradle/gradle-script-kotlin and these release notes https://github.com/gradle/gradle/releases/tag/v3.0.0-M2

    gradle wrapper --gradle-version 3.0-milestone-2          # or
    gradle wrapper --gradle-version 3.0-20160701000016+0000

    ./gradlew --version

### ActiveMQ Artemis

Build expects to have ActiveMQ Artemis locally because it pulls jars from the lib dir in there.

    wget http://mirror.hosting90.cz/apache/activemq/activemq-artemis/1.3.0/apache-artemis-1.3.0-bin.zip
    unzip apache-artemis-1.3.0-bin.zip

### Build

    ./gradlew compile

## Install

    mkdir broker-i0/lib
    cp build/libs/mqtt-artemis-interceptor.jar broker-i0/lib/

## Configure

### ActiveMQ Artemis

    apache-artemis-1.3.0/bin/artemis create --user admin --password admin --role admin --allow-anonymous broker-i0 
    vim broker-i0/etc/broker.xml
    
add the following to `broker.xml`

    <configuration ...>
    ...
        <core ...>
            <remoting-incoming-interceptors>
                <class-name>com.github.jdanekrh.MyInterceptor</class-name>
            </remoting-incoming-interceptors>
        </core>
    ... 
    </configuration>

## Run
    
    broker-i0/bin/artemis run
    
    # in another terminal
    python mqtt_sender
    # or
    python qpid-proton/examples/python/simple_send.py -a127.0.0.1/jms.queue.myYetAnotherQueue
    
    # and when the send command finishes
    
    python mqtt_receiver.py
    # or
    python qpid-proton/examples/python/simple_recv.py -a127.0.0.1/jms.queue.myYetAnotherQueue

Now send and receive a message over the core protocol.

    ./gradlew run

### Bug description

When sending AMQP or MQTT messages, the Interceptor does not get triggered (it does not print anything). When sending message over the core protocol, it does get triggered and prints.

## Useful commands

    # http://stackoverflow.com/questions/1342894/find-a-class-somewhere-inside-dozens-of-jar-files
    find apache-artemis-1.3.0/lib/ -name "*.jar" -exec grep -Hls "jms.Queue" {} \;
