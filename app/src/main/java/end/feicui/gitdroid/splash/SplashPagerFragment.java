package end.feicui.gitdroid.splash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import end.feicui.gitdroid.R;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Administrator on 2016/7/26.
 */
public class SplashPagerFragment extends Fragment {

    private ViewPager viewPager;
    private CircleIndicator indicator;
    SplashPagerAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        adapter = new SplashPagerAdapter(getContext());
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        //添加ViewPager监听(为了动画)
        viewPager.setOnPageChangeListener(pageColorListener);
        viewPager.setOnPageChangeListener(phoneViewListener);
    }
    //主要为了做背景颜色渐变处理
    private ViewPager.OnPageChangeListener pageColorListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    // 主要为了做"手机"的动画效果处理(平移、缩放、透明度变化)
    private ViewPager.OnPageChangeListener phoneViewListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
