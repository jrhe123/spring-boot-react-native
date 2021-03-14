package com.example.demo.service;

import com.example.demo.model.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> findAllTransactions();

    List<Transaction> findAllTransactionsByParams(int limit, int offset);

    Transaction findById(Long id);

    Transaction saveTransaction(Transaction transaction);

    Transaction updateTransaction(Transaction transaction);

    void deleteTransaction(Long transactionId);

    Long numberOfTransactions();

}
