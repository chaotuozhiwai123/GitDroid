package end.feicui.gitdroid.splash.pager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import end.feicui.gitdroid.R;

/**
 * Created by Administrator on 2016/7/26.
 */
public class pager0 extends FrameLayout {
    public pager0(Context context) {
        super(context,null);
    }

    public pager0(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public pager0(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.content_pager_0, this,true);
    }

}
