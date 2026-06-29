package com.diya.oms.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Primary
public class CreditCardPayment implements PaymentStrategy{
    private static final Logger log = LoggerFactory.getLogger(CreditCardPayment.class);

    @Override
    public void pay(BigDecimal amount) {
        log.info("Processing credit card payment of {}",amount);
    }
}
