package com.fean.seufinanceiro.service;

import com.fean.seufinanceiro.dto.ChartDto;
import com.fean.seufinanceiro.dto.FluxoDeCaixaMensalDto;
import com.fean.seufinanceiro.model.Movimentacao;
import com.fean.seufinanceiro.model.enums.Meses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Service
public class ChartService {

    private MovimentacaoService movimentacaoService;

    @Autowired
    public ChartService(MovimentacaoService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }


    public ChartDto findChartByYear(String year, Long userId) {
        ChartDto chartDto = new ChartDto();
        List<FluxoDeCaixaMensalDto> fluxoDeCaixaMensalDtos = new ArrayList<>();

        List<Meses> months = Arrays.asList(Meses.values());

        months.forEach( month ->{
            FluxoDeCaixaMensalDto fluxoDeCaixaMensalDto = new FluxoDeCaixaMensalDto(month.ordinal());

            List<Movimentacao> movimentacaos = movimentacaoService.showAllDespesasByYearMonth(year, month.name(), userId);
            movimentacaos.forEach( movimentacao -> {
                fluxoDeCaixaMensalDto.setFluxoDeCaixa(fluxoDeCaixaMensalDto.getFluxoDeCaixa() + movimentacao.getValor());
            });

            fluxoDeCaixaMensalDtos.add(fluxoDeCaixaMensalDto);
        });

        chartDto.getFluxoDeCaixaMensalDtos().addAll(fluxoDeCaixaMensalDtos);
        return chartDto;
    }
}
