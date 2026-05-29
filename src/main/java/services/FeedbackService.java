package services;

import models.DTO_data_transfer_object.CreateFeedbackDto;
import models.DTO_data_transfer_object.UpdateFeedbackDto;
import models.Feedback;
import repository.FeedbackRepository;

import java.sql.Timestamp;
import java.time.Instant;

public class FeedbackService extends BaseService<Feedback, CreateFeedbackDto, UpdateFeedbackDto, FeedbackRepository> {

    private final FeedbackRepository feedbackRepository;

    public FeedbackService(){
        super("feedback", new FeedbackRepository());
        this.feedbackRepository = this.modelRepository;
    }

    @Override
    public Feedback create(CreateFeedbackDto dto) {
        if (!isValidCreateDto(dto)) {
            throw new IllegalArgumentException("Të dhënat për krijimin e feedback-ut janë të pavlefshme.");
        }
        return feedbackRepository.create(dto);
    }

    @Override
    public Feedback update(UpdateFeedbackDto dto) {
        if (!isValidUpdateDto(dto)) {
            throw new IllegalArgumentException("Të dhënat për përditësim janë të pavlefshme.");
        }
        return feedbackRepository.update(dto);
    }

    private boolean isValidCreateDto(CreateFeedbackDto dto) {
        return dto.getIdProfesori() > 0 &&
                dto.getIdStudenti() > 0 &&
                dto.getVleresimi() > 0;
    }

    private boolean isValidUpdateDto(UpdateFeedbackDto dto) {
        return dto.getId() > 0 &&
                dto.getVleresimi() > 0 &&
                (dto.getData() == null || !dto.getData().after(Timestamp.from(Instant.now())));
    }

    public String getEvaluationDescription(int vleresimi) {
        String lang = LanguageManager.getInstance().getLocale().getLanguage();

        if ("sq".equals(lang)) {
            return switch (vleresimi) {
                case 1 -> "Shumë keq";
                case 2 -> "Keq";
                case 3 -> "Mesatar";
                case 4 -> "Mirë";
                case 5 -> "Shumë Mirë";
                default -> "I panjohur";
            };
        } else {
            return switch (vleresimi) {
                case 1 -> "Very bad";
                case 2 -> "Bad";
                case 3 -> "Average";
                case 4 -> "Good";
                case 5 -> "Very good";
                default -> "Unknown";
            };
        }
    }


}
