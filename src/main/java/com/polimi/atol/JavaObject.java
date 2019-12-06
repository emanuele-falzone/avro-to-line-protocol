package com.polimi.atol;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;

public class JavaObject {
    public static Object from(GenericRecord record, String field) {
        final String value = record.get(field).toString();
        final Schema.Type type = record.getSchema().getField(field).schema().getType();

        switch (type){
            case LONG:
                return Long.parseLong(value);
            case INT:
                return Integer.parseInt(value);
            case FLOAT:
                return Float.parseFloat(value);
            case BOOLEAN:
                return Boolean.parseBoolean(value);
            case DOUBLE:
                return Double.parseDouble(value);
            case STRING:
                return value;
        }
        return null;
    }
}
