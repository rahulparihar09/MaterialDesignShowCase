package git.rahulparihar.materialdesignshowcase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonRecycler).setOnClickListener(this);
        findViewById(R.id.buttonColor).setOnClickListener(this);
        findViewById(R.id.buttonDialer).setOnClickListener(this);
        findViewById(R.id.buttonElevation).setOnClickListener(this);
        findViewById(R.id.buttonSliding).setOnClickListener(this);
        findViewById(R.id.buttonTransition).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonRecycler:
                startActivity(new Intent(this, RecyclerSampleActivity.class));
                break;

            case R.id.buttonColor:
                startActivity(new Intent(this, ColorActivity.class));
                break;

            case R.id.buttonDialer:
                startActivity(new Intent(this, DialerSampleActivity.class));
                break;

            case R.id.buttonElevation:
                startActivity(new Intent(this, ElevationSampleActivity.class));
                break;

            case R.id.buttonSliding:
                startActivity(new Intent(this, SlidingActivity.class));
                break;

            case R.id.buttonTransition:
                startActivity(new Intent(this, TransitionFirstActivity.class));
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
