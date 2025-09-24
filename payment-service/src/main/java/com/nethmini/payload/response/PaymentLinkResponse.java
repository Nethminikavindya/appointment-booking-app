package com.nethmini.payload.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;




@Data
public class PaymentLinkResponse {
    @JsonProperty("short_url")
    private String payment_link_url;

    @JsonProperty("id")
    private String payment_link_id;
}
