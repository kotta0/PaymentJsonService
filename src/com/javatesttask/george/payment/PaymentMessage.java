package com.javatesttask.george.payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentMessage {
	private Long paymentAmount;
	private String orderId;
	private String currencyCode;
	
	public  PaymentMessage (String currencyCode,String orderId,Long paymentAmount){
		this.currencyCode=currencyCode;
		this.orderId=orderId;
		this.paymentAmount=paymentAmount;
	}
	
	
	public Long getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Long paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	//Check initialization
	public String toString(){
	      return "PaymentMessage [ CCY: "+currencyCode+", orderId: "+ orderId + ", TotalAmount: "+ paymentAmount + " ]";
	   }

	
}
