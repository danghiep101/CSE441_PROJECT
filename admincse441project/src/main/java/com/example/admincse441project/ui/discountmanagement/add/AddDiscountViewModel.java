package com.example.admincse441project.ui.discountmanagement.add;

import androidx.lifecycle.ViewModel;

import com.example.admincse441project.data.model.discount.Discount;
import com.example.admincse441project.utils.FirebaseUtils;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;

public class AddDiscountViewModel extends ViewModel {
    private final FirebaseUtils firebaseUtils = new FirebaseUtils();
    public Task<DocumentReference> addDiscount(Discount discount) {
        return FirebaseUtils.addDiscount(discount).addOnSuccessListener(documentReference -> {
            String generatedId = documentReference.getId();
            discount.setId(generatedId);
            FirebaseUtils.updateDiscount(discount);
        });
    }
}