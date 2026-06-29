package com.diya.oms.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PayPalPayment implements PaymentStrategy {
    private static final Logger log = LoggerFactory.getLogger(PayPalPayment.class);

    @Override
    public void pay(BigDecimal amount) {
        log.info("Processing paypal payment of {}",amount);
    }
}
