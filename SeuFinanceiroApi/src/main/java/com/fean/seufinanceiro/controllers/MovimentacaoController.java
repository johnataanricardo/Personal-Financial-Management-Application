package com.fean.seufinanceiro.controllers;

import com.fean.seufinanceiro.dto.MovimentacaoDto;
import com.fean.seufinanceiro.model.Categoria;
import com.fean.seufinanceiro.model.Movimentacao;
import com.fean.seufinanceiro.model.Usuario;
import com.fean.seufinanceiro.model.enums.TipoDespesa;
import com.fean.seufinanceiro.responses.Response;
import com.fean.seufinanceiro.security.JwtUser;
import com.fean.seufinanceiro.service.CategoriaService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("movimentacoes")
@CrossOrigin(origins = "*")
public class MovimentacaoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MovimentacaoController.class);

    private MovimentacaoService movimentacaoService;

    private UsuarioService usuarioService;

    private CategoriaService categoriaService;

    @Autowired
    public MovimentacaoController(MovimentacaoService movimentacaoService, UsuarioService usuarioService, CategoriaService categoriaService) {
        this.movimentacaoService = movimentacaoService;
        this.usuarioService = usuarioService;
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<Response<List<MovimentacaoDto>>> getMovimentacoes(@AuthenticationPrincipal JwtUser jwtUser){

        LOGGER.info("Buscando todos os dados de movimentações do usuário ID: ", jwtUser.getId());

        Response<List<MovimentacaoDto>> response = new Response<>();

        List<MovimentacaoDto> movimentacaoesDto = new ArrayList<>();

        movimentacaoService.showAllMovimentacoesByUser(jwtUser.getId()).forEach( movimentacao -> {
            movimentacaoesDto.add(convertMovimentacaoDto(movimentacao));
        });

        if (movimentacaoesDto.isEmpty()){
            LOGGER.info("Nenhum fluxo de caixia foi encontrado...");
        }

        response.setData(movimentacaoesDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<MovimentacaoDto>> getMovimentacaoById(@PathVariable("id") Long id,
                                                                         @AuthenticationPrincipal JwtUser jwtUser){

        LOGGER.info("Buscando dados de movimentação pelo ID: ", id);

        Response<MovimentacaoDto> response = new Response<>();
        Movimentacao movimentacao = movimentacaoService.showMovimentacaoByIdByUserId(id, jwtUser.getId());

        if (movimentacao == null){
            LOGGER.info("Movimentacao não encontrada pelo ID: ", id);
            response.getErrors().add("Movimentacao não encontrada pelo ID: " + id);
            return ResponseEntity.badRequest().body(response);
        }
        response.setData(this.convertMovimentacaoDto(movimentacao));
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

        movimentacaoService.novoDespesa(convertDespesa(movimentacaoDto, jwtUser));
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

        this.movimentacaoService.novoDespesa(convertDespesa(movimentacaoDto, jwtUser));
        response.setData("Movimentação atualizada com sucesso!!!");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<String>> remove(@PathVariable("id") Long id,
                                                   @AuthenticationPrincipal JwtUser jwtUser){

        LOGGER.info("Removendo movimentação ID:"+ id +" do usuário ID: {}", jwtUser.getId());

        Response<String> response = new Response<>();

        Movimentacao movimentacao = this.movimentacaoService.showMovimentacaoByIdByUserId(id, jwtUser.getId());

        if (movimentacao == null){
            LOGGER.info("Erro ao remover devido a movimentação ID: {} ser inválido", id);
            response.getErrors().add("Erro ao remover a movimentação. Registro não encontrado para o id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.movimentacaoService.removeDespesa(id);
        response.setData("Movimentação removida com sucesso!");
        return ResponseEntity.ok(response);
    }

    private MovimentacaoDto convertMovimentacaoDto(Movimentacao movimentacao) {
        return  new MovimentacaoDto(String.valueOf(movimentacao.getId()),
                               String.valueOf(movimentacao.getCategoria() != null ? movimentacao.getCategoria().getId() : ""),
                               movimentacao.getCategoria() != null ? movimentacao.getCategoria().getDescricao() : "",
                               movimentacao.getDescricao(),
                               String.valueOf(movimentacao.getValor()),
                               String.valueOf(movimentacao.getTipoDespesa()),
                               movimentacao.getAno(),
                               String.valueOf(movimentacao.getMes()));
    }

    private Movimentacao convertDespesa(MovimentacaoDto movimentacaoDto, JwtUser jwtUser) {
        Movimentacao movimentacao;
        if(movimentacaoDto.getId() != null){
            movimentacao = movimentacaoService.showMovimentacaoByIdByUserId(Long.parseLong(movimentacaoDto.getId()), jwtUser.getId());
        }else {
            movimentacao = new Movimentacao();
            Optional<Usuario> usuario = usuarioService.findUsuarioById(jwtUser.getId());
            usuario.ifPresent(movimentacao::setUsuario);
        }

        movimentacao.setDescricao(movimentacaoDto.getDescricao());
        movimentacao.setValor(Double.valueOf(movimentacaoDto.getValor()));
        movimentacao.setTipoDespesa(TipoDespesa.valueOf(movimentacaoDto.getTipoDespesa()));
        movimentacao.setMes(Integer.parseInt(movimentacaoDto.getMes()));
        movimentacao.setAno(movimentacaoDto.getAno());
        if (movimentacaoDto.getIdCategoria() != null) {
            Categoria categoria = categoriaService.showCategoriaById(Long.valueOf(movimentacaoDto.getIdCategoria()));
            if (categoria != null) {
                movimentacao.setCategoria(categoria);
            }
        }
        return movimentacao;

    }

}
