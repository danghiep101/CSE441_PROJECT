package com.example.admincse441project.ui.auth.forgotpassword;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.admincse441project.utils.FirebaseUtils;

public class ForgotPasswordViewModel extends ViewModel {
    private MutableLiveData<Boolean> _resetStatus = new MutableLiveData<>();
    LiveData<Boolean> resetStatus = _resetStatus;

    void resetPassword(String email){
        FirebaseUtils.firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                _resetStatus.postValue(task.isSuccessful());
            }else {
                _resetStatus.postValue(false);
            }
        });
    }
}
