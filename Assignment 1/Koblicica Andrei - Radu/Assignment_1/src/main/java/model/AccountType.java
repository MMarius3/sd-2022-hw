package model;

public enum AccountType {
    CURRENT("Current"),
    SAVINGS("Savings"),
    RECURRING_DEPOSIT("Recurring Deposit"),
    FIXED_DEPOSIT("Fixed Deposit");


    private String text;

    AccountType(String text){
        this.text=text;
    }

    public String getText(){
        return this.text;
    }

    public static AccountType fromString(String text) {
        for (AccountType accountType : AccountType.values()) {
            if (accountType.text.equalsIgnoreCase(text)) {
                return accountType;
            }
        }
        return null;
    }

    @Override
    public String toString(){
      return text;
    }
}
