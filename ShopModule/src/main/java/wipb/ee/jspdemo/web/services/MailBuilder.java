package wipb.ee.jspdemo.web.services;

import java.util.Date;

public class MailBuilder {
    private int clientId;
    private int productId;
    private int orderId;
    private int quantity;
    private double totalPrice;
    private int trackingNumber;
    private Date deliveryDate;

    public MailBuilder setClientId(int clientId) {
        this.clientId = clientId;
        return this;
    }

    public MailBuilder setProductId(int productId) {
        this.productId = productId;
        return this;
    }

    public MailBuilder setOrderId(int orderId){
        this.orderId = orderId;
        return this;
    }

    public MailBuilder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public MailBuilder setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }
    public MailBuilder setTrackingNumber(int trackingNumber) {
        this.trackingNumber = trackingNumber;
        return this;
    }

    public MailBuilder setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public String buildCancelledOrderBody() {
        String body = "Zamówienie zostało anulowane:\n"
                + "ID klienta: " + clientId + "\n"
                + "ID produktu: " + productId + "\n"
                + "Ilość: " + quantity + "\n"
                + "Łączna cena: " + totalPrice + "\n";
        return body;
    }
    public String buildNewOrderBody() {
        String body = "Dziękujemy za złożenie zamówienia:\n"
                + "ID klienta: " + clientId + "\n"
                + "ID produktu: " + productId + "\n"
                + "Ilość: " + quantity + "\n"
                + "Łączna cena: " + totalPrice + "\n";
        return body;
    }

    public String buildPaymentConfirmationBody() {
        String body = "Potwierdzenie płatności:\n"
                + "ID zamówienia: " + orderId + "\n"
                + "Kwota: " + totalPrice + "\n"
                + "Dziękujemy za dokonanie płatności!\n";

        return body;
    }

    public String buildShippingNotificationBody() {
        String body = "Powiadomienie o wysyłce:\n"
                + "ID zamówienia: " + orderId + "\n"
                + "Numer przesyłki: " + trackingNumber + "\n"
                + "Przewidywany czas dostawy: " + deliveryDate + "\n";

        return body;
    }
}