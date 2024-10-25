package com.example.admincse441project.ui.discountmanagement.showdiscount;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.admincse441project.data.model.discount.Discount;
import com.example.admincse441project.utils.FirebaseUtils;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DiscountViewModel  extends ViewModel {
    private final MutableLiveData<List<Discount>> discounts = new MutableLiveData<>();

    public DiscountViewModel() {
        loadDiscounts();
    }

    public LiveData<List<Discount>> getDiscounts() {
        return discounts;
    }

    private void loadDiscounts() {
        FirebaseUtils.getDiscountsCollection().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Discount> discountList = new ArrayList<>();
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    for (DocumentSnapshot document : querySnapshot) {
                        Discount discount = document.toObject(Discount.class);
                        if (discount != null) {
                            discountList.add(discount);
                        }
                    }
                    discounts.setValue(discountList);
                }
            }
        });
    }
    private MutableLiveData<Discount> discountLiveData = new MutableLiveData<>();
    public LiveData<Discount> getDiscount(String discountId) {

        FirebaseUtils.getDiscountsCollection().document(discountId)
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Discount discount = document.toObject(Discount.class);
                            discountLiveData.setValue(discount);
                        }
                    }
                });
        return discountLiveData;
    }
}
