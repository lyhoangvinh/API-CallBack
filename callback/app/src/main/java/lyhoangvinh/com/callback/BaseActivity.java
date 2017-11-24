package lyhoangvinh.com.callback;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by ADMIN on 11/21/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {
    public ProgressDialog progressDialog;

    protected void showProgress(String msg) {
        if (progressDialog != null && progressDialog.isShowing())
            dismissProgress();

        progressDialog = ProgressDialog.show(this, getResources().getString(R.string.app_name), msg);
    }

    protected void dismissProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProgress();
    }


    protected void showToastLong(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void showToastShort(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void showAlert(String msg) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.app_name))
                .setMessage(msg)
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
    }


    protected boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()){
            NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mobile != null && mobile.isConnectedOrConnecting() ||
                    wifi!=null && wifi.isConnectedOrConnecting()){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }
    public void checkConnectedDialogShow(){
        checkConnectedDialog().show();
    }

    private AlertDialog.Builder checkConnectedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("No Internet Connection!");
        builder.setMessage("You need to have Mobile Data or Wifi to access this.");
        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                startActivity(intent);
            }
        });
        return builder;
    }
}
