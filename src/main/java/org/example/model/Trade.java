package org.example.model;

import org.example.model.building.Material;

public class Trade {
    private final int id;
    private final Material material;
    private final int amountMaterial;
    private final int price;
    private final String requestMessage;
    private String acceptMessage = null;
    private final Empire empire;

    public Trade(int id, Material material, int amountMaterial, int price, String requestMessage, Empire empire) {
        this.id = id;
        this.material = material;
        this.amountMaterial = amountMaterial;
        this.price = price;
        this.requestMessage = requestMessage;
        this.empire = empire;
    }

    public int getId() {
        return id;
    }

    public Empire getEmpire() {
        return empire;
    }

    public Material getMaterial() {
        return material;
    }

    public int getAmountMaterial() {
        return amountMaterial;
    }

    public int getPrice() {
        return price;
    }

    public String getAcceptMessage() {
        return acceptMessage;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setAcceptMessage(String acceptMessage) {
        this.acceptMessage = acceptMessage;
    }
}
