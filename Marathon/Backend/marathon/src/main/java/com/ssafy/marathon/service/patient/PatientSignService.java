package com.ssafy.marathon.service.patient;


import com.ssafy.marathon.dto.request.user.PatientReqDto;
import com.ssafy.marathon.dto.request.user.SignInReqDto;
import com.ssafy.marathon.dto.request.user.UserReqDto;
import com.ssafy.marathon.dto.response.user.PatientResDto;
import com.ssafy.marathon.dto.response.user.SignInResDto;
import com.ssafy.marathon.dto.response.user.SignUpResDto;
import com.ssafy.marathon.expection.FileUploadFailedException;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface PatientSignService {
    SignUpResDto signUp(PatientReqDto patientReqDto);

    PatientResDto getPatient(Long seq);

    String modifyPatient(Long seq, PatientReqDto patientReqDto, MultipartFile file)
        throws Exception;
    String modifyPatient(Long seq, PatientReqDto patientReqDto);
}
