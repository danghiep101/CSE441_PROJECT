package com.example.admincse441project.ui.auth.signup;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.admincse441project.data.model.Admin;
import com.example.admincse441project.utils.FirebaseUtils;

public class SignUpViewModel extends ViewModel {
    private MutableLiveData<Boolean> _signUpStatus = new MutableLiveData<>();
    LiveData<Boolean> signUpStatus = _signUpStatus;

    private MutableLiveData<Boolean> _saveAdminDetailStatus = new MutableLiveData<>();
    LiveData<Boolean> saveAdminDetailStatus = _saveAdminDetailStatus;

    void signUp(String adminEmail, String adminPassword) {
        FirebaseUtils.firebaseAuth.createUserWithEmailAndPassword(adminEmail, adminPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        _signUpStatus.postValue(true);
                    } else {
                        Log.e("SignUpViewModel", "Sign up failed: " + task.getException().getMessage());
                        _signUpStatus.postValue(false);
                    }
                });
    }

    void saveUserDetail(String adminName, String adminEmail, String dateOfBirth, String phoneNumber) {
        String userId = FirebaseUtils.currentUserId();

        if (userId != null) {
            Admin admin = new Admin(userId, adminEmail, adminName, phoneNumber, "null", dateOfBirth);
            FirebaseUtils.currentUserDetail().set(admin).addOnCompleteListener(task -> {
                _saveAdminDetailStatus.postValue(task.isSuccessful());
            });
        } else {
            _saveAdminDetailStatus.postValue(false);
        }
    }

    Boolean notEmpty(String adminEmail, String adminName, String userPassword, String dateOfBirth, String phoneNumber) {
        return !adminEmail.isEmpty() && !adminName.isEmpty() && !userPassword.isEmpty() && !dateOfBirth.isEmpty() && !phoneNumber.isEmpty();
    }
}