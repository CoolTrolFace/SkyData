package com.emre.skydata;

public class TransactionData {

    private double totalSellMoney = 0;
    private double totalBuyMoney = 0;
    private int sellAmount = 0;
    private int buyAmount = 0;

    public TransactionData(){

    }
    public TransactionData(double Smoney, int Samount, double Pmoney, int Pamount){
        this.totalSellMoney=Smoney;
        this.sellAmount=Samount;
        this.totalBuyMoney=Pmoney;
        this.buyAmount=Pamount;
    }

    public int getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(int buyAmount) {
        this.buyAmount = buyAmount;
    }

    public int getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(int sellAmount) {
        this.sellAmount = sellAmount;
    }

    public double getTotalBuyMoney() {
        return totalBuyMoney;
    }

    public void setTotalBuyMoney(double totalBuyMoney) {
        this.totalBuyMoney = totalBuyMoney;
    }

    public double getTotalSellMoney() {
        return totalSellMoney;
    }

    public void setTotalSellMoney(double totalSellMoney) {
        this.totalSellMoney = totalSellMoney;
    }
}
