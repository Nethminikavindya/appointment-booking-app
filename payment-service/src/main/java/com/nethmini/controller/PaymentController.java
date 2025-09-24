package com.nethmini.controller;

import com.nethmini.domain.PaymentMethod;
import com.nethmini.modal.PaymentOrder;
import com.nethmini.payload.dto.BookingDTO;
import com.nethmini.payload.dto.UserDTO;
import com.nethmini.payload.response.PaymentLinkResponse;
import com.nethmini.service.PaymentService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")

public class PaymentController{

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(
          @RequestBody BookingDTO booking,
          @RequestParam PaymentMethod paymentMethod
    ) throws StripeException {
        UserDTO user=new UserDTO();
        user.setFullName("John Doe");
        user.setEmail("nethkavi@gmail.com");
        user.setId(1L);
        PaymentLinkResponse res=paymentService.createOrder(user, booking, paymentMethod);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{paymentOrderId}")
    public ResponseEntity<PaymentOrder> getPaymentOrderById(
            @PathVariable Long paymentOrderId
    ) throws Exception {
       PaymentOrder res=paymentService.getPaymentOrderById(paymentOrderId);
        return ResponseEntity.ok(res);
    }

    @PatchMapping("/proceed")
    public ResponseEntity<Boolean> proceedPayment (
            @RequestParam String paymentId,
            @RequestParam String paymentLinkId
    ) throws Exception {
        PaymentOrder paymentOrder=paymentService.getPaymentOrderByPaymentId(paymentId);
        Boolean res=paymentService.proceedPayment(paymentOrder,paymentId,paymentLinkId);
        return ResponseEntity.ok(res);
    }
}
