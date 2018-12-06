package com.fean.seufinanceiro.repositorys;

import com.fean.seufinanceiro.models.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> findByYearAndMonthAndUserId(String year, Integer month, Long userId);
    List<Transaction> findAllByUserId(Long userId);
    Transaction findByIdAndUserId(Long transactionId, Long userId);

}
