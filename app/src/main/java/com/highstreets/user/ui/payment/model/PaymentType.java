package com.highstreets.user.ui.payment.model;

public class PaymentType {
    private int id;
    private String type;
    private int typeImage;
    private boolean isSelected;

    public PaymentType() {
    }

    public PaymentType(int id, String type, int typeImage) {
        this.id = id;
        this.type = type;
        this.typeImage = typeImage;
    }

    public PaymentType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTypeImage() {
        return typeImage;
    }

    public void setTypeImage(int typeImage) {
        this.typeImage = typeImage;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
