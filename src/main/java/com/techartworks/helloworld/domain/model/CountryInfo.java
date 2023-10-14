package com.techartworks.helloworld.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountryInfo {

    @JsonProperty("Country")
    String country;
    @JsonProperty("CountyCode")
    String countyCode;
    @JsonProperty("Province")
    String province;
    @JsonProperty("City")
    String city;
    @JsonProperty("CityCode")
    String cityCode;
    @JsonProperty("Lat")
    String lat;
    @JsonProperty("Lon")
    String lon;
    @JsonProperty("Confirmed")
    String confirmed;
    @JsonProperty("Deaths")
    String deaths;
    @JsonProperty("Recovered")
    String recovered;
    @JsonProperty("Active")
    String active;
    @JsonProperty("Date")
    Date date;
}
