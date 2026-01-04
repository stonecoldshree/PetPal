package com.yourcompany.petcare.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "users")
public class UserProfile {

    @PrimaryKey
    @NonNull
    private String email; // primary key - must be non-null

    private String name;
    private String phone;
    private int age;
    private String photoUri;

    public UserProfile(@NonNull String email) {
        this.email = email;
    }

    @NonNull public String getEmail() { return email; }
    public void setEmail(@NonNull String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getPhotoUri() { return photoUri; }
    public void setPhotoUri(String photoUri) { this.photoUri = photoUri; }
}
