package com.ssafy.marathon.service.patient;

import com.ssafy.marathon.db.entity.treatment.History;
import com.ssafy.marathon.db.repository.HistoryRepository;
import com.ssafy.marathon.dto.response.treatment.HistoryResDto;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientHistoryServiceImpl implements PatientHistoryService{

    private final HistoryRepository historyRepository;

    @Override
    public Page<HistoryResDto> getHistories(Long patientSeq, int page) {
        List<History> list = historyRepository.findAllByPatient_SeqOrderByDateDesc(patientSeq);
        List<HistoryResDto> resList = new ArrayList<>();

        for (History history:list) {
            HistoryResDto historyResDto = HistoryResDto.builder()
                .historySeq(history.getSeq())
                .doctorName(history.getDoctor().getName())
                .dateTime(LocalDateTime.of(history.getDate(), history.getTime()))
                .day(history.getDate().getDayOfWeek().toString())
                .build();

            resList.add(historyResDto);
        }

        //        list to page
        PageRequest pageRequestForList = PageRequest.of(page, 5);
        int start = (int) pageRequestForList.getOffset();
        int end = Math.min((start + pageRequestForList.getPageSize()), resList.size());
        Page<HistoryResDto> historyResDtoPage = new PageImpl<>(resList.subList(start, end),
            pageRequestForList, resList.size());

        return historyResDtoPage;
    }

    @Override
    public HistoryResDto getHistoryDetail(Long historySeq) {
        History history = historyRepository.findBySeq(historySeq);
        HistoryResDto historyResDto = HistoryResDto.builder()
            .doctorName(history.getDoctor().getName())
            .doctorPhone(history.getDoctor().getPhone())
            .doctorImg(history.getDoctor().getImg())
            .dateTime(LocalDateTime.of(history.getDate(), history.getTime()))
            .day(history.getDate().getDayOfWeek().toString())
            .feedback(history.getFeedback())
            .videoUrl(history.getVideoUrl())
            .build();

        return historyResDto;
    }
}
