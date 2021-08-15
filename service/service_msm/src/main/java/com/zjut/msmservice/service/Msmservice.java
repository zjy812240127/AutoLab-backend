package com.zjut.msmservice.service;

import java.util.Map;

public interface Msmservice {
    boolean send(String phone, String sms_180051135, Map<String, Object> param);
}
