package com.presentation.app.dealsnest.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.presentation.app.dealsnest.BuildConfig;
import com.presentation.app.dealsnest.R;

public class ReferAFriendActivity extends BaseActivity {

    private TextView tvToolbarText;
    private ImageView imShareWhatsapp, imShareFacebook, imSharetwitter, imMore;

    public static Intent getActivityIntent(Context context) {
        return new Intent(context, ReferAFriendActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvToolbarText = findViewById(R.id.tvToolbarText);
        tvToolbarText.setText("Refer a friend");

        imShareWhatsapp = findViewById(R.id.icon_whatsapp);
        imShareFacebook = findViewById(R.id.icon_facebook);
        imSharetwitter = findViewById(R.id.icon_twitter);
        imMore = findViewById(R.id.icon_more);


        imShareWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsappIntent = new Intent();
                whatsappIntent.setAction(Intent.ACTION_SEND);
                whatsappIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                String shareMessage = "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "Whatsapp have not been installed.", Toast.LENGTH_LONG).show();
                }
            }
        });
        imShareFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsappIntent = new Intent();
                whatsappIntent.setAction(Intent.ACTION_SEND);
                whatsappIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                String shareMessage = "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.facebook.katana");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "Facebook have not been installed.", Toast.LENGTH_LONG).show();
                }
            }
        });
        imSharetwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsappIntent = new Intent();
                whatsappIntent.setAction(Intent.ACTION_SEND);
                whatsappIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                String shareMessage = "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.twitter.android");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                try {
                    startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "Twitter have not been installed.", Toast.LENGTH_LONG).show();
                }
            }
        });
        imMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                String shareMessage = "\nLet me recommend you this application\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.app_name)));
            }
        });

    }


    @Override
    protected boolean setToolbar() {
        return true;
    }

    @Override
    public int setLayout() {
        return R.layout.activity_refer_afriend;
    }

    @Override
    public void reloadPage() {

    }

}
