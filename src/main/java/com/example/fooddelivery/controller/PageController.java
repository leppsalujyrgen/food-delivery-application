package com.example.fooddelivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller class for serving API documentation pages.
 * This class handles requests for displaying an overview page of the API endpoints.
 */
@Controller
public class PageController {

    /**
     * Displays the API overview page.
     *
     * @return the name of the HTML template for the API overview page
     */
    @GetMapping("/api")
    public String getApiHomePage() {
        return "api_overview";
    }
}
