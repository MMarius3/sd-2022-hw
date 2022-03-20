package service.TransferMoneyBetweenAccounts.exception;

public class InvalidTransactionException extends Exception {

    private String exceptionMessage;

    public InvalidTransactionException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }
}
