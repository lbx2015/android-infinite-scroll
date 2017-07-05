package lbx.com.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ViewFlipper;

/**
 * Created by zw.zhang on 2017/7/5.
 */

public class ViewFlipperActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_viewflipper);
        ViewFlipper flipper = (ViewFlipper) findViewById(R.id.flipper);
        flipper.startFlipping();//开始切换，注意，如果设置了时间间隔，想让它自动切换，一定要记得加它
    }
}
