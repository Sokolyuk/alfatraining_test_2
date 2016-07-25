package de.sda.einkaufsliste;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import de.sda.einkaufsliste.controller.aboutactivity.OnClickListener;
import de.sda.einkaufsliste.service.FileService;

/**
 * Created by Dmitry Sokolyuk on 25.07.2016.
 */
public class AboutActivity extends AppCompatActivity {

    private FileService mFileService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        OnClickListener clickListener = new OnClickListener();

        ((Button)findViewById(R.id.btnDbBackupToSdCard)).setOnClickListener(clickListener);
        ((Button)findViewById(R.id.btnSupportCall)).setOnClickListener(clickListener);

    }

    public FileService getFileService() {
        return mFileService;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, FileService.class);
        bindService(intent, mFileServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mFileServiceConnection);
            mBound = false;
        }
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mFileServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            FileService.FileServiceBinder binder = (FileService.FileServiceBinder) service;
            mFileService = binder.getService();
            mBound = true;
//!!!
            Log.d(MainActivity.class.toString(), "onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
//!!!
            Log.d(MainActivity.class.toString(), "onServiceDisconnected");
        }
    };


}
