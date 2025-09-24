package com.nethmini.service;

import com.nethmini.domain.PaymentMethod;
import com.nethmini.modal.PaymentOrder;
import com.nethmini.payload.dto.BookingDTO;
import com.nethmini.payload.dto.UserDTO;
import com.nethmini.payload.response.PaymentLinkResponse;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;

public interface PaymentService {
    PaymentLinkResponse createOrder(UserDTO user,
                                    BookingDTO booking,
                                    PaymentMethod paymentMethod
                                    ) throws StripeException;

    PaymentOrder getPaymentOrderById(Long id) throws Exception;

    PaymentOrder getPaymentOrderByPaymentId(String paymentId);

//    PaymentLink createPaypalPaymentLink(UserDTO user,
//                                        Long amount,
//                                        Long orderId);

    String createStripePaymentLink(UserDTO user,
                                        Long amount,
                                        Long orderId) throws StripeException;

    Boolean proceedPayment(PaymentOrder paymentOrder,
                           String paymentId,
                           String paymentLinkId);

}
