package com.example.cse441_project.ui.auth.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cse441_project.utils.FirebaseUtils;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<Boolean> _loginStatus = new MutableLiveData<>();
    LiveData<Boolean> loginStatus = _loginStatus;

    void login(String email, String password){
        FirebaseUtils.firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            _loginStatus.postValue(task.isSuccessful());
        });
    }
    boolean  notEmpty(String email, String password){
        return !email.isEmpty() && !password.isEmpty();
    }
}
