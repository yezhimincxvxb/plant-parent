package com.moguying.plant.utils;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class BigDecimalSignSerialize implements ObjectSerializer {

    @Override
    public void write(JSONSerializer jsonSerializer, Object value, Object filedName, Type type, int i) throws IOException {
        String typeName = type.getTypeName();
        if (typeName.equals(BigDecimal.class.getTypeName())) {
            DecimalFormat decimalFormat = new DecimalFormat("#0%");
            String price = decimalFormat.format(value);
            jsonSerializer.write(price);
        } else {
            jsonSerializer.write(value);
        }
    }
}
