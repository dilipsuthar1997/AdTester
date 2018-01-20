package com.demo.adtester;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class MainActivity extends AppCompatActivity {

    int CoinAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button AdBtn = (Button) findViewById(R.id.adBtn);
        Button videoAdBtn = (Button) findViewById(R.id.adBtn1);
        final TextView currentCoin = (TextView) findViewById(R.id.getCoinTxt);

        //Showing - BANNER Ad
        AdView mAdView = (AdView) findViewById(R.id.adView);
        final AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Showing - INTERSTITIAL Ad
        AdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final InterstitialAd interstitial = new InterstitialAd(getApplicationContext());
                interstitial.setAdUnitId(getString(R.string.ad_interstitial));
                interstitial.loadAd(adRequest);

                interstitial.setAdListener(new AdListener() {

                    @Override
                    public void onAdClosed() {
                        Toast.makeText(MainActivity.this, "Ad Closed", Toast.LENGTH_SHORT).show();
                        super.onAdClosed();
                    }

                    @Override
                    public void onAdFailedToLoad(int i) {
                        Toast.makeText(MainActivity.this, "Ad Failed", Toast.LENGTH_SHORT).show();
                        super.onAdFailedToLoad(i);
                    }

                    @Override
                    public void onAdLeftApplication() {
                        Toast.makeText(MainActivity.this, "Ad Selected", Toast.LENGTH_SHORT).show();
                        super.onAdLeftApplication();
                    }

                    @Override
                    public void onAdOpened() {
                        Toast.makeText(MainActivity.this, "Ad Opened", Toast.LENGTH_SHORT).show();
                        super.onAdOpened();
                    }

                    @Override
                    public void onAdLoaded() {
                        interstitial.show();
                        Toast.makeText(MainActivity.this, "Ad Loaded", Toast.LENGTH_SHORT).show();
                        super.onAdLoaded();
                    }

                    @Override
                    public void onAdClicked() {
                        Toast.makeText(MainActivity.this, "Ad Clicked", Toast.LENGTH_SHORT).show();
                        super.onAdClicked();
                    }

                    @Override
                    public void onAdImpression() {
                        Toast.makeText(MainActivity.this, "Ad Impression", Toast.LENGTH_SHORT).show();
                        super.onAdImpression();
                    }
                } );
            }
        });

        //Showing - REWARDED VIDEO Ad
        final RewardedVideoAd RewardAd = MobileAds.getRewardedVideoAdInstance(this);
        RewardAd.loadAd(String.valueOf(R.string.ad_video), new AdRequest.Builder().build());
        MobileAds.initialize(this);

        videoAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(RewardAd.isLoaded()) {
                    RewardAd.show();
                } else {
                    RewardAd.loadAd(String.valueOf(R.string.ad_video), new AdRequest.Builder().build());
                }
            }
        });

        RewardAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                Toast.makeText(MainActivity.this, "VideoAdLoaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdOpened() {
                Toast.makeText(MainActivity.this, "VideoAdOpened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoStarted() {
                Toast.makeText(MainActivity.this, "VideoAdStarted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdClosed() {
                Toast.makeText(MainActivity.this, "VideoAdClosed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewarded(RewardItem rewardItem) {
                CoinAmount = CoinAmount + 10;
                currentCoin.setText("COINS : " + CoinAmount);
                Toast.makeText(MainActivity.this, "You Get 10 Points", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdLeftApplication() {
                Toast.makeText(MainActivity.this, "LinkOpened", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Toast.makeText(MainActivity.this, "VideoAdFailed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
