package org.example.model;

import org.example.model.building.Material;

public class Trade {
    private final int id;
    private final Material material;
    private final int amountMaterial;
    private final int price;
    private final String requestMessage;
    private String acceptMessage = null;
    private final Empire empireRequester;
    private final Empire toEmpire;

    public Trade(int id, Material material, int amountMaterial, int price, String requestMessage, Empire empireRequester, Empire toEmpire) {
        this.id = id;
        this.material = material;
        this.amountMaterial = amountMaterial;
        this.price = price;
        this.requestMessage = requestMessage;
        this.empireRequester = empireRequester;
        this.toEmpire = toEmpire;
    }

    public int getId() {
        return id;
    }

    public Empire getEmpireRequester() {
        return empireRequester;
    }

    public Empire getToEmpire() {
        return toEmpire;
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
