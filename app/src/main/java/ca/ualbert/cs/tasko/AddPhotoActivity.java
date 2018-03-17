/*
 * Copyright © 2018 Chase Buhler, Imtihan Ahmed, Thomas Lafrance, Ryan Romano, Stephen Packer,
 * Alden Emerson Ern Tan
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ca.ualbert.cs.tasko;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddPhotoActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    private ImageView imageToUpload;
    private Bitmap image;
    private Button confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        confirm = (Button) findViewById(R.id.addPhotoConfirmButton) ;
        confirm.setEnabled(false);
        imageToUpload = (ImageView) findViewById(R.id.addPhotoTaskImage);
    }

    public void onUploadClick(View view) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media
                .EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, RESULT_LOAD_IMAGE);
    }

    public void onConfirmClick(View view) {
        // https://stackoverflow.com/questions/11010386/passing-android-bitmap-data-within
        // -activity-using-intent-in-android taken on 2018-03-17
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte [] byteArray = stream.toByteArray();

        Intent returnImage = new Intent();
        returnImage.putExtra("image", byteArray);
        setResult(RESULT_OK, returnImage);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_LOAD_IMAGE && data != null) {
                Uri selectedImage = data.getData();

                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(selectedImage);
                    image = BitmapFactory.decodeStream(inputStream);
                    imageToUpload.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this.getApplicationContext(), "Image not found",
                            Toast.LENGTH_LONG).show();
                }
                confirm.setEnabled(true);
            }
        }
    }
}
