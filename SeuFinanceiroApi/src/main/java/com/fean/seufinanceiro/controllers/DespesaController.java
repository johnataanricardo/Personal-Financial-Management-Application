package com.fean.seufinanceiro.controllers;


import com.fean.seufinanceiro.dto.DespesaDto;
import com.fean.seufinanceiro.dto.FluxoDeCaixaDto;
import com.fean.seufinanceiro.model.Despesa;
import com.fean.seufinanceiro.model.enums.Meses;
import com.fean.seufinanceiro.model.enums.TipoDespesa;
import com.fean.seufinanceiro.responses.Response;
import com.fean.seufinanceiro.service.DespesaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@Controller
@RequestMapping("despesas")
public class DespesaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoriaController.class);

    @Autowired
    private DespesaService despesaService;


    @GetMapping
    public ResponseEntity<Response<FluxoDeCaixaDto>> getDespesas(){
        LOGGER.info("Buscando todos dados de despesa...");
        Response<FluxoDeCaixaDto> response = new Response<>();
       // List<Despesa> despesas = despesaService.despesas();
        FluxoDeCaixaDto fluxoDeCaixaDto = despesaService.fluxoDeCaixaDto();

        if (fluxoDeCaixaDto == null){
            LOGGER.info("Nenhum fluxo de caixia foi encontrado...");
            response.getErrors().add("Nenhum fluxo de caixa foi encontrado...");
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(fluxoDeCaixaDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<DespesaDto>> getDespesaById(@PathVariable("id") Long id){
        LOGGER.info("Buscando dados de despesa pelo ID: ", id);
        Response<DespesaDto> response = new Response<>();
        Despesa despesa = despesaService.finById(id);

        if (despesa == null){
            LOGGER.info("Despesa não encontrada pelo ID: ", id);
            response.getErrors().add("Despesa não encontrada pelo ID: " + id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.convertDespesaDto(despesa));
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<Response<String>> save(@Valid @RequestBody
                                                         DespesaDto despesaDto,
                                                 BindingResult result) {

        Response<String> response = new Response<>();

        if (result.hasErrors()){
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        despesaService.add(convertDespesa(despesaDto));
        response.setData("Categoria salvo com sucesso!!!");

        return ResponseEntity.ok(response);
    }


    @PutMapping("{id}")
    public ResponseEntity<Response<String>> update(@PathVariable("id") Long id,
                                                   @Valid @RequestBody DespesaDto despesaDto,
                                                   BindingResult result) throws ParseException {

        despesaDto.setId(String.valueOf(id));

        LOGGER.info("Atualizando despesa: {}", despesaDto.toString());

        Response<String> response = new Response<>();
        if (result.hasErrors()) {
            LOGGER.error("Erro validando usuário: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.despesaService.update(convertDespesa(despesaDto));
        response.setData("Despesa atualizada com sucesso!!!");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<String>> remove(@PathVariable("id") Long id){
        LOGGER.info("Removendo despesa ID: {}", id);
        Response<String> response = new Response<>();
        Despesa categoria = this.despesaService.finById(id);

        if (categoria == null){
            LOGGER.info("Erro ao remover devido a despesa ID: {} ser inválido", id);
            response.getErrors().add("Erro ao remover a despesa. Registro não encontrado para o id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.despesaService.remove(id);
        response.setData("Despesa removida com sucesso!");
        return ResponseEntity.ok(response);
    }

    private DespesaDto convertDespesaDto(Despesa despesa) {
        return  new DespesaDto(despesa.getDescricao(),
                               String.valueOf(despesa.getValor()),
                               String.valueOf(despesa.getTipoDespesa()),
                               despesa.getAno(),
                               String.valueOf(despesa.getMes()));
    }

    private Despesa convertDespesa(DespesaDto despesaDto) {
        Despesa despesa = new Despesa();
        despesa.setId(Long.parseLong(despesaDto.getId()));
        despesa.setDescricao(despesaDto.getDescricao());
        despesa.setValor(Double.parseDouble(despesaDto.getValor()));
        despesa.setTipoDespesa(TipoDespesa.valueOf(despesaDto.getTipoDespesa()));
        despesa.setMes(Meses.valueOf(despesaDto.getMes()));
        despesa.setAno(despesaDto.getAno());
        return despesa;
    }

}
