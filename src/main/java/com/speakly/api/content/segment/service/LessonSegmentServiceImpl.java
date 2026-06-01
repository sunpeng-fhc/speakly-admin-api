package com.speakly.api.content.segment.service;

import com.speakly.api.common.PageResponse;
import com.speakly.api.entity.Lesson;
import com.speakly.api.content.lesson.repository.LessonRepository;
import com.speakly.api.content.segment.dto.LessonSegmentDTO;
import com.speakly.api.entity.LessonSegment;
import com.speakly.api.content.segment.repository.LessonSegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonSegmentServiceImpl implements LessonSegmentService{

    private final LessonSegmentRepository segmentRepository;
    private final LessonRepository lessonRepository;

    @Override
    public PageResponse<LessonSegmentDTO> list(Long lessonId, Integer current, Integer size) {
        int pageCurrent = current == null ? 1 : current;
        int pageSize = size == null ? 10 : size;

        Pageable pageable = PageRequest.of(
                pageCurrent - 1,
                pageSize,
                Sort.by(Sort.Direction.ASC, "sortOrder", "id")
        );

        Page<LessonSegment> page = segmentRepository.findByLessonId(lessonId, pageable);

        List<LessonSegmentDTO> records = page.getContent()
                .stream()
                .map(this::toDTO)
                .toList();

        return new PageResponse<>(records, pageCurrent, pageSize, page.getTotalElements());
    }
    @Override
    public LessonSegmentDTO detail(Long id) {
        return toDTO(getById(id));
    }

    @Override
    public LessonSegmentDTO create(LessonSegmentDTO dto) {
        LessonSegment segment = new LessonSegment();
        copyToEntity(dto, segment);

        return toDTO(segmentRepository.save(segment));
    }

    @Override
    public LessonSegmentDTO update(Long id, LessonSegmentDTO dto) {
        LessonSegment segment = getById(id);
        copyToEntity(dto, segment);

        return toDTO(segmentRepository.save(segment));
    }

    @Override
    public void delete(Long id) {
        segmentRepository.delete(getById(id));
    }

    private LessonSegment getById(Long id) {
        return segmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson segment not found"));
    }

    private Lesson getLesson(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
    }


    private void copyToEntity(LessonSegmentDTO dto, LessonSegment segment) {
        segment.setLesson(getLesson(dto.getLessonId()));
        segment.setStartTime(dto.getStartTime());
        segment.setEndTime(dto.getEndTime());
        segment.setSentence(dto.getSentence());
        segment.setTranslation(dto.getTranslation());
        segment.setSortOrder(dto.getSortOrder());
    }

    private LessonSegmentDTO toDTO(LessonSegment segment) {
        LessonSegmentDTO dto = new LessonSegmentDTO();
        dto.setId(segment.getId());
        dto.setLessonId(segment.getLesson().getId());
        dto.setStartTime(segment.getStartTime());
        dto.setEndTime(segment.getEndTime());
        dto.setSentence(segment.getSentence());
        dto.setTranslation(segment.getTranslation());
        dto.setSortOrder(segment.getSortOrder());
        dto.setCreatedAt(segment.getCreatedAt());
        dto.setUpdatedAt(segment.getUpdatedAt());
        return dto;
    }

    @Override
    public List<LessonSegmentDTO> getByLessonId(Long lessonId) {

        return segmentRepository
                .findByLessonIdOrderBySortOrderAsc(lessonId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    @Transactional
    public List<LessonSegmentDTO> importSrt(Long lessonId, MultipartFile file) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("课程不存在"));

        try {
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);

            List<LessonSegment> segments = parseSrt(content, lesson);

            segmentRepository.deleteByLessonId(lessonId);

            List<LessonSegment> saved = segmentRepository.saveAll(segments);

            return saved.stream()
                    .map(this::toDTO)
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException("SRT 文件读取失败");
        }
    }

    private List<LessonSegment> parseSrt(String content, Lesson lesson) {
        List<LessonSegment> segments = new ArrayList<>();

        String[] blocks = content.split("\\r?\\n\\r?\\n");
        int sortOrder = 1;

        for (String block : blocks) {
            String[] lines = block.split("\\r?\\n");

            if (lines.length < 3) continue;

            String timeLine = lines[1];

            if (!timeLine.contains("-->")) continue;

            String[] times = timeLine.split("-->");

            BigDecimal startTime = parseTimeToSeconds(times[0].trim());
            BigDecimal endTime = parseTimeToSeconds(times[1].trim());

            String sentence = lines[2].trim();
            String translation = lines.length >= 4 ? lines[3].trim() : "";

            LessonSegment segment = new LessonSegment();
            segment.setLesson(lesson);
            segment.setStartTime(startTime);
            segment.setEndTime(endTime);
            segment.setSentence(sentence);
            segment.setTranslation(translation);
            segment.setSortOrder(sortOrder++);

            segments.add(segment);
        }

        return segments;
    }

    private BigDecimal parseTimeToSeconds(String time) {
        String[] parts = time.split(":");

        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);

        String[] secondParts = parts[2].split(",");

        int seconds = Integer.parseInt(secondParts[0]);
        int millis = Integer.parseInt(secondParts[1]);

        double totalSeconds =
                hours * 3600 +
                        minutes * 60 +
                        seconds +
                        millis / 1000.0;

        return BigDecimal.valueOf(totalSeconds)
                .setScale(3, RoundingMode.HALF_UP);
    }

    @Override
    @Transactional
    public List<LessonSegmentDTO> saveSegments(
            Long lessonId,
            List<LessonSegmentDTO> segmentDTOList
    ) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("课程不存在"));

        segmentRepository.deleteByLessonId(lessonId);

        List<LessonSegment> segments = new ArrayList<>();

        for (int i = 0; i < segmentDTOList.size(); i++) {
            LessonSegmentDTO dto = segmentDTOList.get(i);

            LessonSegment segment = new LessonSegment();
            segment.setLesson(lesson);
            segment.setStartTime(dto.getStartTime());
            segment.setEndTime(dto.getEndTime());
            segment.setSentence(dto.getSentence());
            segment.setTranslation(dto.getTranslation());
            segment.setSortOrder(i + 1);

            segments.add(segment);
        }

        List<LessonSegment> savedSegments = segmentRepository.saveAll(segments);

        String transcript = savedSegments.stream()
                .sorted(Comparator.comparing(LessonSegment::getSortOrder))
                .map(LessonSegment::getSentence)
                .filter(sentence -> sentence != null && !sentence.isBlank())
                .collect(Collectors.joining("\n"));

        lesson.setTranscript(transcript);
        lessonRepository.save(lesson);

        return savedSegments.stream()
                .sorted(Comparator.comparing(LessonSegment::getSortOrder))
                .map(this::toDTO)
                .toList();
    }
}
