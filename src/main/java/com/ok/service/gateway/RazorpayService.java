package com.ok.service.gateway;

import com.ok.domain.PaymentType;
import com.ok.model.Payment;
import com.ok.model.SubscriptionPlan;
import com.ok.model.User;
import com.ok.payload.response.PaymentLinkResponse;
import com.ok.service.SubscriptionPlanService;
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

	private final SubscriptionPlanService subscriptionPlanService;
	String razorpayKeyId = new String();
	String razorpayKeySecret = new String();
	String callbackBaseUrl = "http://localhost:5173";

	public PaymentLinkResponse createPaymentLink(User user, Payment payment) {

//		@Value("${razorpay.key.id:}")
//		private String razorpayKeyId;
//
//		@Value("${razorpay.key.secret:}")
//		private String razorpayKeySecret;

//		@Value("${razorpay.callback.base-url:http://localhost:5173}")
//		private String callbackBaseUrl;

//		String razorpayKeyId = new String();
//		String razorpayKeySecret = new String();
//		String callbackBaseUrl = "http://localhost:5173";

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

	public JSONObject fetchPaymentDetails(String paymentId) throws Exception {

		try {

			RazorpayClient razorpay =  new RazorpayClient(razorpayKeyId,
							razorpayKeySecret);
			com.razorpay.Payment payment = razorpay.payments.fetch(paymentId);

			return payment.toJson();
		} catch (RazorpayException e) {

			throw new Exception("Failed to fetch payment details" + e.getMessage(),
							e);
		}

	}

	public boolean isValidPayment(String paymentId) throws Exception {

		try {

			JSONObject paymentDetails = fetchPaymentDetails(paymentId);

			String status = paymentDetails.optString("status");
			long amount = paymentDetails.optLong("amount");
			long amountInRupees = amount / 100;

			JSONObject notes = paymentDetails.optJSONObject("notes");

			String paymentType = notes.optString("type");

			if (!"captured".equalsIgnoreCase(status)) {

				return false;
			}

			if (paymentType.equals(PaymentType.MEMBERSHIP.toString())) {

				String planCode = notes.optString("plan");
				SubscriptionPlan subscriptionPlan = subscriptionPlanService
								.getBySubscriptionPlanCode(planCode);

				return amountInRupees == subscriptionPlan.getPrice();

			} else if (paymentType.equals(PaymentType.FINE.toString())) {

				Long fineId = notes.getLong("fine_id");

				// todo
			}

			return false;
		} catch (Exception e) {

			return false;
		}
	}

}
