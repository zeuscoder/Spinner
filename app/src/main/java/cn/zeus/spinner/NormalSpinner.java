package cn.zeus.spinner;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;

import java.util.Calendar;

/**
 * Created by zeus on 16-5-28.
 */
public class NormalSpinner extends AutoCompleteTextView implements AdapterView.OnItemClickListener {

    private static final int MAX_CLICK_DURATION = 200;
    private long startClickTime;
    private boolean isPopup;
    private int selectedItemPosition;

    public NormalSpinner(Context context) {
        super(context);
        initSpinner(context);
    }

    public NormalSpinner(Context context, AttributeSet arg1) {
        super(context, arg1);
        initSpinner(context);
    }

    public NormalSpinner(Context context, AttributeSet arg1, int arg2) {
        super(context, arg1, arg2);
        initSpinner(context);
    }

    private void initSpinner(Context context) {
        setTextSize(DeviceUtil.getFontSize(context) + 2);
        setPadding(20, 20, 20, 20);
        setBackgroundDrawable(DeviceUtil.createRoundCornerShapeDrawable(15, 1, 0xffffffff, true));
        setPopupIconDrawable(false);
        setOnItemClickListener(this);
    }

    public int getSelectedItemPosition() {
        return selectedItemPosition;
    }

    public void setSelectedItemPosition(int selectedItemPosition) {
        this.selectedItemPosition = selectedItemPosition;
    }

    public Object getSelectedItem() {
        return getAdapter().getItem(selectedItemPosition);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction,
                                  Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused) {
            if (getAdapter() != null) {
                performFiltering("", 0);
            }
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindowToken(), 0);
            setKeyListener(null);
            dismissDropDown();
        } else {
            isPopup = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                startClickTime = Calendar.getInstance().getTimeInMillis();
                break;
            }
            case MotionEvent.ACTION_UP: {
                long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                if (clickDuration < MAX_CLICK_DURATION) {
                    if (isPopup) {
                        dismissDropDown();
                        isPopup = false;
                    } else {
                        requestFocus();
                        showDropDown();
                        isPopup = true;
                    }
                    setPopupIconDrawable(isPopup);
                }
            }
        }

        return super.onTouchEvent(event);
    }

    //设置翻转图标动画
    private void setPopupIconDrawable(boolean isPopup) {
        Drawable dropdownIcon;
        if (isPopup) {
            dropdownIcon = new BitmapDrawable(DeviceUtil.getImageFromAssetsFile(getContext(), "menu_up.png"));
        } else {
            dropdownIcon = new BitmapDrawable(DeviceUtil.getImageFromAssetsFile(getContext(), "menu_down.png"));
        }
        setCompoundDrawables(null, null, dropdownIcon, null);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        isPopup = false;
        setPopupIconDrawable(isPopup);
        setSelectedItemPosition(position);
    }

    @Override
    public void setCompoundDrawables(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        if (right != null) {
            right.mutate().setAlpha(128);
            right.mutate().setBounds(0,0, DeviceUtil.dip2px(getContext(), 30),DeviceUtil.dip2px(getContext(), 30));
        }
        super.setCompoundDrawables(left, top, right, bottom);
    }
}
