package com.moguying.plant.utils;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;

@Component
public class IdCardSerialize implements ObjectSerializer {
    @Override
    public void write(JSONSerializer jsonSerializer, Object value, Object filedName, Type type, int i) throws IOException {

        if (value instanceof String) {
            StringBuffer sb = new StringBuffer();
            String idCard = (String) value;
            int length = idCard.length();
            if(length >= 15) {
                sb.append(idCard.substring(0, 3))
                        .append("***")
                        .append(idCard.substring(length - 4, length));
                jsonSerializer.write(sb.toString());
            } else if(length == 11) {
                sb.append(idCard.substring(0, 3))
                        .append("***")
                        .append(idCard.substring(length - 3, length));
                jsonSerializer.write(sb.toString());
            } else {
                jsonSerializer.write(value);
            }

        } else {
            jsonSerializer.write(value);
        }
    }
}
