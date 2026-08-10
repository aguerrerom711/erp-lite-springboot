package com.andresgm.erp_lite.domain.order;

import java.time.Year;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

public record OrderNumber(String value) {

    private static final Pattern ORDER_NUMBER_PATTERN = Pattern.compile("ORD-\\d{4}-\\d{3}");

    public OrderNumber {
        if (value == null) {
            throw new IllegalArgumentException("OrderNumber value must not be null");
        }
        if (!ORDER_NUMBER_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("OrderNumber value must match pattern ORD-YYYY-NNN (e.g. ORD-2025-001): " + value);
        }
    }

    public static OrderNumber of(String value) {
        return new OrderNumber(value);
    }

    public static OrderNumber generate() {
        int year = Year.now().getValue();
        int sequence = ThreadLocalRandom.current().nextInt(1, 1000);
        return new OrderNumber("ORD-%d-%03d".formatted(year, sequence));
    }
}
