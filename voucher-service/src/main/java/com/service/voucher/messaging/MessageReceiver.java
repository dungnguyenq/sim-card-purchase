package com.service.voucher.messaging;

import com.service.voucher.service.VoucherService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.concurrent.CountDownLatch;

@Component
public class MessageReceiver {

    final static Logger logger = LogManager.getLogger(MessageReceiver.class);

    @Autowired
    VoucherService voucherService;

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        logger.info("Received <" + message + ">");
        voucherService.save(message);
        latch.countDown();

    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
