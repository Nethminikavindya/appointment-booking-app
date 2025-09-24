package com.nethmini.service.impl;

import com.nethmini.domain.PaymentMethod;
import com.nethmini.domain.PaymentOrderStatus;
import com.nethmini.modal.PaymentOrder;
import com.nethmini.payload.dto.BookingDTO;
import com.nethmini.payload.dto.UserDTO;
import com.nethmini.payload.response.PaymentLinkResponse;
import com.nethmini.repository.PaymentOrderRepository;
import com.nethmini.service.PaymentService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.model.PaymentLink;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentOrderRepository
            paymentOrderRepository;

    @Value("${stripe.api.key}")
    private String stripeSecretKey;

    //    @Value("${paypal.api.key}")
    //    private String paypalApiKey;
    //
    //    @Value("${paypal.api.secret}")
    //    private String paypalApiSecret;


    @Override
    public PaymentLinkResponse createOrder(UserDTO user, BookingDTO booking, PaymentMethod paymentMethod) throws StripeException {
        Long amount=(long) booking.getTotalPrice();

        PaymentOrder order = new PaymentOrder();

        order.setAmount(amount);
        order.setPaymentMethod(paymentMethod);
        order.setBookingId(booking.getId());
        order.setSalonId(booking.getSalonId());
        PaymentOrder savedOrder = paymentOrderRepository.save(order);

        PaymentLinkResponse paymentLinkResponse=new PaymentLinkResponse() ;


        if(paymentMethod.equals(PaymentMethod.STRIPE))
//        {
//            PaymentLink payment = createPaypalPaymentLink(user,
//                    savedOrder.getAmount(),
//                    savedOrder.getId());
//            String paymentUrl=payment.getUrl();
//            String paymentUrlId=payment.getId();
//
//            paymentLinkResponse.setPayment_link_url(paymentUrl);
//            paymentLinkResponse.setPayment_link_id(paymentUrlId);
//
//            savedOrder.setPaymentLinkId(paymentUrlId);
//
//            paymentOrderRepository.save(savedOrder);
//        }else
        {
            String paymentUrl=createStripePaymentLink(user,
                    savedOrder.getAmount(),
                    savedOrder.getId() );
            paymentLinkResponse.setPayment_link_url(paymentUrl);
            }
                return paymentLinkResponse;
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        PaymentOrder paymentOrder = paymentOrderRepository.findById(id).orElse(null);
        if(paymentOrder==null) {
            throw new Exception("payment order not found");
        }
        return paymentOrder;
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentId) {
        return paymentOrderRepository.findByPaymentLinkId(paymentId);
    }

//    @Override
//    public PaymentLink createPaypalPaymentLink(UserDTO user,
//                                               Long Amount,
//                                               Long orderId) {
//
//        Long amount=Amount*100;
//
//        PaypalClient paypal= new PaypalClient(paypalApiKey,paypalApiSecret);
//
//        JSONObject paymentLinkRequest=new JSONObject();
//        paymentLinkRequest.put("amount",amount);
//        paymentLinkRequest.put("currency","LKR");
//
//        JSONObject customer=new JSONObject();
//        customer.put("name",user.getFullName());
//        customer.put("email",user.getEmail());
//
//        paymentLinkRequest.put("customer",customer);
//
//        JSONObject notify=new JSONObject();
//        notify.put("email",true);
//
//        paymentLinkRequest.put("notify",notify);
//
//        paymentLinkRequest.put("reminder_enable",true);
//
//        paymentLinkRequest.put("callback_url","http://localhost:3000/payment-success/"+orderId);
//
//        paymentLinkRequest.put("callback_method","get");
//
//        return paypal.paymentLink.create(paymentLinkRequest);
//
//    }

    @Override
    public String createStripePaymentLink(UserDTO user,
                                          Long amount,
                                          Long orderId) throws StripeException {
        Stripe.apiKey=stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment-success/"+orderId)
                .setCancelUrl("http://localhost:3000/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()                                . setCurrency("usd")
                                .setUnitAmount(amount*100)
                                .setProductData(SessionCreateParams
                                        .LineItem
                                        .PriceData
                                        .ProductData
                                        .builder().setName("salon appointment booking").build()
                                ).build()
                                ).build()
                ).build();
      Session session = Session.create(params);
        return session.getUrl();
    }

    @Override
    public Boolean proceedPayment(PaymentOrder paymentOrder,
                                  String paymentId,
                                  String paymentLinkId) {
        if (paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)){
            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS) ;
            paymentOrderRepository.save(paymentOrder) ;
            return true;
        }
return false;
    }
}
