package com.example.cse441_project.ui.bookticket;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.cse441_project.data.model.discount.Discount;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.cse441_project.utils.FirebaseUtils;
import java.util.ArrayList;
import java.util.List;

public class DiscountViewModel  extends ViewModel {
    private final MutableLiveData<List<Discount>> _discounts = new MutableLiveData<>();
    public LiveData<List<Discount>> discounts = _discounts;
    public DiscountViewModel() {
        loadDiscounts();
    }
    void loadDiscounts() {
        FirebaseUtils.getDiscountsCollection().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Discount> discountList = new ArrayList<>();
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        Discount discount = document.toObject(Discount.class);
                        if (discount != null) {
                            discountList.add(discount);
                        }
                    }
                    _discounts.setValue(discountList);

                }
            }
        });
    }
}
