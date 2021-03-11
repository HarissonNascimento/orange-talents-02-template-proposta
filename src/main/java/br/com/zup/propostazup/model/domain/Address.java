package br.com.zup.propostazup.model.domain;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class Address {

    @NotBlank
    private String city;
    @NotBlank
    private String district;
    @NotBlank
    private String street;
    @NotBlank
    private String number;
    private String complement;

    public Address(@NotBlank String city,
                   @NotBlank String district,
                   @NotBlank String street,
                   @NotBlank String number,
                   String complement) {

        this.city = city;
        this.district = district;
        this.street = street;
        this.number = number;
        this.complement = complement;
    }

    @Deprecated
    protected Address() {

    }
}
