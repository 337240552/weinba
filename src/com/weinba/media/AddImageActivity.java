
package com.weinba.media;

import com.weinba.ActivityBase;
import com.weinba.Connector;
import com.weinba.Settings;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;

public class AddImageActivity extends ActivityBase {
    private static final int CAMERA_ACTIVITY = 0;
    private static final int MAX_HEIGHT = 1024;
    private static final int MAX_WIDTH = 1024;
    private static final int PICKER_ACTIVITY = 1;

    private static final String TAG = "AddImageActivity";
    private static final String TMP_FILE = "tmp_oo.jpg";

    protected AddImageActivity m_actAddImage;
    protected Bitmap m_bmpImage;
    protected Button m_buttonFromCamera;
    protected Button m_buttonFromGallery;
    protected Button m_buttonSubmit;
    protected EditText m_editDesc;
    protected EditText m_editTags;
    protected EditText m_editTitle;
    protected String m_sAlbumName;
    protected ImageView m_viewImage;

    private void showToast(String paramString) {
        Toast.makeText(this, paramString, 0).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("AddImageActivity", "Result code = " + resultCode);
        Log.i("AddImageActivity", "Request code = " + requestCode);

        if (resultCode != 0) {
            Log.i("AddImageActivity", "intent = " + data);

            if ((requestCode == CAMERA_ACTIVITY) || (requestCode == PICKER_ACTIVITY)) {
                Bitmap localBitmap = null;

                // 如果选择图片的话
                if (requestCode != CAMERA_ACTIVITY) {
                    Uri localUri = data.getData();
                    Object localObject2 = localUri.toString();

                    if (!localUri.toString().contains("content:")) {

                        localBitmap = BitmapFactory.decodeFile(((String) localObject2).replace(
                                "file://", ""));

                    } else {

                        String[] projectioin = new String[4];
                        projectioin[0] = "_id";
                        projectioin[1] = "_data";
                        projectioin[2] = "mime_type";
                        projectioin[3] = "orientation";
                        Object localObject3 = managedQuery(localUri, projectioin, null, null, null);
                        if (!((Cursor) localObject3).moveToFirst()) {
                            localBitmap = null;
                        } else {
                            int m = ((Cursor) localObject3).getColumnIndex("_data");
                            int i = ((Cursor) localObject3).getColumnIndex("mime_type");
                            int k = ((Cursor) localObject3).getColumnIndex("orientation");
                            Log.i("AddImageActivity",
                                    "orientation = " + ((Cursor) localObject3).getString(k));
                            Log.i("AddImageActivity",
                                    "data = " + ((Cursor) localObject3).getString(m));
                            Log.i("AddImageActivity",
                                    "mime/type = " + ((Cursor) localObject3).getString(i));
                            localBitmap = BitmapFactory.decodeFile(((Cursor) localObject3)
                                    .getString(m));
                        }
                    }
                } else {// 拍照方式
                    Bundle localBundle = null;
                    if (data != null) {
                        localBundle = data.getExtras();

                        if (localBundle != null) {
                            Bitmap bitmap = (Bitmap) localBundle.get("data");
                            this.m_bmpImage = ((Bitmap) bitmap);
                            if (bitmap != null) {
                                Log.i("AddImageActivity", "Bundle = " + localBundle);
                                localBitmap = (Bitmap) localBundle.get("data");
                            }
                        }
                        
                    }
                    Log.i("AddImageActivity", "Bundle is NULL");

                    /*
                     * Object localObject1 = new
                     * File(Environment.getExternalStorageDirectory(),
                     * TMP_FILE); Log.i("AddImageActivity", "Path = " +
                     * Environment.getExternalStorageDirectory());
                     * Log.i("AddImageActivity", "AbsolutePath = " +
                     * ((File)localObject1).getAbsolutePath()); localBitmap =
                     * BitmapFactory
                     * .decodeFile(((File)localObject1).getAbsolutePath());
                     */

                }
                Log.i(TAG, "DISK  file bitmap");
                int j = localBitmap.getWidth();
                int m = localBitmap.getHeight();
                float f;
                if (j <= m)
                    f = 1024.0F / m;
                else
                    f = 1024.0F / j;
                Object localObject3 = new Matrix();
                ((Matrix) localObject3).postScale(f, f);
                this.m_bmpImage = Bitmap.createBitmap(localBitmap, 0, 0, j, m,
                        (Matrix) localObject3, true);
                localBitmap.recycle();
                this.m_viewImage.setImageBitmap(this.m_bmpImage);
                showToast(getString(2130968684));
            }
        } else {
            showToast(getString(2130968683));
        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle, false);

        setContentView(2130903048);
        this.m_buttonSubmit = ((Button) findViewById(2131165213));
        this.m_buttonFromCamera = ((Button) findViewById(2131165208));
        this.m_buttonFromGallery = ((Button) findViewById(2131165209));
        this.m_editTitle = ((EditText) findViewById(2131165210));
        this.m_editTags = ((EditText) findViewById(2131165211));
        this.m_editDesc = ((EditText) findViewById(2131165212));
        this.m_viewImage = ((ImageView) findViewById(2131165207));
        if (this.m_bmpImage != null)
            this.m_viewImage.setImageBitmap(this.m_bmpImage);
        this.m_actAddImage = this;
        this.m_sAlbumName = getIntent().getStringExtra("album_name");

        this.m_buttonFromCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                File localFile = new File(Environment.getExternalStorageDirectory(), TMP_FILE);
                Intent localIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                localIntent.putExtra("output", Uri.fromFile(localFile));
                AddImageActivity.this.startActivityForResult(localIntent, CAMERA_ACTIVITY);
            }
        });

        // pick from gallery
        this.m_buttonFromGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                Intent localIntent = new Intent("android.intent.action.PICK");
                localIntent.setType("image/*");
                AddImageActivity.this.startActivityForResult(localIntent, PICKER_ACTIVITY);
            }
        });

        // 添加上传照片按钮监听器 开始
        this.m_buttonSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                Object localObject1 = Settings.getConnector();

                // 照片不为空，描述不为空
                if ((AddImageActivity.this.m_editTitle.getText().length() != 0)
                        && (AddImageActivity.this.m_bmpImage != null)) {
                    Object localObject2 = new ByteArrayOutputStream();
                    AddImageActivity.this.m_bmpImage.compress(Bitmap.CompressFormat.JPEG, 75,
                            (OutputStream) localObject2);
                    byte[] byteArray = ((ByteArrayOutputStream) localObject2).toByteArray();// localObject2
                    Object[] arrayOfObject = new Object[8];
                    arrayOfObject[0] = ((Connector) localObject1).getUsername();
                    arrayOfObject[1] = ((Connector) localObject1).getPassword();
                    arrayOfObject[2] = AddImageActivity.this.m_sAlbumName;
                    arrayOfObject[3] = localObject2;

                    arrayOfObject[4] = new Integer(byteArray.length).toString();
                    arrayOfObject[5] = AddImageActivity.this.m_editTitle.getText().toString();
                    arrayOfObject[6] = AddImageActivity.this.m_editTags.getText().toString();
                    arrayOfObject[7] = AddImageActivity.this.m_editDesc.getText().toString();

                    // 上传图片
                    ((Connector) localObject1).execAsyncMethod("dolphin.uploadImage",
                            arrayOfObject,
                            new Connector.Callback() {

                                public void callFinished(Object paramObject) {
                                    Log.d("AddImageActivity", "dolphin.uploadImage result: "
                                            + paramObject.toString());

                                    Object localObject;
                                    if (paramObject.toString().equals("ok")) {// 上传成功
                                        localObject = Settings.getConnector();
                                        ((Connector) localObject).setImagesReloadRequired(true);
                                        ((Connector) localObject).setAlbumsReloadRequired(true);
                                        AddImageActivity.this.finish();
                                    } else {// 上传失败
                                        String str1 = AddImageActivity.this.getString(2130968687);
                                        String str2 = AddImageActivity.this.getString(2130968686);
                                        localObject = new AlertDialog.Builder(
                                                AddImageActivity.this.m_actAddImage)
                                                .create();
                                        ((AlertDialog) localObject).setTitle(str1);
                                        ((AlertDialog) localObject).setMessage(str2);
                                        ((AlertDialog) localObject).setButton(-3,
                                                AddImageActivity.this.getString(2130968579),
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(
                                                            DialogInterface paramDialogInterface,
                                                            int paramInt) {
                                                        paramDialogInterface.dismiss();
                                                        AddImageActivity.this.finish();
                                                    }
                                                });
                                        ((AlertDialog) localObject).show();
                                    }
                                }

                            }, AddImageActivity.this.m_actAddImage);

                } else {// 出错了
                    localObject1 = new AlertDialog.Builder(AddImageActivity.this.m_actAddImage)
                            .create();
                    ((AlertDialog) localObject1).setTitle(AddImageActivity.this
                            .getString(2130968687));
                    ((AlertDialog) localObject1).setMessage(AddImageActivity.this
                            .getString(2130968685));
                    ((AlertDialog) localObject1).setButton(-3,
                            AddImageActivity.this.getString(2130968579),
                            new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface paramDialogInterface, int paramInt)
                    {
                        paramDialogInterface.dismiss();
                    }
                    });
                    ((AlertDialog) localObject1).show();
                }

            }
        });
        // 添加上传照片按钮监听器 完成

    }
}
