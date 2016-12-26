/*
 * **********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     16-10-8 下午4:47
 * *********************************************************
 */
package com.fosung.gui;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 单选框组合弹出框
 */
public class ZDialogRadioGroup extends ZDialog implements View.OnClickListener {
    private static int LAYOUT_ID;

    protected TextView                             tvTitle;        //标题
    protected ZDialogParamSubmitInterface<Integer> submitInter;    // 点击确定按钮回调接口
    protected RadioGroup                           rgChoise;
    protected TextView                             tvCancel;
    protected TextView                             tvSubmit;

    /**
     * 如果用户需要自己使用布局替代此xml文件，则需要在Application中初始化此函数，
     * 传入自定义的Layout，layout中的所有Id必须与本xml的Id相同
     */
    public static void initLayout(int layoutId) {
        LAYOUT_ID = layoutId;
    }

    public ZDialogRadioGroup(Activity context) {
        super(context, LAYOUT_ID == 0 ? R.layout.gui_dlg_radiogroup : LAYOUT_ID);
        initRes();
    }

    private void initRes() {
        tvTitle = getView(R.id.dialogradiogroup_title);
        rgChoise = getView(R.id.dialogradiogroup_radiogroup);
        tvCancel = getView(R.id.dialog_cancelbutton);
        tvSubmit = getView(R.id.dialog_okbutton);

        tvCancel.setText("取消");
        tvSubmit.setText("确定");
        tvSubmit.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    public ZDialogRadioGroup setTitle(String str) {
        if (!TextUtils.isEmpty(str)) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(str);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        return this;
    }

    public ZDialogRadioGroup setDatas(ArrayList<String> listData, String defStr) {
        setDatas(listData.toArray(new String[listData.size()]), defStr);
        return this;
    }

    public ZDialogRadioGroup setDatas(String[] attrStr, String defStr) {
        int paddingVer = (int) getContext().getResources()
                                           .getDimension(R.dimen.gui_dimens_mid);
        int paddingHor = (int) getContext().getResources()
                                           .getDimension(R.dimen.gui_dimens_big);
        LayoutParams layout = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layout.leftMargin = paddingHor;
        layout.rightMargin = paddingHor;

        rgChoise.removeAllViews();
        for (int i = 0; i < attrStr.length; i++) {
            RadioButton btn = new RadioButton(getContext());
            btn.setText(attrStr[i]);
            btn.setTextAppearance(getContext(), R.style.Gui_TextStyle_GrayMid_Normal);
            btn.setPadding(0, paddingVer, 0, paddingVer);
            btn.setBackgroundResource(R.drawable.gui_dlg_radio_sel);
            btn.setId(i + 100);
            if (attrStr[i].equals(defStr)) {
                btn.setChecked(true);
            }
            rgChoise.addView(btn, layout);
        }
        return this;
    }

    /**
     * 添加确定回调接口
     */
    public ZDialogRadioGroup addSubmitListener(ZDialogParamSubmitInterface<Integer> submitInter) {
        this.submitInter = submitInter;
        return this;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_okbutton) {
            if (submitInter != null && rgChoise.getCheckedRadioButtonId() > 0) {
                submitInter.submit(rgChoise.getCheckedRadioButtonId() - 100);
                cancel();
            }
        } else if (v.getId() == R.id.dialog_cancelbutton) {
            cancel();
        }
    }
}
