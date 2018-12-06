package com.fean.seufinanceiro.services;

import com.fean.seufinanceiro.dtos.ChartDto;
import com.fean.seufinanceiro.dtos.CashFlowMonthlyDto;
import com.fean.seufinanceiro.models.Transaction;
import com.fean.seufinanceiro.models.enums.TypeTransaction;
import com.fean.seufinanceiro.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChartService {

    private final TransactionService transactionService;

    @Autowired
    public ChartService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public ChartDto findChartByYear(String year, Long userId) {
        ChartDto chartDto = new ChartDto();
        List<CashFlowMonthlyDto> cashFlowMonthlyDtoList = new ArrayList<>();

        for(int month = 0; month < 12; month++){
            CashFlowMonthlyDto cashFlowMonthlyDto = new CashFlowMonthlyDto(month);
            List<Transaction> transactions = transactionService
                                                    .showAllTransactionByYearMonth(year, String.valueOf(month), userId);

            transactions.forEach(transaction -> {
                if( transaction.getTypeTransaction().equals(TypeTransaction.INPUT)){
                    cashFlowMonthlyDto.setCashFlow(
                                            NumberUtils.formatDouble(cashFlowMonthlyDto.getCashFlow() +
                                                                           transaction.getValue()));
                }else {
                    cashFlowMonthlyDto.setCashFlow(
                                            NumberUtils.formatDouble(cashFlowMonthlyDto.getCashFlow() -
                                                                           transaction.getValue()));
                }
            });

            cashFlowMonthlyDtoList.add(cashFlowMonthlyDto);
        }

        chartDto.getCashFlowMonthlyDto().addAll(cashFlowMonthlyDtoList);
        return chartDto;
    }
}
