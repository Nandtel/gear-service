package com.gearservice.controller;

import com.gearservice.model.cheque.Payment;
import com.gearservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class PaymentController {

    @Autowired PaymentService paymentService;

    @RequestMapping(value = "/api/payment/cheque/{chequeID}", method = RequestMethod.GET)
    public List<Payment> getPaymentsOfCheque(@PathVariable Long chequeID) {
        return paymentService.getPaymentsOfCheque(chequeID);
    }

    @RequestMapping(value = "/api/payment/cheque/{chequeID}", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void setPaymentsOfCheque(@PathVariable Long chequeID, @RequestBody Set<Payment> payments) {
        paymentService.setPaymentsOfCheque(chequeID, payments);
    }

    @RequestMapping(value = "/api/payment/{paymentID}/cheque", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deletePaymentOfCheque(@PathVariable Long paymentID) {
        paymentService.deletePayment(paymentID);
    }

}
