package br.com.zup.propostazup.util.creator;

import br.com.zup.propostazup.model.request.AddressPostRequest;

public class AddressPostRequestCreator {

    public static AddressPostRequest createValidAddressPostRequest(){
        return new AddressPostRequest("Testcity",
                "Test District",
                "Test Street",
                "00",
                "Test");
    }
}
