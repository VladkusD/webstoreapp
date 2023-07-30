package bg.webapp.shop.service;

public class NotificationAlert {
    String message;
    public NotificationAlert(String message){
        this.message=message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
