package com.yourcompany.petcare.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.yourcompany.petcare.data.local.AppDatabase;
import com.yourcompany.petcare.data.local.PetDao;
import com.yourcompany.petcare.data.model.Pet;

import java.util.List;
import java.util.concurrent.Executors;

public class PetViewModel extends AndroidViewModel {

    private final PetDao petDao;
    private final MutableLiveData<List<Pet>> petsLive = new MutableLiveData<>();

    public PetViewModel(@NonNull Application application) {
        super(application);
        AppDatabase db = AppDatabase.getInstance(application);
        petDao = db.petDao();
        loadPetsForCurrentUser();
    }

    public LiveData<List<Pet>> getPets() {
        return petsLive;
    }

    public void loadPetsForCurrentUser() {
        String email = FirebaseAuth.getInstance().getCurrentUser() != null ?
                FirebaseAuth.getInstance().getCurrentUser().getEmail() : null;

        if (email == null) {
            petsLive.postValue(null);
            return;
        }

        Executors.newSingleThreadExecutor().execute(() -> {
            List<Pet> list = petDao.getPetsForOwner(email);
            petsLive.postValue(list);
        });
    }

    public void insertPet(Pet p) {
        Executors.newSingleThreadExecutor().execute(() -> {
            petDao.insertPet(p);
            // reload list
            loadPetsForCurrentUser();
        });
    }

    public void updatePet(Pet p) {
        Executors.newSingleThreadExecutor().execute(() -> {
            petDao.updatePet(p);
            loadPetsForCurrentUser();
        });
    }

    // NEW: delete pet and reload list
    public void deletePet(Pet p) {
        Executors.newSingleThreadExecutor().execute(() -> {
            petDao.deletePet(p);
            loadPetsForCurrentUser();
        });
    }

    public int countPetsForUser(String email) {
        return petDao.countPetsForOwner(email);
    }
}
