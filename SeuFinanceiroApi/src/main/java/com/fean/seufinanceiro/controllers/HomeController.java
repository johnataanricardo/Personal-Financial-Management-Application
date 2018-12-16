package com.fean.seufinanceiro.controllers;

import com.fean.seufinanceiro.dtos.HomeDto;
import com.fean.seufinanceiro.responses.Response;
import com.fean.seufinanceiro.security.JwtUser;
import com.fean.seufinanceiro.services.HomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("home")
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    private final HomeService homeService;

    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<Response<HomeDto>> getHome(@PathVariable("year")  String year,
                                                     @PathVariable("month") String month,
                                                     @AuthenticationPrincipal JwtUser jwtUser){

        LOGGER.info("Searching transaction data...");
        Response<HomeDto> response = new Response<>();

        HomeDto homeDto = homeService.showMovimentacaoByMonthYear(year, month, jwtUser.getId());

        if (homeDto == null ||  (homeDto.getInput() == null && homeDto.getOuput() == null)){
            LOGGER.info("No transaction found...");
        }

        response.setData(homeDto);
        return ResponseEntity.ok(response);
    }

}
