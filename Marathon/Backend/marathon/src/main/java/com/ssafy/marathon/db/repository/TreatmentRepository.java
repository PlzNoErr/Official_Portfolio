package com.ssafy.marathon.db.repository;

import com.ssafy.marathon.db.entity.treatment.Treatment;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreatmentRepository extends JpaRepository<Treatment, Long> {

    List<Treatment> findByDateBetweenAndDoctor_Seq(LocalDate start, LocalDate end, Long doctorSeq);

    List<Treatment> findAllByPatient_Seq(Long patientSeq);

    List<Treatment> findDistinctByDoctor_Seq(Long doctorSeq);

    List<Treatment> findDistinctByPatient_Seq(Long patientSeq);

    Treatment findBySeq(Long seq);

}
