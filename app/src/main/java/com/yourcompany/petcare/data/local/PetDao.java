package com.yourcompany.petcare.data.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.yourcompany.petcare.data.model.Pet;

import java.util.List;

@Dao
public interface PetDao {

    @Insert
    long insertPet(Pet pet);

    @Update
    void updatePet(Pet pet);

    @Delete
    void deletePet(Pet pet);

    @Query("SELECT * FROM pets WHERE ownerEmail = :ownerEmail ORDER BY name ASC")
    List<Pet> getPetsForOwner(String ownerEmail);

    @Query("SELECT COUNT(*) FROM pets WHERE ownerEmail = :ownerEmail")
    int countPetsForOwner(String ownerEmail);

    @Query("SELECT * FROM pets ORDER BY name ASC")
    List<Pet> getAllPets();   // <-- OLD METHOD RESTORED

    @Query("SELECT * FROM pets WHERE id = :id LIMIT 1")
    Pet getPetByIdSync(long id);   // <-- OLD METHOD RESTORED

    @Query("SELECT * FROM pets WHERE id = :id LIMIT 1")
    Pet getById(long id);    // (safe to keep)
}
