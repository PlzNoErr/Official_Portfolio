package com.ssafy.marathon.dto.request.treatment;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationReqDto {

    private Long reservationSeq;

    private Long patientSeq;

    private LocalDate localDate;

    private String bitDate;
}
