package com.android021box.htstartup.tool;

import android.content.Intent;

public interface OnTabActivityResultListener {
    public void onTabActivityResult(int requestCode, int resultCode, Intent data);
}