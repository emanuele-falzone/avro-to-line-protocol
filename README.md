# avro-to-line-protocol

### Example

Suppose that we have a simple avro value schema
```json
{
   "namespace":"com.example",
   "type":"record",
   "name":"ValueRecord",
   "fields":[
      {
         "name":"timestamp",
         "type":"long"
      },
      {
         "name":"sampletag1",
         "type":"long"
      },
      {
         "name":"sampletag2",
         "type":"long"
      },
      {
         "name":"samplefield1",
         "type":"long"
      },
      {
         "name":"samplefield2",
         "type":"long"
      }
   ]
}
```
#### To be noticed!
The avro schema must include a `timestamp` field.

Then we can use this `docker-compose.yml` that includes the component:

```yml
version: '3.2'
services:

  ...

  avro-to-line-protocol:
    image: emanuelefalzone/avro-to-line-protocol:1.0.0
    hostname: avro-to-line-protocol
    container_name: avro-to-line-protocol
    environment:
      KAFKA_BOOTSTRAP_SERVERS: broker:29092
      KAFKA_SCHEMA_REGISTRY_URL: http://schema-registry:8081
      KAFKA_INPUT_TOPIC: avro_topic
      KAFKA_OUTPUT_TOPIC: line_protocol_topic
      INFLUX_TAGS: sampletag1,sampletag2
      INFLUX_FIELDS: samplefield1,samplefield2
      INFLUX_TIMESTAMP: timestamp
      INFLUX_MEASUREMENT: sample_measurment
```

INFLUX_TAGS and INFLUX_FIELDS refer to the avro fields that are used as fields and tags in InflixDB LineProtocol.
