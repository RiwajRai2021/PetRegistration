package com.petclinicvisit.PetRegistration.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petclinicvisit.PetRegistration.model.Pet;

public interface PetRepository extends JpaRepository<Pet, Integer> {

}
