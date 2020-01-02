package com.moguying.plant.utils;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author  Qinhir
 */
@Component
public class IdCardSerialize implements ObjectSerializer {
    @Override
    public void write(JSONSerializer jsonSerializer, Object value, Object filedName, Type type, int i) throws IOException {
        if (value instanceof String) {
            String idOrPhone = (String) value;
            StringBuilder sb = new StringBuilder();
            int length = idOrPhone.length();
            if (length >= 15) {
                sb.append(idOrPhone, 0, 3)
                        .append("***")
                        .append(idOrPhone, length - 4, length);
            } else if (length == 11) {
                sb.append(idOrPhone, 0, 3)
                        .append("****")
                        .append(idOrPhone, length - 4, length);
            }
            jsonSerializer.write(sb.toString());
        } else {
            jsonSerializer.write(value);
        }
    }
}
