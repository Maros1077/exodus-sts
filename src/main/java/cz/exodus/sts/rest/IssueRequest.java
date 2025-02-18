package cz.exodus.sts.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueRequest {

    @JsonProperty(required = true)
    String clientId;

    @JsonProperty(required = true)
    String scope;

    @JsonProperty(required = true)
    String grantType;

    @JsonProperty(required = true)
    String sub;

    @JsonProperty(required = true)
    JsonNode metadata;

}