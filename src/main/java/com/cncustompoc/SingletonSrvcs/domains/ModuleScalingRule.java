package com.cncustompoc.SingletonSrvcs.domains;


public class ModuleScalingRule {
    private int quantity=1;
    private int minQuantity=1;
    private int maxQuantity=42;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        if(maxQuantity<42&&maxQuantity>=0)
        this.maxQuantity = maxQuantity;
    }

    @Override
    public String toString() {
        return "ModuleScalingRule{" +
                "quantity=" + quantity +
                ", minQuantity=" + minQuantity +
                ", maxQuantity=" + maxQuantity +
                '}';
    }
}
