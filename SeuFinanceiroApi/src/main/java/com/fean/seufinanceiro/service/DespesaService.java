package com.fean.seufinanceiro.service;

import com.fean.seufinanceiro.dto.FluxoDeCaixaDto;
import com.fean.seufinanceiro.model.Despesa;
import com.fean.seufinanceiro.model.enums.Meses;
import com.fean.seufinanceiro.model.enums.TipoDespesa;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DespesaService {

    private static List<Despesa> despesas;

    private static FluxoDeCaixaDto fluxoDeCaixaDto;

    public DespesaService(){
        criarListDespesa();
    }

    private List<Despesa> criarListDespesa(){
        if (despesas == null) {
            fluxoDeCaixaDto = new FluxoDeCaixaDto();
            despesas = new LinkedList<>();
            despesas.add(new Despesa(1L, "Salário", 2000.00, TipoDespesa.ENTRADA, "2018", Meses.JANEIRO));
            despesas.add(new Despesa(2L, "Rendimentos", 120.00, TipoDespesa.ENTRADA, "2018", Meses.JANEIRO));
            despesas.add(new Despesa(3L, "Freelancer", 1000.00, TipoDespesa.ENTRADA, "2018", Meses.JANEIRO));

            despesas.add(new Despesa(4L, "Inglês", 300.00, TipoDespesa.SAIDA, "2018", Meses.JANEIRO));
            despesas.add(new Despesa(5L, "Faculdade", 650.00, TipoDespesa.SAIDA, "2018", Meses.JANEIRO));
            despesas.add(new Despesa(6L, "Almoço", 20.34, TipoDespesa.SAIDA, "2018", Meses.JANEIRO));
            despesas.add(new Despesa(7L, "Tênis", 200.00, TipoDespesa.SAIDA, "2018", Meses.JANEIRO));
            despesas.add(new Despesa(8L, "Calça", 120.00, TipoDespesa.SAIDA, "2018", Meses.JANEIRO));
            fluxoDeCaixaDto.setDespesas(despesas);
            calcFluxoCaixa(fluxoDeCaixaDto);
        }

        return despesas;
    }

    public void add(Despesa despesa){
        int posicao = despesas.size();
        despesa.setId(posicao + 1L);
        despesas.add(despesa);
    }

    public void remove(Long id){
        despesas.removeIf(despesa -> despesa.getId().equals(id));
    }

    public void update(Despesa despesa){
        despesas.stream().filter( (desp) -> desp.getId().equals(despesa.getId()))
                .forEach( (desp) -> {
                    desp.setDescricao(despesa.getDescricao());
                    desp.setValor(despesa.getValor());
                    desp.setTipoDespesa(despesa.getTipoDespesa());
                    desp.setMes(desp.getMes());
                    desp.setAno(desp.getAno());
                });
    }

    public List<Despesa> despesas(){
        return despesas;
    }

    public FluxoDeCaixaDto fluxoDeCaixaDto(){
        return fluxoDeCaixaDto;
    }

    public Despesa finById(Long id) {
        return despesas().stream()
                .filter((categoria) -> categoria.getId().equals(id))
                .collect(Collectors.toList())
                .get(0);
    }

    private void calcFluxoCaixa(FluxoDeCaixaDto fluxoDeCaixaDto){

        Double depSaida = 0.0;
        Double depEntrada = 0.0;

        for (Despesa despesa: fluxoDeCaixaDto.getDespesas()) {
            if (despesa.getTipoDespesa().equals(TipoDespesa.ENTRADA)){
                depEntrada +=  despesa.getValor();
            }else{
                depSaida +=  despesa.getValor();
            }
        }

        fluxoDeCaixaDto.setFluxoCaixa(String.valueOf(depEntrada - depSaida));
    }

}
