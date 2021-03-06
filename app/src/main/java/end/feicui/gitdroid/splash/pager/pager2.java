package end.feicui.gitdroid.splash.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import butterknife.BindView;
import butterknife.ButterKnife;
import end.feicui.gitdroid.R;

/**
 * Created by Administrator on 2016/7/26.
 */
public class pager2 extends FrameLayout {
    @BindView(R.id.ivBubble1)
    ImageView ivBubble1;
    @BindView(R.id.ivBubble2)
    ImageView ivBubble2;
    @BindView(R.id.ivBubble3)
    ImageView ivBubble3;

    public pager2(Context context) {
        this(context, null);
    }

    public pager2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public pager2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.content_pager_2, this, true);
        ButterKnife.bind(this);
        ivBubble1.setVisibility(View.GONE);
        ivBubble2.setVisibility(View.GONE);
        ivBubble3.setVisibility(View.GONE);
    }
    /** 用来显示当前页面内三张图像的进入动画，只显示一次*/
    public void showAnimation() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                ivBubble1.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInLeft).duration(300).playOn(ivBubble1);
            }
        }, 50);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                ivBubble2.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInLeft).duration(300).playOn(ivBubble2);
            }
        }, 550);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                ivBubble3.setVisibility(View.VISIBLE);
                YoYo.with(Techniques.FadeInLeft).duration(300).playOn(ivBubble3);
            }
        }, 1050);
    }
}
