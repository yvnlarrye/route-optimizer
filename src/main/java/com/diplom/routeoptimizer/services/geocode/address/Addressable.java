package com.diplom.routeoptimizer.services.geocode.address;


import com.diplom.routeoptimizer.services.geocode.address.impl.OneStringAddress;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = OneStringAddress.class, name = "oneStringAddress")
})
public interface Addressable {
    String oneStringAddress();
}
