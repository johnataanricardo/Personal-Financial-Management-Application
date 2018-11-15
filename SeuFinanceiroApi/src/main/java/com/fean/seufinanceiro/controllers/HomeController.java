package com.fean.seufinanceiro.controllers;

import com.fean.seufinanceiro.dto.HomeDto;
import com.fean.seufinanceiro.responses.Response;
import com.fean.seufinanceiro.service.HomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("home")
@CrossOrigin(origins = "*")
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private HomeService homeService;

    @GetMapping("/{year}/{month}")
    public ResponseEntity<Response<HomeDto>> getHome(@PathVariable("year")  String year,
                                                     @PathVariable("month") String month){

        LOGGER.info("Buscando dados da movimentação...");
        Response<HomeDto> response = new Response<>();

        HomeDto homeDto = homeService.showMovimentacaoByMonthYear(year, month);

            if ( homeDto == null ||  (homeDto.getEntrada() == null && homeDto.getSaida() == null)){
            LOGGER.info("Nenhuma movimentação foi encontrada...");
            response.getErrors().add("Nenhuma movimentação foi encontrada...");
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(homeDto);
        return ResponseEntity.ok(response);
    }

}
