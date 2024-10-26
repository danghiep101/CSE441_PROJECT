package com.example.admincse441project.ui.discountmanagement.edit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.admincse441project.data.model.discount.Discount;
import com.example.admincse441project.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;

public class EditDiscountViewModel extends ViewModel {
    private MutableLiveData<Discount> _discount = new MutableLiveData<>();
    public LiveData<Discount> getDiscount(String discountId) {
        FirebaseUtils.getDiscountsCollection().document(discountId)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Discount discount = document.toObject(Discount.class);
                            _discount.setValue(discount);
                        }
                    }
                });
        return _discount;
    }

    public void updateDiscount(Discount discount, OnCompleteListener<Void> onCompleteListener) {
        FirebaseUtils.updateDiscount(discount).addOnCompleteListener(onCompleteListener);
    }
}
