package br.com.zup.propostazup.endpoint.controller;

import br.com.zup.propostazup.model.domain.Biometry;
import br.com.zup.propostazup.model.domain.ProposalAccount;
import br.com.zup.propostazup.model.request.NewBiometryPostRequestBody;
import br.com.zup.propostazup.model.response.BiometryGetResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;

@RestController
@RequestMapping("/api/biometry")
public class BiometryController {

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/{cardId}")
    @Transactional
    public ResponseEntity<?> createNewBiometry(@PathVariable("cardId") Long cardId,
                                               @RequestBody @Valid NewBiometryPostRequestBody newBiometryPostRequestBody) {

        Optional<ProposalAccount> accountOptional = Optional.ofNullable(entityManager.find(ProposalAccount.class, cardId));

        if (accountOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Biometry biometry = newBiometryPostRequestBody.toBiometry(accountOptional.get());

        entityManager.persist(biometry);

        URI location = fromCurrentContextPath().pathSegment("api/biometry/get/{id}").buildAndExpand(biometry.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getBiometry(@PathVariable("id") Long id) {

        Optional<Biometry> optionalBiometry = Optional.ofNullable(entityManager.find(Biometry.class, id));

        if (optionalBiometry.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        BiometryGetResponseBody biometryGetResponseBody = new BiometryGetResponseBody().toBiometryGetResponseBody(optionalBiometry.get());

        return ResponseEntity.ok(biometryGetResponseBody);
    }
}
