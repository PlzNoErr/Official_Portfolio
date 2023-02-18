package com.ssafy.marathon.dto.request.consulting;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultingReqDto {

    private String name;
    private boolean sex;
    private LocalDate birthDate;
    private String email;
    private String phone1;
    private String phone2;
    private String phone2Relationship;
    private String phone3;
    private String phone3Relationship;
    @Nullable
    private LocalDate sickDate;
    private String description;
    private LocalDate hopeDate;
}
