package org.spk.model;

public class CustomerFeedback {

    public String customerName;
    public int productQuality;
    public int serviceSpeed;
    public int price;
    public int customerService;

    public CustomerFeedback(String customerName, int productQuality, int serviceSpeed, int price, int customerService) {
        this.customerName = customerName;
        this.productQuality = productQuality;
        this.serviceSpeed = serviceSpeed;
        this.price = price;
        this.customerService = customerService;
    }
}
