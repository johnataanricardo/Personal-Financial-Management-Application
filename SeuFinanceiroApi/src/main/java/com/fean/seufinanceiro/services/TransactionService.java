package com.fean.seufinanceiro.services;

import com.fean.seufinanceiro.models.Transaction;
import com.fean.seufinanceiro.repositorys.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> showAllTransactionsByUserId(Long id) {
        return transactionRepository.findAllByUserId(id);
    }

    public Transaction showTransactionByIdByUserId(Long transactionId, Long idUser) {
        return transactionRepository.findByIdAndUserId(transactionId, idUser);
    }

    public List<Transaction> showAllTransactionByYearMonth(String year, String month, Long userId) {
        return transactionRepository.findByYearAndMonthAndUserId(year, Integer.parseInt(month), userId);
    }

    public void newTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    public void removeTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

}
