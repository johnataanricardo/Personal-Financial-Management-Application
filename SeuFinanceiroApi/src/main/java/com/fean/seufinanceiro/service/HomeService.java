package com.fean.seufinanceiro.service;

import com.fean.seufinanceiro.dto.HomeDto;
import com.fean.seufinanceiro.model.Movimentacao;
import com.fean.seufinanceiro.model.enums.TipoDespesa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HomeService {

    @Autowired
    private MovimentacaoService movimentacaoService;

    public HomeDto showMovimentacaoByMonthYear(String year, String month){

        HomeDto homeDto = new HomeDto();

        List<Movimentacao> movimentacaos = movimentacaoService.showAllDespesasByYearMonth(year, month);

        if (movimentacaos.isEmpty()) { return null; }

        movimentacaos.forEach(despesa -> {
            if (despesa.getTipoDespesa().equals(TipoDespesa.ENTRADA)){
                    homeDto.getEntrada().add(despesa);
                    homeDto.setTotalEntrada(homeDto.getTotalEntrada() + despesa.getValor());
            }else  {
                    homeDto.getSaida().add(despesa);
                    homeDto.setTotalSaida(homeDto.getTotalSaida() + despesa.getValor());
            }
        });

        homeDto.setFluxoCaixa(homeDto.getTotalEntrada() - homeDto.getTotalSaida());
        return homeDto;
    }


}
