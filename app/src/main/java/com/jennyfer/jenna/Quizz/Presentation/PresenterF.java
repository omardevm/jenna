package com.jennyfer.jenna.Quizz.Presentation;

import android.widget.Button;

import com.google.android.material.button.MaterialButton;

public class PresenterF {

    private ViewPagerTransactions viewPagerTransactions;

    public PresenterF(ViewPagerTransactions viewPagerTransactions){
        this.viewPagerTransactions = viewPagerTransactions;
    }

    public void trigger(String factor_, Button button){
        viewPagerTransactions.clicButtonPager(factor_,button);
    }
}
