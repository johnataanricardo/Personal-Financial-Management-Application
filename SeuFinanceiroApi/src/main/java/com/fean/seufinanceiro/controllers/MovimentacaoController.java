package com.fean.seufinanceiro.controllers;

import com.fean.seufinanceiro.dto.MovimentacaoDto;
import com.fean.seufinanceiro.dto.FluxoDeCaixaDto;
import com.fean.seufinanceiro.model.Movimentacao;
import com.fean.seufinanceiro.model.Usuario;
import com.fean.seufinanceiro.model.enums.Meses;
import com.fean.seufinanceiro.model.enums.TipoDespesa;
import com.fean.seufinanceiro.responses.Response;
import com.fean.seufinanceiro.security.JwtUser;
import com.fean.seufinanceiro.service.MovimentacaoService;
import com.fean.seufinanceiro.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("movimentacoes")
@CrossOrigin(origins = "*")
public class MovimentacaoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovimentacaoController.class);

    @Autowired
    private MovimentacaoService movimentacaoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Response<FluxoDeCaixaDto>> getMovimentacoes(){

        LOGGER.info("Buscando todos dados de movimentações...");
        Response<FluxoDeCaixaDto> response = new Response<>();

        FluxoDeCaixaDto fluxoDeCaixaDto = new FluxoDeCaixaDto();
        fluxoDeCaixaDto.setMovimentacaos(movimentacaoService.showAllDespesas());
        fluxoDeCaixaDto.setFluxoCaixa(String.valueOf(movimentacaoService.calcFluxoCaixa(fluxoDeCaixaDto)));

        if (fluxoDeCaixaDto.getMovimentacaos() == null){
            LOGGER.info("Nenhum fluxo de caixia foi encontrado...");
            response.getErrors().add("Nenhum fluxo de caixa foi encontrado...");
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(fluxoDeCaixaDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<MovimentacaoDto>> getMovimentacaoById(@PathVariable("id") Long id){

        LOGGER.info("Buscando dados de movimentação pelo ID: ", id);
        Response<MovimentacaoDto> response = new Response<>();
        Movimentacao movimentacao = movimentacaoService.findDespesaById(id);

        if (movimentacao == null){
            LOGGER.info("Movimentacao não encontrada pelo ID: ", id);
            response.getErrors().add("Movimentacao não encontrada pelo ID: " + id);
            return ResponseEntity.badRequest().body(response);
        }
        response.setData(this.convertDespesaDto(movimentacao));
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<Response<String>> save(@Valid @RequestBody MovimentacaoDto movimentacaoDto,
                                                 @AuthenticationPrincipal JwtUser jwtUser,
                                                                     BindingResult result) {

        Response<String> response = new Response<>();

        if (result.hasErrors()){
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Optional<Usuario> usuario = usuarioService.findUsuarioById(jwtUser.getId());
        movimentacaoService.novoDespesa(convertDespesa(movimentacaoDto, usuario.get()));
        response.setData("Movimentacao salva com sucesso!!!");

        return ResponseEntity.ok(response);
    }


    @PutMapping("{id}")
    public ResponseEntity<Response<String>> update(@PathVariable("id") Long id,
                                                   @Valid @RequestBody MovimentacaoDto movimentacaoDto,
                                                   @AuthenticationPrincipal JwtUser jwtUser,
                                                   BindingResult result)    {

        movimentacaoDto.setId(String.valueOf(id));

        LOGGER.info("Atualizando movimentação: {}", movimentacaoDto.toString());

        Response<String> response = new Response<>();

        if (result.hasErrors()) {
            LOGGER.error("Erro validando movimentação: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        Optional<Usuario> usuario = usuarioService.findUsuarioById(jwtUser.getId());
        this.movimentacaoService.novoDespesa(convertDespesa(movimentacaoDto, usuario.get()));
        response.setData("Movimentação atualizada com sucesso!!!");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<String>> remove(@PathVariable("id") Long id){

        LOGGER.info("Removendo movimentação ID: {}", id);

        Response<String> response = new Response<>();

        Movimentacao movimentacao = this.movimentacaoService.findDespesaById(id);

        if (movimentacao == null){
            LOGGER.info("Erro ao remover devido a movimentação ID: {} ser inválido", id);
            response.getErrors().add("Erro ao remover a movimentação. Registro não encontrado para o id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.movimentacaoService.removeDespesa(id);
        response.setData("Movimentação removida com sucesso!");
        return ResponseEntity.ok(response);
    }

    private MovimentacaoDto convertDespesaDto(Movimentacao movimentacao) {
        return  new MovimentacaoDto(String.valueOf(movimentacao.getId()),
                               movimentacao.getDescricao(),
                               String.valueOf(movimentacao.getValor()),
                               String.valueOf(movimentacao.getTipoDespesa()),
                               movimentacao.getAno(),
                               String.valueOf(movimentacao.getMes()));
    }

    private Movimentacao convertDespesa(MovimentacaoDto movimentacaoDto, Usuario usuario) {
        Movimentacao movimentacao;
        if(movimentacaoDto.getId() != null){
            movimentacao = movimentacaoService.findDespesaById(Long.parseLong(movimentacaoDto.getId()));
        }else {
            movimentacao = new Movimentacao();
        }

        movimentacao.setDescricao(movimentacaoDto.getDescricao());
        movimentacao.setValor(Double.parseDouble(movimentacaoDto.getValor()));
        movimentacao.setTipoDespesa(TipoDespesa.valueOf(movimentacaoDto.getTipoDespesa()));
        movimentacao.setMes(Meses.valueOf(movimentacaoDto.getMes()));
        movimentacao.setAno(movimentacaoDto.getAno());
        movimentacao.setUsuario(usuario);
        return movimentacao;
    }

}
