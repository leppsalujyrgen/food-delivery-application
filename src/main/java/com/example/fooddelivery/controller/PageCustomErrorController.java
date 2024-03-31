package com.example.fooddelivery.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Controller class for handling custom error pages.
 * This class implements ErrorController to handle errors at the controller level.
 */
@Controller
public class PageCustomErrorController implements ErrorController {

    /**
     * Handles error requests and displays custom error pages.
     *
     * @param request the HttpServletRequest object containing error information
     * @param model   the Model object to pass data to the view
     * @return the name of the HTML template for the error page
     */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("error", "Error 404. Requested resource does not exist");
            } else {
                model.addAttribute("error", "Error 500. Internal server error occurred");
            }
        }

        // Return api_overview page that displays an error message passed as model attribute.
        return "api_overview"; 
    }
}
