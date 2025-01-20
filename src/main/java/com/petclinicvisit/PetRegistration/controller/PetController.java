package com.petclinicvisit.PetRegistration.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import com.petclinicvisit.PetRegistration.model.Owner;
import com.petclinicvisit.PetRegistration.model.Pet;
import com.petclinicvisit.PetRegistration.service.PetService;
import reactor.core.publisher.Mono;

@RestController
@Configuration
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/pet")
public class PetController {
    
    private WebClient webClient;

    @Autowired
    private PetService petService;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @PostMapping
    public ResponseEntity<?> createPet(@RequestBody Pet pet) {
        System.out.println("***pet object::" + pet.toString());

        // Ensure the base URL for the owner service is correct
        webClient = webClientBuilder.baseUrl("http://localhost:8085").build();

        Mono<Owner> ownerMono = webClient.get()
                .uri("/owner/{id}", pet.getOwnerId())
                .retrieve()
                .bodyToMono(Owner.class);

        try {
            System.out.println("Attempting to retrieve owner with ID: " + pet.getOwnerId());
            Owner owner = ownerMono.block();
            System.out.println("Owner retrieved: " + owner);

            Pet createdPet = petService.savePet(pet);
            System.out.println("Pet created: " + createdPet);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPet);

        } catch (WebClientResponseException.NotFound e) {
            System.out.println("Owner with ID " + pet.getOwnerId() + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Owner with ID " + pet.getOwnerId() + " not found");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Integer id) {
        System.out.println("**Inside Pet Controller getPetByID api call**");
        Optional<Pet> pet = petService.getPetById(id);
        return pet.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
