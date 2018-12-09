package com.fean.seufinanceiro.services;

import com.fean.seufinanceiro.dtos.HomeDto;
import com.fean.seufinanceiro.dtos.TransactionDto;
import com.fean.seufinanceiro.models.Transaction;
import com.fean.seufinanceiro.models.enums.TypeTransaction;
import com.fean.seufinanceiro.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    private TransactionService transactionService;

    @Autowired
    public HomeService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public HomeDto showMovimentacaoByMonthYear(String year, String month, Long userId) {

        HomeDto homeDto = new HomeDto();

        List<Transaction> transactions = transactionService.showAllTransactionByYearMonth(year, month, userId);

        if (transactions.isEmpty()) {
            return null;
        }

        transactions.forEach(transaction -> {
            if (transaction.getTypeTransaction().equals(TypeTransaction.INPUT)) {
                homeDto.getInput().add(convertMovimentacaoDto(transaction));
                homeDto.setTotalInput(homeDto.getTotalInput() + transaction.getValue());
            } else {
                homeDto.getOuput().add(convertMovimentacaoDto(transaction));
                homeDto.setTotalOutput(homeDto.getTotalOutput() + transaction.getValue());
            }
        });

        homeDto.setCashFlow(NumberUtils.formatDouble(homeDto.getTotalInput() - homeDto.getTotalOutput()));
        return homeDto;
    }

    private TransactionDto convertMovimentacaoDto(Transaction transaction) {
        return new TransactionDto(String.valueOf(transaction.getId()),
                String.valueOf(transaction.getCategory() != null ? transaction.getCategory().getId() : ""),
                transaction.getCategory() != null ? transaction.getCategory().getDescription() : "",
                transaction.getDescription(),
                String.valueOf(transaction.getValue()),
                String.valueOf(transaction.getTypeTransaction()),
                transaction.getYear(),
                String.valueOf(transaction.getMonth()));
    }

}
