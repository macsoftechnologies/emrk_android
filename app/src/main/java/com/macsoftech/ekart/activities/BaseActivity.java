package com.macsoftech.ekart.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.macsoftech.ekart.BuildConfig;
import com.macsoftech.ekart.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import id.zelory.compressor.Compressor;


public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG) {
            Log.e(getClass().getSimpleName(), "onCreate: called");
        }
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

    private ProgressDialog progressDialog;

    public void showProgress() {
        showProgress("Please wait..");
    }

    public void showProgress(String title) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage(title);
        progressDialog.setCanceledOnTouchOutside(false);
        if (!isFinishing()) {
            progressDialog.show();
        } else {
            progressDialog = null;
        }
    }

    public void hideDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    //
    protected long lastModifiedTime;
    public File photoFile;
    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE_300 = 300;
    public static final int GALLERY_PICK_REQUEST_CODE_400 = 400;
    public static final String IMAGE_DIRECTORY_NAME = ".CCE";


    public void openCamera() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_CAPTURE_IMAGE_REQUEST_CODE_300);
            return;
        }
        File file = new File(getExternalFilesDir(null), IMAGE_DIRECTORY_NAME);
        if (!file.isDirectory()) {
            file.mkdirs();
        }
        if (!file.exists()) {
            file.mkdirs();
        }

        photoFile = new File(file.getPath() + "/"
                + System.currentTimeMillis() + ".jpg");
        Uri tempUri = Uri.fromFile(photoFile);
        Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            photoCaptureIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, getPackageName() + ".fileProvider", photoFile);
            photoCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            photoCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        }
        startActivityForResult(photoCaptureIntent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE_300);
//        mLog.i("BaseActivity", "photoFile:" + photoFile.getAbsolutePath());
    }

    public void choosePhoto() {

        AlertDialog.Builder photoUploadAlert = new AlertDialog.Builder(this);
        photoUploadAlert.setTitle("Select Option");

        photoUploadAlert.setItems(new String[]{"Capture Photo",
                "Pick from Gallery"}, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        openCamera();
                        break;
                    case 1:
                        startGallery();
                        break;

                }
                dialog.dismiss();
            }
        });
        AlertDialog uploadPhotoDialog = photoUploadAlert.create();
        uploadPhotoDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface dialog) {

            }
        });
        uploadPhotoDialog.show();

    }

    public void startGallery() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, GALLERY_PICK_REQUEST_CODE_400);
            return;
        }

        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        startActivityForResult(i, GALLERY_PICK_REQUEST_CODE_400);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment visibleFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (visibleFragment != null) {
            visibleFragment.onActivityResult(requestCode, resultCode, data);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
            //
            if (requestCode == GALLERY_PICK_REQUEST_CODE_400
                    && resultCode == Activity.RESULT_OK
                    && data != null) {
                Uri selectedImage = data.getData();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);

                if (cursor == null || cursor.getCount() < 1) {
                    return;
                }
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                if (columnIndex < 0) // no column index
                    return; // DO YOUR ERROR HANDLING

                String picturePath = cursor.getString(columnIndex);
                cursor.close(); // close cursor
//                File file = new File(Environment.getExternalStorageDirectory(),
//                        IMAGE_DIRECTORY_NAME);
                File file = new File(getExternalFilesDir(null), IMAGE_DIRECTORY_NAME);

                if (!file.isDirectory()) {
                    file.mkdirs();
                }
                photoFile = new File(file.getPath() + "/"
                        + System.currentTimeMillis() + ".jpg");

                if (!photoFile.getParentFile().exists()) {
                    photoFile.getParentFile().mkdirs();
                }
                lastModifiedTime = new File(picturePath).lastModified();

                copy(new File(picturePath), photoFile);
                Compressor compressor = new Compressor(this.getApplicationContext());
                try {
                    photoFile = compressor.compressToFile(photoFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                UCrop.of(Uri.parse(photoFile.getAbsolutePath()), Uri.parse(photoFile.getAbsolutePath()))
////                        .withAspectRatio(16, 9)
//                        .start(this);
            }
        }

    }

    public void copy(File src, File dst) {
        try {
            FileInputStream inStream = new FileInputStream(src);
            FileOutputStream outStream = new FileOutputStream(dst);
            FileChannel inChannel = inStream.getChannel();
            FileChannel outChannel = outStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            inStream.close();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE_300) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                if (getAnyView() != null) {
                    final Snackbar snackbar = Snackbar.make(getAnyView(), "Camera/Storage Permissions Denied. Please try again", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    snackbar.dismiss();
                                    openCamera();
                                }
                            }
                    );
                    snackbar.show();
                }

            }
        } else if (requestCode == GALLERY_PICK_REQUEST_CODE_400) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startGallery();
            } else {
                if (getAnyView() != null) {
                    final Snackbar snackbar = Snackbar.make(getAnyView(), "Storage Permissions Denied. Please try again", Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    snackbar.dismiss();
                                    startGallery();
                                }
                            }
                    );
                    snackbar.show();
                }

            }
        }
    }

    private View getAnyView() {
        return null;
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}


