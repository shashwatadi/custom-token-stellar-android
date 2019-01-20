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
    private static BasicActivity instance;

    public static BasicActivity getConfig(){
        return instance;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new CreateFragment(), "Create");
        adapter.addFragment(new TransactFragment(), "Transact");
        //adapter.addFragment(new HistoryFragment(), "History");


        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                if(i == 1) {
                    TransactFragment transactFragment = (TransactFragment) adapter.getItem(i);
                    transactFragment.updateBalance();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }
}
