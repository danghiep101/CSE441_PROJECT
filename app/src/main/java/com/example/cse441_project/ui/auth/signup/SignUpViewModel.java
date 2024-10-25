package com.example.cse441_project.ui.auth.signup;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.cse441_project.data.model.user.User;
import com.example.cse441_project.utils.FirebaseUtils;

public class SignUpViewModel extends ViewModel {
    private MutableLiveData<Boolean> _signUpStatus = new MutableLiveData<>();
    LiveData<Boolean> signUpStatus = _signUpStatus;

    private MutableLiveData<Boolean> _saveUserDetailStatus = new MutableLiveData<>();
    LiveData<Boolean> saveUserDetailStatus = _saveUserDetailStatus;

    void signUp(String userEmail, String userPassword) {
        FirebaseUtils.firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        _signUpStatus.postValue(true);
                    } else {
                        Log.e("SignUpViewModel", "Sign up failed: " + task.getException().getMessage());
                        _signUpStatus.postValue(false);
                    }
                });
    }

    void saveUserDetail(String userName, String userEmail, String dateOfBirth, String phoneNumber) {
        String userId = FirebaseUtils.currentUserId();

        if (userId != null) {
            User user = new User(userId, userEmail, userName, phoneNumber, "null", dateOfBirth);
            FirebaseUtils.currentUserDetail().set(user).addOnCompleteListener(task -> {
                _saveUserDetailStatus.postValue(task.isSuccessful());
            });
        } else {
            _saveUserDetailStatus.postValue(false);
        }
    }

    Boolean notEmpty(String userEmail, String userName, String userPassword, String dateOfBirth, String phoneNumber) {
        return !userEmail.isEmpty() && !userName.isEmpty() && !userPassword.isEmpty() && !dateOfBirth.isEmpty() && !phoneNumber.isEmpty();
    }
}
