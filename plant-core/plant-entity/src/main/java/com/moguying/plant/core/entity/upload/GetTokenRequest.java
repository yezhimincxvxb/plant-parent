package com.moguying.plant.core.entity.upload;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetTokenRequest implements Serializable {
    private String fileName;
}
