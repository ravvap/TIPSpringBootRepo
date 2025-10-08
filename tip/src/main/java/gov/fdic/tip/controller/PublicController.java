package gov.fdic.tip.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {

    @GetMapping("/health")
    public String health() {
        return "Service is healthy";
    }

    @GetMapping("/info")
    public String info() {
        return "Review Cycle Group Service v1.0";
    }
}