package com.models;

import lombok.Builder;
import lombok.Data;

// Data чтобы гетеры/сеторы работали
@Data
// Builder чтобы из теста задавать значения
@Builder
public class ShipInfo {
    private String firstName;
    private String lastName;
    private String postCode;
}