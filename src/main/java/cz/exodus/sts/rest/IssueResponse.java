package cz.exodus.sts.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueResponse {

    @JsonProperty(required = true)
    String token;

    @JsonProperty(required = true)
    String type;

    @JsonProperty(required = true)
    int expiresIn;

}