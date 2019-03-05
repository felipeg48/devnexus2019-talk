# Spring Cloud App Starters Demo

The purpose of this demo is to show how easy it is to use the App Starters with re-inventing the wheel by creating clients or any other integration app.


## Requirements
The following files are required for this demo.

### Repository
- [App Starters](https://repo.spring.io/libs-release/org/springframework/cloud/stream/app/) repository.

### Downloads:
Download the following app starters:
- [http-source-rabbit](https://repo.spring.io/libs-release/org/springframework/cloud/stream/app/http-source-rabbit/2.1.0.RELEASE/http-source-rabbit-2.1.0.RELEASE.jar)
- [filter-processor-rabbit](https://repo.spring.io/libs-release/org/springframework/cloud/stream/app/filter-processor-rabbit/2.1.0.RELEASE/filter-processor-rabbit-2.1.0.RELEASE.jar)
- [groovy-transform-processor-rabbit](https://repo.spring.io/libs-release/org/springframework/cloud/stream/app/groovy-transform-processor-rabbit/2.1.0.RELEASE/groovy-transform-processor-rabbit-2.1.0.RELEASE.jar)
- [jdbc-sink-rabbit](https://repo.spring.io/libs-release/org/springframework/cloud/stream/app/jdbc-source-rabbit/2.1.0.RELEASE/jdbc-source-rabbit-2.1.0.RELEASE.jar)
- [log-sink-rabbit](https://repo.spring.io/libs-release/org/springframework/cloud/stream/app/log-sink-rabbit/2.1.0.RELEASE/log-sink-rabbit-2.1.0.RELEASE.jar)


There is a `download.sh` script that will download all the necessary app starters. These starters are based on RabbitMQ as transport layer.


### A Simple Example

1. Start up RabbitMQ. You can install it using `brew install rabbitmq` on a Mac or Linux. If you are using Windows you can use `choco install rabbitmq` with [Chocolatey](https://chocolatey.org/). Or if you prefer and you have Docker install you can execute: `docker run --rm --name rmq -d -p 5672:5672 rabbitmq:3.7`.
2. Start the `http-source-rabbit` app starter.
   ```
   java -jar http-source-rabbit-2.1.0.RELEASE.jar --spring.cloud.stream.bindings.output.destination=http --server.port=8081
   ```
3. Start the `log-sink-rabbit` app starter.
 ```
 java -jar log-sink-rabbit-2.1.0.RELEASE.jar --spring.cloud.stream.bindings.input.destination=http --server.port=8082
 ```
4. Send some information
 ```
 curl -XPOST -H "Content-Type: application/json" -d '{"review": {"topic":"spring","comment":"this is amazing","stars":5} }' http://localhost:8081
 ```
5. [Optional] You can stop the app starters by doing `Ctrl+c`.

### Adding a Filter

1. Make sure you have RabbitMQ up and running.
2. Start the `http-source-rabbit` app starter.
   ```
   java -jar http-source-rabbit-2.1.0.RELEASE.jar --spring.cloud.stream.bindings.output.destination=http --server.port=8081
   ```
3. Start the `filter-processor-rabbit` app starter.
 ```
 java -jar filter-processor-rabbit-2.1.0.RELEASE.jar --filter.expression="#jsonPath(payload,'$.review.stars') >= 3" --spring.cloud.stream.bindings.input.destination=http --spring.cloud.stream.bindings.output.destination=log --server.port=8082
 ```
4. Start the `log-sink-rabbit` app starter.
 ```
 java -jar log-sink-rabbit-2.1.0.RELEASE.jar --spring.cloud.stream.bindings.input.destination=log --server.port=8083
 ```
5. Send some information
 ```
 curl -XPOST -H "Content-Type: application/json" -d '{"review":{"topic":"spring","comment":"this is amazing","stars":5}}' http://localhost:8081
curl -XPOST -H "Content-Type: application/json" -d '{"review":{"topic":".net","comment":"this is microsoft","stars":1}}' http://localhost:8081
 ```

**NOTE**
You can create a folder for every app and create an `application.properties` with all the parameters, avoiding have too much in the command line.


### Adding a Transformer
1. Make sure you have RabbitMQ up and running.
2. Create a folder per each app starter and add the `application.properties`.
3. Start the `http-source-rabbit` app starter.
```
cd source
java -jar http-source-rabbit-2.1.0.RELEASE.jar
```
4. Start the `filter-processor-rabbit` app starter.
```
cd processor
java -jar filter-processor-rabbit-2.1.0.RELEASE.jar
```
5. Add the following snippet to the `transformer` folder. Create the script: `transfrom.groovy`.
   ```groovy
   import groovy.json.JsonSlurper
   import groovy.json.JsonOutput

   def jsonSlurper = new JsonSlurper()
   def json = jsonSlurper.parseText(new String(payload))

   if (json.review.stars == 5) {
      json.quote = json.review.comment.toUpperCase()
      println "Accepted and transformed, data to be sent..."
    } else {
      println "Data to be sent with no transformation..."
    }

    return JsonOutput.toJson(json)
    ```
   Start the `groovy-transform-processor-rabbit` app starter.
   ```
   cd transformer
   java -jar groovy-transform-processor-rabbit-2.1.0.RELEASE.jar
   ```
6. Start the `log-sink-rabbit` app starter.
```
cd sink
java -jar log-sink-rabbit-2.1.0.RELEASE.jar
```
7. Send some information
```
curl -XPOST -H "Content-Type: application/json" -d '{"review":{"topic":"spring","comment":"this is amazing","stars":5}}' http://localhost:8081
curl -XPOST -H "Content-Type: application/json" -d '{"review":{"topic":".net","comment":"this is microsoft","stars":1}}' http://localhost:8081
```
