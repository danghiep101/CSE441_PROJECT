package com.example.cse441_project.ui.bookticket;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class CustomArcView extends View {

    private Paint paint;
    private Path path;

    public CustomArcView(Context context) {
        super(context);
        init();
    }

    public CustomArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(0xFFBFA975); // Màu vàng như trong ảnh
        paint.setStrokeWidth(10f);  // Độ dày của đường vẽ
        paint.setStyle(Paint.Style.STROKE); // Vẽ đường viền

        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        // Xác định kích thước và vị trí của đường cong
        int width = getWidth();
        int height = getHeight();
        int arcHeight = height / 2; // Chiều cao của đường cong

        // Tạo đường cong
        path.reset();
        path.moveTo(0, arcHeight); // Bắt đầu từ phía bên trái
        path.quadTo(width / 2, 0, width, arcHeight); // Tạo đường cong giữa

        // Vẽ đường cong lên canvas
        canvas.drawPath(path, paint);
    }
}
