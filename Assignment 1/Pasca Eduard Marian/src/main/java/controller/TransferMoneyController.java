package controller;

import view.TransferMoneyView;

import java.sql.Connection;

public class TransferMoneyController {
    private final TransferMoneyView transferMoneyView;
    private final Connection connection;

    public TransferMoneyController(TransferMoneyView transferMoneyView, Connection connection) {
        this.transferMoneyView = transferMoneyView;
        this.connection = connection;
    }
}
