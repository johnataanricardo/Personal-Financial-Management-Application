package com.fean.seufinanceiro.controllers;

import com.fean.seufinanceiro.dto.ChartDto;
import com.fean.seufinanceiro.responses.Response;
import com.fean.seufinanceiro.security.JwtUser;
import com.fean.seufinanceiro.service.ChartService;
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
@RequestMapping("chart")
public class ChartController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChartController.class);

    private ChartService chartService;


    @Autowired
    public ChartController(ChartService chartService) {
        this.chartService = chartService;
    }

    @GetMapping("{year}")
    public ResponseEntity<Response<ChartDto>> getHome(@PathVariable("year")  String year,
                                                     @AuthenticationPrincipal JwtUser jwtUser){

        LOGGER.info("Buscando dados do gráfico...");
        Response<ChartDto> response = new Response<>();

        ChartDto chartDto = chartService.findChartByYear(year, jwtUser.getId());

        if (chartDto == null){
            LOGGER.info("Nenhum gráfico foi encontrado...");
            response.getErrors().add("Nenhum gráfico foi encontrado...");
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(chartDto);
        return ResponseEntity.ok(response);
    }

}
