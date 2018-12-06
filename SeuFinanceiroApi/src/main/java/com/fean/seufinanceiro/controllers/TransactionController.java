package com.fean.seufinanceiro.controllers;

import com.fean.seufinanceiro.dtos.TransactionDto;
import com.fean.seufinanceiro.models.Category;
import com.fean.seufinanceiro.models.Transaction;
import com.fean.seufinanceiro.models.User;
import com.fean.seufinanceiro.models.enums.TypeTransaction;
import com.fean.seufinanceiro.responses.Response;
import com.fean.seufinanceiro.security.JwtUser;
import com.fean.seufinanceiro.services.CategoryService;
import com.fean.seufinanceiro.services.TransactionService;
import com.fean.seufinanceiro.services.UserService;
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
public class TransactionController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);

    private final TransactionService transactionService;

    private final UserService userService;

    private final CategoryService categoryService;

    @Autowired
    public TransactionController(TransactionService transactionService,
                                 UserService userService,
                                 CategoryService categoryService) {

        this.transactionService = transactionService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<Response<List<TransactionDto>>> getTransaction(@AuthenticationPrincipal JwtUser jwtUser){

        LOGGER.info("Buscando todos os dados de movimentações do usuário ID: ", jwtUser.getId());

        Response<List<TransactionDto>> response = new Response<>();

        List<TransactionDto> transactionDtoList = new ArrayList<>();

        transactionService.showAllTransactionsByUserId(jwtUser.getId()).forEach(transaction -> {
            transactionDtoList.add(convertTransactionDto(transaction));
        });

        if (transactionDtoList.isEmpty()){
            LOGGER.info("Nenhum fluxo de caixia foi encontrado...");
        }

        response.setData(transactionDtoList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<TransactionDto>> getTransactionById(@PathVariable("id") Long id,
                                                                       @AuthenticationPrincipal JwtUser jwtUser){

        LOGGER.info("Buscando dados de movimentação pelo ID: ", id);

        Response<TransactionDto> response = new Response<>();
        Transaction transaction = transactionService.showTransactionByIdByUserId(id, jwtUser.getId());

        if (transaction == null){
            LOGGER.info("Transaction não encontrada pelo ID: ", id);
            response.getErrors().add("Transaction não encontrada pelo ID: " + id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.convertTransactionDto(transaction));
        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<Response<String>> save(@Valid @RequestBody TransactionDto transactionDto,
                                                 @AuthenticationPrincipal JwtUser jwtUser,
                                                 BindingResult result) {

        Response<String> response = new Response<>();

        if (result.hasErrors()){
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        transactionService.newTransaction(convertTransaction(transactionDto, jwtUser));
        response.setData("Transaction salva com sucesso!!!");

        return ResponseEntity.ok(response);
    }


    @PutMapping("{id}")
    public ResponseEntity<Response<String>> update(@PathVariable("id") Long id,
                                                   @Valid @RequestBody TransactionDto transactionDto,
                                                   @AuthenticationPrincipal JwtUser jwtUser,
                                                   BindingResult result)    {

        transactionDto.setId(String.valueOf(id));

        LOGGER.info("Atualizando movimentação: {}", transactionDto.toString());

        Response<String> response = new Response<>();

        if (result.hasErrors()) {
            LOGGER.error("Erro validando movimentação: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        this.transactionService.newTransaction(convertTransaction(transactionDto, jwtUser));
        response.setData("Movimentação atualizada com sucesso!!!");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<String>> remove(@PathVariable("id") Long id,
                                                   @AuthenticationPrincipal JwtUser jwtUser){

        LOGGER.info("Removendo movimentação ID:"+ id +" do usuário ID: {}", jwtUser.getId());

        Response<String> response = new Response<>();

        Transaction transaction = this.transactionService.showTransactionByIdByUserId(id, jwtUser.getId());

        if (transaction == null){
            LOGGER.info("Erro ao remover devido a movimentação ID: {} ser inválido", id);
            response.getErrors().add("Erro ao remover a movimentação. Registro não encontrado para o id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.transactionService.removeTransaction(id);
        response.setData("Movimentação removida com sucesso!");
        return ResponseEntity.ok(response);
    }

    private TransactionDto convertTransactionDto(Transaction transaction) {
        return  new TransactionDto(String.valueOf(transaction.getId()),
                         String.valueOf(transaction.getCategory() != null ? transaction.getCategory().getId() : ""),
                         transaction.getCategory() != null ? transaction.getCategory().getDescription() : "",
                         transaction.getDescription(),
                         String.valueOf(transaction.getValue()),
                         String.valueOf(transaction.getTypeTransaction()),
                         transaction.getYear(),
                         String.valueOf(transaction.getMonth()));
    }

    private Transaction convertTransaction(TransactionDto transactionDto, JwtUser jwtUser) {
        Transaction transaction;

        if(transactionDto.getId() != null){
            transaction = transactionService.showTransactionByIdByUserId(Long.parseLong(transactionDto.getId()),
                                                                         jwtUser.getId());
        }else {
            transaction = new Transaction();
            Optional<User> user = userService.findUsuarioById(jwtUser.getId());
            user.ifPresent(transaction::setUser);
        }

        transaction.setDescription(transactionDto.getDescription());
        transaction.setValue(Double.valueOf(transactionDto.getValue()));
        transaction.setTypeTransaction(TypeTransaction.valueOf(transactionDto.getTypeTransaction()));
        transaction.setMonth(Integer.parseInt(transactionDto.getMonth()));
        transaction.setYear(transactionDto.getYear());

        if (transactionDto.getCategoryId() != null) {
            Optional<Category> category = categoryService.showCategoryById(Long.valueOf(transactionDto.getCategoryId()));
            if (category.isPresent()) {
                transaction.setCategory(category.get());
                category.get().setTransaction(transaction);
            }
        }

        return transaction;
    }
}
