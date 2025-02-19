package cz.exodus.sts.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateResponse {

    @JsonProperty(required = true)
    private String clientId;

    @JsonProperty(required = true)
    private String type;

    @JsonProperty(required = true)
    private String sub;

    @JsonProperty(required = true)
    private String scopes;

    @JsonProperty(required = true)
    private Date expiration;

    @JsonProperty(required = true)
    long expiresIn;

    @JsonProperty(required = true)
    private String metadata;

}