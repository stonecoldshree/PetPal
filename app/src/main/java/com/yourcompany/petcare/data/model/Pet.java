package com.yourcompany.petcare.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pets")
public class Pet {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;
    private String species;    // dog, cat, bird, other
    private String gender;     // male/female
    private String breed;
    private String ownerEmail;
    private int age;
    private String avatar;     // drawable name or uri

    public Pet(String name, String species, String gender, String breed, String ownerEmail, int age) {
        this.name = name;
        this.species = species;
        this.gender = gender;
        this.breed = breed;
        this.ownerEmail = ownerEmail;
        this.age = age;
        this.avatar = null;
    }

    // getters & setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public String getSpecies() { return species; }
    public String getGender() { return gender; }
    public String getBreed() { return breed; }
    public String getOwnerEmail() { return ownerEmail; }
    public int getAge() { return age; }
    public String getAvatar() { return avatar; }

    public void setName(String name) { this.name = name; }
    public void setSpecies(String species) { this.species = species; }
    public void setGender(String gender) { this.gender = gender; }
    public void setBreed(String breed) { this.breed = breed; }
    public void setOwnerEmail(String ownerEmail) { this.ownerEmail = ownerEmail; }
    public void setAge(int age) { this.age = age; }
    public void setAvatar(String avatar) { this.avatar = avatar; }
}
