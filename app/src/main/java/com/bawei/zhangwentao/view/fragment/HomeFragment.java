package com.bawei.zhangwentao.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bawei.zhangwentao.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 作者：张文涛
 * 功能：生成与扫描二维码
 * 时间：2019-11-5 11:08:03
 */

public class HomeFragment extends Fragment {
    @BindView(R.id.img_sao)
    ImageView imgSao;
    @BindView(R.id.et_nei)
    EditText etNei;
    @BindView(R.id.btn_click)
    Button btnClick;
    @BindView(R.id.img_er)
    ImageView imgEr;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.fragment_home, null);
        bind = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @OnClick({R.id.img_sao, R.id.btn_click})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            // 扫描二维码
            case R.id.img_sao:
                Toast.makeText(getActivity(), "扫描", Toast.LENGTH_SHORT).show();
                customScan();
                break;
                // 生成二维码
            case R.id.btn_click:
                Toast.makeText(getActivity(), "生成", Toast.LENGTH_SHORT).show();
                String neirong = etNei.getText().toString();

                imgEr.setImageBitmap((Bitmap) encodeAs(neirong));
                break;
        }
    }

    // 生成二维码
    private Object encodeAs(String neirong) {
        Bitmap bitmap = null;
        BitMatrix result = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            result = multiFormatWriter.encode(neirong, BarcodeFormat.QR_CODE, 200, 200);
            // 使用 ZXing Android Embedded 要写的代码
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(result);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException iae) {
            return null;
        }
        return bitmap;
    }

    // 扫描二维码
    private void customScan() {
        IntentIntegrator integrator = new IntentIntegrator(getActivity());
        integrator.setDesiredBarcodeFormats(IntentIntegrator.CODE_39);
        integrator.setPrompt("请扫描二维码");//底部的提示文字，设为""可以置空
        integrator.setCameraId(0);//前置或者后置摄像头
        integrator.setBeepEnabled(true);//扫描成功的「哔哔」声，默认开启
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bind != null)
            bind.unbind();
    }
}
