package me.hashcode.dawadeals.data.model.trade;

import java.util.List;

public class LatestTransactionsResponse {
    int sucess;
    String message;
    List<Transaction> transactions;

    public LatestTransactionsResponse() {
    }

    public LatestTransactionsResponse(int sucess, String message, List<Transaction> transactions) {
        this.sucess = sucess;
        this.message = message;
        this.transactions = transactions;
    }

    public static LatestTransactionsResponse getDummy() {
        return new LatestTransactionsResponse(1,"return data", Transaction.getDummy());
    }

    public int getSucess() {
        return sucess;
    }

    public void setSucess(int sucess) {
        this.sucess = sucess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
