package controller;

public abstract class SessionController {
    private Long loggedInUser;

    public SessionController(){
        loggedInUser = -1L;
    }

    public Long getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(Long loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}
