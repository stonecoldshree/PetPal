package com.yourcompany.petcare.data.repository;

import android.content.Context;

import com.yourcompany.petcare.data.local.AppDatabase;
import com.yourcompany.petcare.data.local.PetDao;
import com.yourcompany.petcare.data.model.Pet;

import java.util.List;

public class PetRepository {
    private final PetDao dao;

    public PetRepository(Context ctx) {
        dao = AppDatabase.getInstance(ctx).petDao();
    }

    public long insertPet(Pet p) {
        return dao.insertPet(p);
    }

    public void updatePet(Pet p) {
        dao.updatePet(p);
    }

    public void deletePet(Pet p) {
        dao.deletePet(p);
    }

    public List<Pet> getPetsForOwner(String email) {
        return dao.getPetsForOwner(email);
    }

    public Pet getPetByIdSync(long id) {
        return dao.getPetByIdSync(id);
    }

    public List<Pet> getAllPets() { return dao.getAllPets(); }
}
