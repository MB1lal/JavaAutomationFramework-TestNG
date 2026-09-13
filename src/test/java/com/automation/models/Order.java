package com.automation.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    private int id;
    private int petId;
    private int quantity;
    private String shipDate;
    @Builder.Default
    private String status = "placed";
    @Builder.Default
    private boolean complete = true;
}
