package com.javatesttask.george.payment;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;



/**
 * Servlet implementation class PaymentJsonServlet
 * @Georgi Karamihaylov
 */
@WebServlet("/PaymentJsonServlet")
public class PaymentJsonServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String URl_API_PAYMENT = "http://sandbox-pay.jeton.com/merchant/initiate-payment";
	private static final String URl_API_AOUTH = "http://sandbox-pay.jeton.com/merchant/authorize";
	private static final String API_KEY = "fec5b073762b4726af7d6a151f30b7ab";
	private static  String TOKEN = "";
	private static  String PAYMENT_ID = "";
	private static String redirectUrl = "";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
	

    public PaymentJsonServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		Long amount = Long.parseLong( request.getParameter("amount"));
		String currency =  request.getParameter("currency");
		String orderId = request.getParameter("orderId");
		PaymentMessage paymentMessage = new PaymentMessage(currency,orderId,amount);
		paymentMessage.setPaymentAmount(amount);
		paymentMessage.setOrderId(orderId);
		paymentMessage.setCurrencyCode(currency);
		try {

			WebResource webResource = Client.create(new DefaultClientConfig()).resource(URl_API_PAYMENT);
			String jsonInput = "{\"paymentAmount\":\" " +paymentMessage.getPaymentAmount()+"\",\"currencyCode\":\""+paymentMessage.getCurrencyCode()+"\",\"orderId\":\""+paymentMessage.getOrderId()+"\",\"successRedirectUrl\":\"http://www.yourdomain.com?a=success\",\"cancelRedirectUrl\":\"http://www.yourdomain.com?a=cancel\",\"failRedirectUrl\":\"http://www.yourdomain.com?b=fail\"}";
			System.out.println(paymentMessage.toString());

           		
			ClientResponse clientResponse = webResource
					.header("X-PAY-API-KEY",API_KEY)
					.header("Content-Type", "application/json")
					.post(ClientResponse.class,jsonInput);							
														
			clientResponse.toString();
		
			//implement filter logging for better trace (deprecated)	
			//	client.addFilter(new com.sun.jersey.api.client.filter.LoggingFilter(System.out));
			if (clientResponse.getStatus() != 200)
                throw new RuntimeException("Failed : HTTP error code : " + clientResponse.getStatus());
			if (clientResponse.getStatus() == 500)
                throw new RuntimeException("Internal Server error : " + clientResponse.getStatus());
			String output = clientResponse.getEntity(String.class);
			String status = clientResponse.toString();
			System.out.println(status);
			System.out.println("Output from Server .... \n");
			System.out.println(output);
			response.getWriter().write(output.toString());
			JSONArray jsonarr = new JSONArray("["+output+"]");

		    for(int i = 0; i < jsonarr.length(); i++){

		    	JSONObject jsonobj = jsonarr.getJSONObject(i);

		    TOKEN=jsonobj.getString("token");
		    redirectUrl=jsonobj.getString("redirectUrl");
		    PAYMENT_ID=	jsonobj.getString("paymentId");
		    }
		    response.sendRedirect(redirectUrl);
		    System.out.println(redirectUrl);
			
		  } catch (Exception e) {

			e.printStackTrace();

		  }

		// Future Implementation for autorization
		//Pseudo Code
		//get response from JSONAPi from Jeton Login
		//Implement Class using success URL in the initiatePayment request from Jeton...
		PaymentRedirectTransaction pymntRedTransact = new PaymentRedirectTransaction(TOKEN,PAYMENT_ID,redirectUrl);
			
		doGet(request, response);
	}

}
