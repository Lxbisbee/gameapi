package com.lauren.gameapi.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class GamePricing {

    private int id;
    private String consoleName;
    private String productName;
    private double loosePrice;
    private double cibPrice;
    private double newPrice;
    private double retailLooseBuy;
    private double retailLooseSell;
    private double retailCibBuy;
    private double retailCibSell;
    private double retailNewBuy;
    private double retailNewSell;
    private double gameStopPrice;
    private double gameStopTradePrice;

    public GamePricing(){
    }

    public GamePricing(int id, String consoleName, String productName, double loosePrice, double cibPrice, 
    		double newPrice, double retailLooseBuy, double retailLooseSell, double retailCibBuy, double retailCibSell, 
    		double retailNewBuy, double retailNewSell, double gameStopPrice, double gameStopTradePrice) {
        this.id = id;
        this.consoleName = consoleName;
        this.productName = productName;
        this.loosePrice = loosePrice;
        this.cibPrice = cibPrice;
        this.newPrice = newPrice;
        this.retailLooseBuy = retailLooseBuy;
        this.retailLooseSell = retailLooseSell;
        this.retailCibBuy = retailCibBuy;
        this.retailCibSell = retailCibSell;
        this.retailNewBuy = retailNewBuy;
        this.retailNewSell = retailNewSell;
        this.gameStopPrice = gameStopPrice;
        this.gameStopTradePrice = gameStopTradePrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConsoleName() {
        return consoleName;
    }

    public void setConsoleName(String consoleName) {
        this.consoleName = consoleName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getLoosePrice() {
        return loosePrice;
    }

    public void setLoosePrice(double loosePrice) {
        this.loosePrice = loosePrice;
    }

    public double getCibPrice() {
        return cibPrice;
    }

    public void setCibPrice(double cibPrice) {
        this.cibPrice = cibPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public double getRetailLooseBuy() {
        return retailLooseBuy;
    }

    public void setRetailLooseBuy(double retailLooseBuy) {
        this.retailLooseBuy = retailLooseBuy;
    }

    public double getRetailLooseSell() {
        return retailLooseSell;
    }

    public void setRetailLooseSell(double retailLooseSell) {
        this.retailLooseSell = retailLooseSell;
    }

    public double getRetailCibBuy() {
        return retailCibBuy;
    }

    public void setRetailCibBuy(double retailCibBuy) {
        this.retailCibBuy = retailCibBuy;
    }

    public double getRetailCibSell() {
        return retailCibSell;
    }

    public void setRetailCibSell(double retailCibSell) {
        this.retailCibSell = retailCibSell;
    }

    public double getRetailNewBuy() {
        return retailNewBuy;
    }

    public void setRetailNewBuy(double retailNewBuy) {
        this.retailNewBuy = retailNewBuy;
    }

    public double getRetailNewSell() {
        return retailNewSell;
    }

    public void setRetailNewSell(double retailNewSell) {
        this.retailNewSell = retailNewSell;
    }

    public double getGameStopPrice() {
        return gameStopPrice;
    }

    public void setGameStopPrice(double gameStopPrice) {
        this.gameStopPrice = gameStopPrice;
    }

    public double getGameStopTradePrice() {
        return gameStopTradePrice;
    }

    public void setGameStopTradePrice(double gameStopTradePrice) {
        this.gameStopTradePrice = gameStopTradePrice;
    }


	@Override
	public String toString() {
		return "GamePricing [id=" + id + ", consoleName=" + consoleName + ", productName=" + productName
				+ ", loosePrice=" + loosePrice + ", cibPrice=" + cibPrice + ", newPrice=" + newPrice
				+ ", retailLooseBuy=" + retailLooseBuy + ", retailLooseSell=" + retailLooseSell + ", retailCibBuy="
				+ retailCibBuy + ", retailCibSell=" + retailCibSell + ", retailNewBuy=" + retailNewBuy
				+ ", retailNewSell=" + retailNewSell + ", gameStopPrice=" + gameStopPrice + ", gameStopTradePrice="
				+ gameStopTradePrice + "]";
	}
    
    public static void main(String[] args){
    	
    }

}