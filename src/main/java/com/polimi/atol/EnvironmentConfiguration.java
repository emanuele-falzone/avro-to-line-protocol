package com.polimi.atol;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EnvironmentConfiguration implements Configuration {
    private final String KAFKA_BOOTSTRAP_SERVERS = "KAFKA_BOOTSTRAP_SERVERS";
    private final String KAFKA_SCHEMA_REGISTRY_URL = "KAFKA_SCHEMA_REGISTRY_URL";
    private final String KAFKA_INPUT_TOPIC = "KAFKA_INPUT_TOPIC";
    private final String KAFKA_OUTPUT_TOPIC = "KAFKA_OUTPUT_TOPIC";
    private final String INFLUX_TIMESTAMP = "INFLUX_TIMESTAMP";
    private final String INFLUX_MEASUREMENT = "INFLUX_MEASUREMENT";
    private final String INFLUX_FIELDS = "INFLUX_FIELDS";
    private final String INFLUX_TAGS = "INFLUX_TAGS";

    @Override
    public String getKafkaBootstrapServers() {
        return System.getenv(KAFKA_BOOTSTRAP_SERVERS);
    }

    @Override
    public String getKakfaSchemaRegistryUrl() {
        return System.getenv(KAFKA_SCHEMA_REGISTRY_URL);
    }

    @Override
    public String getKafkaInputTopic() {
        return System.getenv(KAFKA_INPUT_TOPIC);
    }

    @Override
    public String getKafkaOutputTopic() {
        return System.getenv(KAFKA_OUTPUT_TOPIC);
    }

    @Override
    public String getInfluxTimestamp() {
        return System.getenv(INFLUX_TIMESTAMP);
    }

    @Override
    public String getInfluxMeasurement() {
        return System.getenv(INFLUX_MEASUREMENT);
    }

    @Override
    public List<String> getInfluxTags() {
        final String value = System.getenv(INFLUX_TAGS);
        if (value.isEmpty()) return Collections.emptyList();
        return Arrays.asList(value.split(","));
    }

    @Override
    public List<String> getInfluxFields() {
        final String value = System.getenv(INFLUX_FIELDS);
        if (value.isEmpty()) return Collections.emptyList();
        return Arrays.asList(value.split(","));
    }
}
