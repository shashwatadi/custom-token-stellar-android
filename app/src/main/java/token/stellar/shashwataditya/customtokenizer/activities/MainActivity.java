package token.stellar.shashwataditya.customtokenizer.activities;

import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;

import token.stellar.shashwataditya.customtokenizer.R;
import token.stellar.shashwataditya.customtokenizer.services.Horizon;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import org.stellar.sdk.Asset;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.Memo;

public class MainActivity extends AppCompatActivity {
    //Funded
    private final String Account1ID = "GA6WKR5KOIGT25NFWBOO7PBMZDUADZYAS6R3TQ5VUMF637NHYEJ344CJ";
    private final String Account1Seed = "CAPX6S2FNWXDEKGVSHO6UR7COK3AW3WRVXIUC4RLFETZB2RPA4ICLVP";
    private final String Account2ID = "GBRJAV4AW5R5KYZ42LCEYFDGMPNR22AOWNIUKTPDJF5HWD7X4MSC3NJX";
    private String Account2Seed = "SBUFEGGYJS2WIG5TWRRTPFSTYJFCCPXRU2TW4FUNLT7Q3OFPG6NLUDZY";
    private String AccountSeed = "SBQQ7TI6OGAXE7GSWMTIFW43UEWOLRXXZZMWYSS5Y6F3FKGU2E6CML4X";
    public String AccoutID = "GAXFJRA7T4W2AYFU25GMHCQZ3MGMYIVBFLTG5XPYVW6ZBYMPKDRG5UYZ";

    public byte[] seed;
    //Non-funded
    private final String Account3ID = "GBDRQUCU6HVEXZ4LP4R3RGOHGLJK2NZPFSVIDQ2USUKYL4LTPGHOOQZQ";
    private final String Account3Seed = "SBTG43ZABRMF3537DZ5PRCUZVIY36DYLNOXNGZFX37AS4627ULUBEO5D";
    private KeyPair pair, issuingKeys, otherKeys;

    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;

    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //testRun();
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);


        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new CreateFragment(), "Create");
        adapter.addFragment(new TransactFragment(), "Transact");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


      /*  Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);







        mDrawerLayout = findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) mDrawerLayout.findViewById(R.id.nav_view);
        Log.i("Check", "Val: " + R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        selectDrawerItem(menuItem);
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });*/
    }

    public void selectDrawerItem(MenuItem item){
        navigationView.inflateMenu(R.menu.drawer_view);
        Fragment fragment = null;
        Class fragmentClass = null;
        Log.i("ITEM", "ID: "+item.getItemId());

        switch (item.getItemId()) {
          /*  case android.R.id.create:
                fragmentClass = CreateFragment.class;
                break;
            case android.R.id.transact:
                fragmentClass = TransactFragment.class;
                break;*/
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

       /* // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
*/

        //TODO: Set MenuBar Title here

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment fragment = null;
        Class fragmentClass;

        switch (item.getItemId()) {
            /*case android.R.id.create:
                fragmentClass = CreateFragment.class;
                break;
            case android.R.id.transact:
                fragmentClass = TransactFragment.class;
                break;*/
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void testRun(){
        pair = KeyPair.fromSecretSeed("SBQQ7TI6OGAXE7GSWMTIFW43UEWOLRXXZZMWYSS5Y6F3FKGU2E6CML4X");
        issuingKeys = KeyPair.fromSecretSeed("SBUFEGGYJS2WIG5TWRRTPFSTYJFCCPXRU2TW4FUNLT7Q3OFPG6NLUDZY");
        otherKeys = KeyPair.fromSecretSeed("SAFKGZ7FFICE2ZTAUBWJOD2LNKD4ONSCVYVWW4SIHDR7KSKKUJMULQK5");
        //seed.equals("SBQQ7TI6OGAXE7GSWMTIFW43UEWOLRXXZZMWYSS5Y6F3FKGU2E6CML4X".getBytes());
        final Horizon horizon = new Horizon();
       // System.out.println("Seed Length: "+seed.length);
        //horizon.updateAccountBalance(pair);
        //horizon.createDistributionAccount(issuingKeys, pair, "EPIC", "1000");
        Asset customToken = Asset.createNonNativeAsset("EPIC", issuingKeys);
        horizon.getChangeTrust(false, otherKeys, customToken, "1000");
        horizon.updateAccountBalance(otherKeys);
        horizon.getSendToken(customToken, "50", Memo.text("--"), pair, otherKeys);
        horizon.updateAccountBalance(otherKeys);
    }
}
