package com.petclinicvisit.PetRegistration.service;

import java.util.Optional;

import com.petclinicvisit.PetRegistration.model.Pet;

public interface PetService {
	
	Pet savePet(Pet pet); 
	Optional<Pet>getPetById(Integer id); 
	
	
	

}
