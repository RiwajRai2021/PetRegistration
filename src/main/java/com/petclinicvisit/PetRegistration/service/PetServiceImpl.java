package com.petclinicvisit.PetRegistration.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petclinicvisit.PetRegistration.Repository.PetRepository;
import com.petclinicvisit.PetRegistration.model.Pet;

import jakarta.transaction.Transactional;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Override
    @Transactional
    public Pet savePet(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public Optional<Pet> getPetById(Integer id) {
        return petRepository.findById(id);
    }
}
