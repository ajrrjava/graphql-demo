package com.strateknia.graphql.common.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Planet {
    private long id;
    private String name;
    private String description;
    private double diameter;
    private String type;
}
