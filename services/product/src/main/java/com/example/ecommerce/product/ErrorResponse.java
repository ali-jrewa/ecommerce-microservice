package com.example.ecommerce.product;

import java.util.Map;

public record ErrorResponse(
        Map<String, String > errors

) {
}
