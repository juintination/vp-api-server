package com.example.vpapi.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
@Log4j2
public class VpController {

    @GetMapping("/")
    public String index() {
        return "redirect:/docs";
    }

    @GetMapping("/docs")
    public void redirectToAnotherLink(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.sendRedirect("https://documenter.getpostman.com/view/32366655/2sA3kXELHZ");
    }

}
