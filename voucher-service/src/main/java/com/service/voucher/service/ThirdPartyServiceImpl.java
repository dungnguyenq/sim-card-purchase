package com.service.voucher.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ThirdPartyServiceImpl implements ThirdPartyService {

    final static Logger logger = LogManager.getLogger(ThirdPartyServiceImpl.class);

    @Value("${thirdparty.endpoint}")
    private String thirdPartyAPI;

    @Override
    public String generateVoucherCode() {
        String voucher = "";
        try {
            URL url = new URL(thirdPartyAPI);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP Error code : "
                        + conn.getResponseCode());
            }
            InputStreamReader in = new InputStreamReader(conn.getInputStream());
            BufferedReader br = new BufferedReader(in);

            String output;
            while ((output = br.readLine()) != null) {
                voucher+=output;
            }
            conn.disconnect();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return voucher;
    }
}
