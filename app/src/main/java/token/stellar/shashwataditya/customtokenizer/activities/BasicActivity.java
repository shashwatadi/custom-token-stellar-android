package token.stellar.shashwataditya.customtokenizer.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import token.stellar.shashwataditya.customtokenizer.R;

public class BasicActivity extends AppCompatActivity {

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    char profileValue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

//        if (savedInstanceState == null) {
//            Bundle extras = getIntent().getExtras();
//            if(extras == null) {
//                profileValue = '\0';
//            } else {
//                profileValue= extras.getChar("PROFILE");
//            }
//        } else {
//            profileValue= (char) savedInstanceState.getSerializable("PROFILE");
//        }

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new CreateFragment(), "Create");
        adapter.addFragment(new TransactFragment(), "Transact");
        //adapter.addFragment(new HistoryFragment(), "History");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
