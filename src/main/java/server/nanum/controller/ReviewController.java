package server.nanum.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.nanum.domain.dto.Request.AddReviewDto;
import server.nanum.domain.dto.Response.MyReviewOrdersDto;
import server.nanum.domain.dto.Response.MyUnReviewOrdersDto;
import server.nanum.service.ReviewService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reviews/")
public class ReviewController {
    private final ReviewService reviewService;
    @GetMapping("/delivered")
    public ResponseEntity<MyUnReviewOrdersDto> getDeliveredOrder(){
        Long userId=1L; // 가상으로 지정
        MyUnReviewOrdersDto dto = reviewService.GetUnReviewOrder(userId);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/my")
    public ResponseEntity<MyReviewOrdersDto> getMyOrder(){
        Long userId=1L; // 가상으로 지정
        MyReviewOrdersDto dto = reviewService.GetReviewedOrder(userId);
        return ResponseEntity.ok().build();
    }
    @PostMapping()
    public ResponseEntity<Void> addReview(@RequestBody AddReviewDto dto){
        reviewService.createReview(dto);
        return ResponseEntity.ok().build();
    }
}
