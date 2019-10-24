package com.moguying.plant.utils;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class BankCarSerialize implements ObjectSerializer {
    @Override
    public void write(JSONSerializer jsonSerializer, Object value, Object filedName, Type type, int i) throws IOException {
        if (value instanceof String) {
            StringBuffer sb = new StringBuffer();
            String bankCar = (String) value;
            int length = bankCar.length();
            if(length > 0 ) {
                sb.append("····").append(" ")
                        .append("····").append(" ")
                        .append("····").append(" ")
                        .append(bankCar.substring(length - 4,length));

                jsonSerializer.write(sb.toString());
            } else {
                jsonSerializer.write(value);
            }

        } else {
            jsonSerializer.write(value);
        }
    }
}
