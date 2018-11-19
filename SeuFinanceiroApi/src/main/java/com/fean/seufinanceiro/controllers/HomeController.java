package com.fean.seufinanceiro.controllers;

import com.fean.seufinanceiro.dto.HomeDto;
import com.fean.seufinanceiro.responses.Response;
import com.fean.seufinanceiro.security.JwtUser;
import com.fean.seufinanceiro.service.HomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("home")
@CrossOrigin(origins = "*")
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    private HomeService homeService;


    @Autowired
    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/{year}/{month}")
    public ResponseEntity<Response<HomeDto>> getHome(@PathVariable("year")  String year,
                                                     @PathVariable("month") String month,
                                                     @AuthenticationPrincipal JwtUser jwtUser){

        LOGGER.info("Buscando dados da movimentação...");
        Response<HomeDto> response = new Response<>();

        HomeDto homeDto = homeService.showMovimentacaoByMonthYear(year, month, jwtUser.getId());

        if (homeDto == null ||  (homeDto.getEntrada() == null && homeDto.getSaida() == null)){
            LOGGER.info("Nenhuma movimentação foi encontrada...");
        }

        response.setData(homeDto);
        return ResponseEntity.ok(response);
    }

}
