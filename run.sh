#! /bin/bash

echo "Checking enviroment variables..."

function check {
    [ -z "$2" ] && echo "Need to set $1" && exit 1;
}

check KAFKA_BOOTSTRAP_SERVERS $KAFKA_BOOTSTRAP_SERVERS
check KAFKA_SCHEMA_REGISTRY_URL $KAFKA_SCHEMA_REGISTRY_URL
check KAFKA_INPUT_TOPIC $KAFKA_INPUT_TOPIC
check KAFKA_OUTPUT_TOPIC $KAFKA_OUTPUT_TOPIC
check INFLUX_TIMESTAMP $INFLUX_TIMESTAMP
check INFLUX_MEASUREMENT $INFLUX_MEASUREMENT
check INFLUX_FIELDS $INFLUX_FIELDS
check INFLUX_TAGS $INFLUX_TAGS

echo "Done!"
echo "Starting ATOL..."

while true # Try 10 times
do
    java -cp /app/magic.jar com.polimi.atol.App
    echo "Retrying in 5 seconds..."
    sleep 5
done