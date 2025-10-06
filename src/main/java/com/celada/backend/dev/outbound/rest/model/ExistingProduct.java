package com.celada.backend.dev.outbound.rest.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ExistingProduct {
    private String id;

    private String name;

    private BigDecimal price;

    private Boolean availability;
}
