package com.javatesttask.george.payment;

public class PaymentRedirectTransaction {
private String token;
private String redirectURL;
private String paymentID;
public PaymentRedirectTransaction(String token, String redirectURL, String paymentID) {
	super();
	this.token = token;
	this.redirectURL = redirectURL;
	this.paymentID = paymentID;
}
public String getToken() {
	return token;
}
public void setToken(String token) {
	this.token = token;
}
public String getRedirectURL() {
	return redirectURL;
}
public void setRedirectURL(String redirectURL) {
	this.redirectURL = redirectURL;
}
public String getPaymentID() {
	return paymentID;
}
public void setPaymentID(String paymentID) {
	this.paymentID = paymentID;
}


}
