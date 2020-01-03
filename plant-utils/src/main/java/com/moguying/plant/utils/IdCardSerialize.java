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
            jsonSerializer.write(CommonUtil.INSTANCE.idOrPhoneMask((String) value));
        } else {
            jsonSerializer.write(value);
        }
    }
}
