package com.bawei.zhangwentao.view.activity;

import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bawei.zhangwentao.R;
import com.bawei.zhangwentao.utils.OkHttpUtil;
import com.bawei.zhangwentao.view.fragment.HomeFragment;
import com.bawei.zhangwentao.view.fragment.ListFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：张文涛
 * 功能：ViewPager + Fragment
 * 时间：2019-11-5 09:02:02
 */

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.group)
    RadioGroup group;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = ButterKnife.bind(this);

        if (OkHttpUtil.isNetWorkConnection(this)){
            Toast.makeText(this, "有网", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "没网", Toast.LENGTH_SHORT).show();
        }


        // 创建 Fragment 集合
        final ArrayList<Fragment> list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new ListFragment());

        // 创建 ViewPager 适配器
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        // 滑动切换
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                group.check(viewPager.getChildAt(position).getId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        // 点击切换
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbn_home:
                        viewPager.setCurrentItem(0, false);
                        break;
                    case R.id.rbn_list:
                        viewPager.setCurrentItem(1, false);
                        break;
                }
            }
        });
    }

    // 解绑 ButterKnife
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
