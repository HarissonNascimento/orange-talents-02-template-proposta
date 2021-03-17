package br.com.zup.propostazup.model.request;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class TravelPostRequestBody {

    @NotBlank
    private final String destiny;
    @Future
    @NotNull
    private final LocalDate returnIn;

    public TravelPostRequestBody(@NotBlank String destiny, @Future @NotNull LocalDate returnIn) {
        this.destiny = destiny;
        this.returnIn = returnIn;
    }

    public String getDestiny() {
        return destiny;
    }

    public LocalDate getReturnIn() {
        return returnIn;
    }
}
