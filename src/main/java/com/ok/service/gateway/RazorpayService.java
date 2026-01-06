package com.ok.service.gateway;

import com.ok.domain.PaymentType;
import com.ok.model.Payment;
import com.ok.model.User;
import com.ok.payload.response.PaymentLinkResponse;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RazorpayService {

	public PaymentLinkResponse createPaymentLink(User user, Payment payment) {

//		@Value("${razorpay.key.id:}")
//		private String razorpayKeyId;
//
//		@Value("${razorpay.key.secret:}")
//		private String razorpayKeySecret;

//		@Value("${razorpay.callback.base-url:http://localhost:5173}")
//		private String callbackBaseUrl;

		String razorpayKeyId = new String();
		String razorpayKeySecret = new String();
		String callbackBaseUrl = "http://localhost:5173";

		try {

			RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId,
							razorpayKeySecret);

			Long amountInPaisa = payment.getAmount() * (new java.math.BigDecimal(
							"100")).intValue();

			JSONObject paymentLinkRequest = new JSONObject();
			paymentLinkRequest.put("amount", amountInPaisa);
			paymentLinkRequest.put("currency", "UZS");
			paymentLinkRequest.put("description", payment.getDescription());

			JSONObject customer =  new JSONObject();
			customer.put("name", user.getFullName());
			customer.put("email", user.getEmail());

			if (user.getPhone()!=null) {
				customer.put("contact", user.getPhone());
			}

			paymentLinkRequest.put("customer", customer);

			JSONObject notify = new JSONObject();
			notify.put("email", true);
			notify.put("sms", user.getPhone() != null);
			paymentLinkRequest.put("notify", notify);

			paymentLinkRequest.put("reminder_enable", true);

			String successUrl = callbackBaseUrl + "/payment-success" + payment.getId();
//			String cancelUrl =
//							callbackBaseUrl + "/payment-cancelled" + payment.getId();

			paymentLinkRequest.put("callback_url", successUrl);
			paymentLinkRequest.put("callback_method", "get");

			JSONObject notes =  new JSONObject();
			notes.put("user_id", user.getId());
			notes.put("payment_id", payment.getId());

			if (payment.getPaymentType() == PaymentType.MEMBERSHIP) {

				notes.put("subscription_id", payment.getSubscription().getId());
				notes.put("plan", payment.getSubscription().getPlan().getPlanCode());
				notes.put("type", PaymentType.MEMBERSHIP);

			} else if (payment.getPaymentType() == PaymentType.FINE) {

				//todo
				//notes.put("fine_id", payment.getfine().getId());
				notes.put("type", PaymentType.FINE);
			}

			paymentLinkRequest.put("notes", notes);

			PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);

			String paymentUrl = paymentLink.get("short_url");
			String paymentLinkId = paymentLink.get("id");

			PaymentLinkResponse response = new PaymentLinkResponse();
			response.setPayment_link_url(paymentUrl);
			response.setPayment_link_id(paymentLinkId);

			return response;

		} catch (RazorpayException e) {

			throw new RuntimeException(e);
		}

	}

}
