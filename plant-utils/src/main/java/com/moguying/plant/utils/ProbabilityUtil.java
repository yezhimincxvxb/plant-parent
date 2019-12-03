package com.moguying.plant.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
@Slf4j
public class ProbabilityUtil {

    private final List<String> probabilityList = Collections.synchronizedList(new LinkedList<>());

    /**
     * 填充概率
     *
     * @param probability 概率规则
     * @return
     */
    public String fillList(String probability) {
        probabilityList.clear();
        String[] split = probability.split("\\|");
        for (String rate : split) {
            String[] rateDes = rate.split("-");
            for (int i = 0; i < Integer.parseInt(rateDes[1]); i++) {
                probabilityList.add(rateDes[0]);
            }
        }
        Collections.shuffle(probabilityList);
        long l = System.currentTimeMillis() % probabilityList.size();
        return probabilityList.get((int) l);
    }
}
