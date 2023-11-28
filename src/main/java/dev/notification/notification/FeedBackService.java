package dev.notification.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FeedBackService  {
    @Autowired
    private FeedBackRepository _feedbackRepository;
    @Autowired
    private  SequenceService _sequenceService;
    @Autowired
    private MongoOperations mongoOperations;

    public FeedBack createFeedBack(FeedBack feedbacks) {
        Long feedbackId = generateNextId("feedback_sequence");
        feedbacks.setFeedbackId(feedbackId);
        return _feedbackRepository.save(feedbacks);
    }

    private long generateNextId(String sequenceName) {
        // Get the current sequence value for notification IDs
        long sequenceValue = _sequenceService.getSequenceValue(sequenceName);

        // Increment the sequence value and return it
        _sequenceService.incrementSequenceValue(sequenceName);

        return sequenceValue;
    }

    public List<FeedBack> getAllFeedBacks() {
        return _feedbackRepository.findAll();
    }
}
