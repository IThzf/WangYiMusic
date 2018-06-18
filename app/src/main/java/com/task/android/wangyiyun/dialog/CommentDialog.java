package com.task.android.wangyiyun.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.task.android.wangyiyun.R;

/**
 * Created by Administrator on 2017/9/22.
 */

public class CommentDialog extends Dialog {
    public CommentDialog(@NonNull Context context) {
        this(context, R.style.dialog);
    }

    public CommentDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected CommentDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 预先设置Dialog的一些属性
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.x = 0;
        p.y = 0;
        p.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(p);
    }


}
