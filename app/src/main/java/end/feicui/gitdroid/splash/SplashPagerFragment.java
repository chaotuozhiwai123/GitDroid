package end.feicui.gitdroid.splash;

import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import end.feicui.gitdroid.R;
import end.feicui.gitdroid.splash.pager.pager2;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by Administrator on 2016/7/26.
 */
public class SplashPagerFragment extends Fragment {

    private ViewPager viewPager;
    private CircleIndicator indicator; //指示器
    SplashPagerAdapter adapter;
    FrameLayout frameLayout;//当前页面Layout(主要为了更新其背景颜色)

    FrameLayout layoutPhone;//屏幕中央的"手机"
    ImageView ivPhoneFont;

    private int colorGreen;
    private int colorRed;
    private int colorYellow;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_pager, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        colorGreen = getResources().getColor(R.color.colorGreen);
        colorRed = getResources().getColor(R.color.colorRed);
        colorYellow = getResources().getColor(R.color.colorYellow);

        layoutPhone = (FrameLayout) view.findViewById(R.id.layoutPhone);
        ivPhoneFont = (ImageView) view.findViewById(R.id.ivPhoneFont);

        frameLayout = (FrameLayout) view.findViewById(R.id.content);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        adapter = new SplashPagerAdapter(getContext());
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);
        //添加ViewPager监听(为了动画)
        viewPager.addOnPageChangeListener(pageColorListener);
        viewPager.addOnPageChangeListener(phoneViewListener);
    }
    //主要为了做背景颜色渐变处理
    private ViewPager.OnPageChangeListener pageColorListener=new ViewPager.OnPageChangeListener() {
        final ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        // 在Scroll过程中进行触发的方法
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // 第一个页面到第二个页面之间
            if (position == 0) {
                int color = (int) argbEvaluator.evaluate(positionOffset, colorGreen, colorRed);
                frameLayout.setBackgroundColor(color);
                return;
            }
            // 第二个页面到第三个页面之间
            if (position == 1) {
                int color = (int) argbEvaluator.evaluate(positionOffset, colorRed, colorYellow);
                frameLayout.setBackgroundColor(color);
            }
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
            // 第一个页面到第二个页面之间
            if (position == 0) {
                // 手机的缩放处理
                float scale = 0.3f + positionOffset * 0.7f;
                layoutPhone.setScaleX(scale);
                layoutPhone.setScaleY(scale);
                // 手机的平移处理
                int scroll = (int) ((positionOffset - 1) * 100);
                layoutPhone.setTranslationX(scroll);
                // 手机字体的渐变
                ivPhoneFont.setAlpha(positionOffset);
                return;
            }
            // 在第二个页面和第三个页面之间
            if (position == 1) {
                layoutPhone.setTranslationX(-positionOffsetPixels);
            }
        }

        @Override
        public void onPageSelected(int position) {
            // 当显示出最后一个pager时，播放它自己的动画
            if (position == 2) {
                pager2 pager2View = (pager2) adapter.getView(position);
                pager2View.showAnimation();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
