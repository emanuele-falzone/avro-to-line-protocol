package com.polimi.atol;

import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.influxdb.dto.Point;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class App 
{

    public static void main(String[] args )
    {
        Configuration configuration = new EnvironmentConfiguration();
        System.out.println(configuration.getKafkaBootstrapServers());
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "avro-to-line-protocol-application");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, configuration.getKafkaBootstrapServers());
        props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, configuration.getKakfaSchemaRegistryUrl());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, GenericAvroSerde.class);

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, GenericRecord> avroGenericRecords = builder.stream(configuration.getKafkaInputTopic());
        KStream<String, String> lineProtocolRecords = avroGenericRecords
                .map( (key, record) ->
                        new KeyValue<String, String>(null,
                            Point.measurement(configuration.getInfluxMeasurement())
                                    .time(Long.parseLong(record.get(configuration.getInfluxTimestamp()).toString()), TimeUnit.MILLISECONDS)
                                    .tag(collectTags(configuration.getInfluxTags(), record))
                                    .fields(collectFields(configuration.getInfluxFields(), record))
                                    .build()
                                    .lineProtocol()
                        )
                );

        lineProtocolRecords.to(configuration.getKafkaOutputTopic(),
                               Produced.with(Serdes.String(), Serdes.String()));
        
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();
    }

    private static Map<String, Object> collectFields(List<String> fields, GenericRecord record) {
        return fields.stream()
                     .collect(Collectors.toMap(
                          field -> field,
                          field -> JavaObject.from(record, field)
                     ));
    }

    private static Map<String, String> collectTags(List<String> tags, GenericRecord record) {
        return tags.stream()
                .collect(Collectors.toMap(
                        tag -> tag,
                        tag -> record.get(tag).toString()
                ));
    }
}
