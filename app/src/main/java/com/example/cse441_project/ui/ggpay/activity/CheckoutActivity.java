/*
 * Copyright 2024 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.cse441_project.ui.ggpay.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cse441_project.R;

import com.example.cse441_project.databinding.ActivityCheckoutGgBinding;
import com.example.cse441_project.ui.ggpay.util.PaymentsUtil;
import com.example.cse441_project.ui.ggpay.viewmodel.CheckoutViewModel;
import com.google.android.gms.common.api.CommonStatusCodes;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.button.ButtonOptions;
import com.google.android.gms.wallet.button.PayButton;
import com.google.android.gms.wallet.contract.TaskResultContracts;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Checkout implementation for the app
 */
public class CheckoutActivity extends AppCompatActivity {

  private double priceInVND;
  private String selectedSeatsString;
  private CheckoutViewModel model;

  private PayButton googlePayButton;
  private String userId;
  private int numberOfSeats;

  private final ActivityResultLauncher<Task<PaymentData>> paymentDataLauncher =
      registerForActivityResult(new TaskResultContracts.GetPaymentDataResult(), result -> {
        int statusCode = result.getStatus().getStatusCode();
        switch (statusCode) {
          case CommonStatusCodes.SUCCESS:
            handlePaymentSuccess(result.getResult());
            break;
          //case CommonStatusCodes.CANCELED: The user canceled
          case CommonStatusCodes.DEVELOPER_ERROR:
            handleError(statusCode, result.getStatus().getStatusMessage());
            break;
          default:
            handleError(statusCode, "Unexpected non API" +
                " exception when trying to deliver the task result to an activity!");
            break;
        }
      });

  /**
   * Initialize the Google Pay API on creation of the activity
   *
   * @see Activity#onCreate(Bundle)
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


    model = new ViewModelProvider(this).get(CheckoutViewModel.class);
//todo: 2 layout thanh toán là activity_checkout và activity_checkout_success
    model.canUseGooglePay.observe(this, this::setGooglePayAvailable);
    //todo: Nhận giá trị từ Intent gửi sang, ví dự như ở dưới
    //
    userId = getUserId();
    selectedSeatsString=getIntent().getStringExtra("SELECTED_SEATS_LIST");

    priceInVND = getIntent().getDoubleExtra("TOTAL_PRICE", 10000); // Mặc định là 0 nếu không có giá
    Log.e("CheckoutActivity", "bookTicket: " +priceInVND );
    // Sử dụng giá để hiển thị hoặc thực hiện thanh toán
    requestPayment(priceInVND);
    initializeUi();
  }
  private String getUserId() {
    // Lấy ID người dùng từ SharedPreferences
    SharedPreferences sharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
    return sharedPreferences.getString("USER_ID", null); // Trả về null nếu không có ID
  }

  private void initializeUi() {
    // Use view binding to access the UI elements
    ActivityCheckoutGgBinding layoutBinding = ActivityCheckoutGgBinding.inflate(getLayoutInflater());
    setContentView(layoutBinding.getRoot());
    //todo: dùng cấu trúc nh bên dưới để set giá trị cho các trường
    layoutBinding.txtPrice.setText(String.valueOf(priceInVND));

    // The Google Pay button is a layout file – take the root view
    googlePayButton = layoutBinding.googlePayButton;
    try {
      googlePayButton.initialize(
              ButtonOptions.newBuilder()
                      .setAllowedPaymentMethods(PaymentsUtil.getAllowedPaymentMethods().toString()).build()
      );
      // Thay đổi cách gọi requestPayment để truyền giá tiền
      googlePayButton.setOnClickListener(v -> requestPayment(priceInVND)); // Sử dụng biến giá tiền
    } catch (JSONException e) {
      // Giấu nút Google Pay nếu có lỗi
      googlePayButton.setVisibility(View.GONE);
    }
  }

  /**
   * If isReadyToPay returned {@code true}, show the button and hide the "checking" text.
   * Otherwise, notify the user that Google Pay is not available. Please adjust to fit in with
   * your current user flow. You are not required to explicitly let the user know if isReadyToPay
   * returns {@code false}.
   *
   * @param available isReadyToPay API response.
   */
  private void setGooglePayAvailable(boolean available) {
    if (available) {
      googlePayButton.setVisibility(View.VISIBLE);
    } else {
      Toast.makeText(this, R.string.google_pay_status_unavailable, Toast.LENGTH_LONG).show();
    }
  }

  public void requestPayment(double priceInVND) {
    // Sử dụng giá tiền đã truyền vào
    final Task<PaymentData> task = model.getLoadPaymentDataTask(priceInVND);
    task.addOnCompleteListener(paymentDataLauncher::launch);
  }

  /**
   * PaymentData response object contains the payment information, as well as any additional
   * requested information, such as billing and shipping address.
   *
   * @param paymentData A response object returned by Google after a payer approves payment.
   * @see <a href="https://developers.google.com/pay/api/android/reference/
   * object#PaymentData">PaymentData</a>
   */
  private void handlePaymentSuccess(PaymentData paymentData) {
    final String paymentInfo = paymentData.toJson();

    try {
      JSONObject paymentMethodData = new JSONObject(paymentInfo).getJSONObject("paymentMethodData");
      // If the gateway is set to "example", no payment information is returned - instead, the
      // token will only consist of "examplePaymentMethodToken".

      final JSONObject info = paymentMethodData.getJSONObject("info");
      // Logging token string.
      Log.d("Google Pay token", paymentMethodData
          .getJSONObject("tokenizationData")
          .getString("token"));
      updateTicketsInFirestore(userId, selectedSeatsString);
      startActivity(new Intent(this, CheckoutSuccessActivity.class));
      //todo: Nếu thành công, Truyền activity muốn đến ở đây

    } catch (JSONException e) {
      Log.e("handlePaymentSuccess", "Error: " + e);
    }
  }
  private void updateTicketsInFirestore(String userId, String selectedSeatsString) {
    // Khởi tạo Firestore
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String currentShowtimeId = getIntent().getStringExtra("SHOWTIME_ID");

// Chia tách danh sách ghế từ chuỗi
    String[] selectedSeatsArray = selectedSeatsString.split(",");

// Chuyển đổi mảng thành danh sách để dễ dàng xử lý
    List<String> selectedSeatsList = Arrays.asList(selectedSeatsArray);

// Giả sử bạn có một collection 'tickets' và mỗi ticket có các trường 'seat' và 'showtimeId'
    CollectionReference ticketsRef = db.collection("tickets");

// Cập nhật từng ticket có điều kiện seat = "" và showtimeId = currentShowtimeId
    ticketsRef.whereEqualTo("seat", "")
            .whereEqualTo("showtimeId", currentShowtimeId)
            .get()
            .addOnCompleteListener(task -> {
              if (task.isSuccessful()) {
                int index = 0; // Biến để theo dõi ghế
                for (QueryDocumentSnapshot document : task.getResult()) {
                  if (index < selectedSeatsList.size()) {
                    // Gán ghế từ danh sách vào tài liệu
                    String newSeatValue = selectedSeatsList.get(index);

                    // Cập nhật tài liệu
                    ticketsRef.document(document.getId()).update("seat", newSeatValue, "userId", userId)
                            .addOnSuccessListener(aVoid -> {
                              // Cập nhật thành công
                              Log.d("Firestore", "Seat and UserId updated successfully: " + newSeatValue + ", UserId: " + userId);
                            })
                            .addOnFailureListener(e -> {
                              // Xử lý lỗi
                              Log.w("Firestore", "Error updating seat and UserId", e);
                            });

                    index++; // Tăng chỉ số để chuyển sang ghế tiếp theo
                  } else {
                    break; // Nếu không còn ghế nào, thoát vòng lặp
                  }
                }
              } else {
                Log.w("Firestore", "Error getting documents.", task.getException());
              }
            });


  }

  /**
   * At this stage, the user has already seen a popup informing them an error occurred. Normally,
   * only logging is required.
   *
   * @param statusCode holds the value of any constant from CommonStatusCode or one of the
   *                   WalletConstants.ERROR_CODE_* constants.
   * @see <a href="https://developers.google.com/android/reference/com/google/android/gms/wallet/
   * WalletConstants#constant-summary">Wallet Constants Library</a>
   */
  private void handleError(int statusCode, @Nullable String message) {
    Log.e("loadPaymentData failed",
        String.format(Locale.getDefault(), "Error code: %d, Message: %s", statusCode, message));
  }
}
