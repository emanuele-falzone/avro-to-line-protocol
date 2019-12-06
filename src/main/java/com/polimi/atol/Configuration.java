package com.polimi.atol;

import java.util.List;

public interface Configuration {
    public String getKafkaBootstrapServers();
    public String getKakfaSchemaRegistryUrl();
    public String getKafkaInputTopic();
    public String getKafkaOutputTopic();
    public String getInfluxTimestamp();
    public String getInfluxMeasurement();
    public List<String> getInfluxTags();
    public List<String> getInfluxFields();
}
