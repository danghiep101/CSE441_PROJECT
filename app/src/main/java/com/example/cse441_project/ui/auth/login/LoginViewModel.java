package com.example.cse441_project.ui.auth.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cse441_project.utils.FirebaseUtils;

public class LoginViewModel extends ViewModel {
    private String userId;
    private MutableLiveData<Boolean> _loginStatus = new MutableLiveData<>();
    LiveData<Boolean> loginStatus = _loginStatus;

    void login(String email, String password){
        FirebaseUtils.firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                userId = FirebaseUtils.firebaseAuth.getCurrentUser().getUid();
                _loginStatus.postValue(true);
            } else {
                _loginStatus.postValue(false);
            }
        });
    }
    boolean  notEmpty(String email, String password){
        return !email.isEmpty() && !password.isEmpty();
    }
    public String getUserId() {
        return userId; // Trả về ID người dùng
    }
}
