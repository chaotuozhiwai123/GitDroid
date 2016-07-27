package end.feicui.gitdroid.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import end.feicui.gitdroid.MainActivity;
import end.feicui.gitdroid.R;
import end.feicui.gitdroid.commons.ActivityUtils;

/**
 * Created by Administrator on 2016/7/26.
 */
public class SplashActivity extends AppCompatActivity {

    private ActivityUtils activityUtils;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        activityUtils = new ActivityUtils(this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnLogin)
    public void login() {

    }
    @OnClick(R.id.btnEnter)
    public void enter() {
        activityUtils.startActivity(MainActivity.class);
        finish();
    }
}
