package br.com.zup.propostazup.model.request;

import br.com.zup.propostazup.model.domain.Address;

import javax.validation.constraints.NotBlank;

public class AddressPostRequest {

    @NotBlank
    private final String city;
    @NotBlank
    private final String district;
    @NotBlank
    private final String street;
    @NotBlank
    private final String number;
    private final String complement;

    public AddressPostRequest(@NotBlank String city,
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

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }

    public Address toAddress() {
        return new Address(this.city, this.district, this.street, this.number, this.complement);
    }
}
