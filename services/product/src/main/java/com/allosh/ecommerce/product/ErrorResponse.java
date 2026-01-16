package com.allosh.ecommerce.product;

import java.util.Map;

public record ErrorResponse(
        Map<String, String > errors

) {
}
