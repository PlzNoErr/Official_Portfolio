package com.bodyfit.restapi.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Gym {
    private long gymId;
    private String gymName;
    private String address;
    private double latitude;
    private double longitude;
}
