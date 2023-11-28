package dev.notification.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feedback")
public class FeedBackController {
    @Autowired
    private FeedBackService _feedbackService;

    @PostMapping
    public ResponseEntity<FeedBack> createFeedback(@RequestBody FeedBack feedbacks) {

        FeedBack createdFeedback = _feedbackService.createFeedBack(feedbacks);
        return new ResponseEntity<>(createdFeedback, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FeedBack>> getAllNFeedbacks() {

        List<FeedBack> feedBacks = _feedbackService.getAllFeedBacks();
        return new ResponseEntity<>(feedBacks, HttpStatus.OK);

    }
}
