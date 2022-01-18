package com.kkkoke.networkrepair.controller.innerController;

import com.kkkoke.networkrepair.statusAndDataResult.StatusAndDataFeedback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TimeController {
    @GetMapping("/getTime")
    public StatusAndDataFeedback getTime() {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String threeDaysAfter = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Map<String, String> res = new HashMap<>();
        res.put("now", now);
        res.put("after", threeDaysAfter);

        return new StatusAndDataFeedback(res, StatusAndDataFeedback.HANDLE_SUCCESS);
    }
}
