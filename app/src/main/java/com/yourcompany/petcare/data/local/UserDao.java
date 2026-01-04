package com.yourcompany.petcare.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.yourcompany.petcare.data.model.UserProfile;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UserProfile u);

    @Update
    void updateUser(UserProfile u);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    UserProfile getUserByEmail(String email);
}
