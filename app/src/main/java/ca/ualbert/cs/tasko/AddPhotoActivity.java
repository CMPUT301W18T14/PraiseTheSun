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

/**
 * Activity that adds a photo to a task. Is called by AddTaskActivity when the user presses the
 * "Add Photo" button from AddTaskActivity.
 *
 * @author tlafranc
 * @see AddTaskActivity
 */
public class AddPhotoActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    private Bitmap image;
    private Button confirm;

    /**
     * Called when the activity is started. Initializes the confirm button.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        confirm = (Button) findViewById(R.id.addPhotoConfirmButton) ;
        confirm.setEnabled(false);
    }

    /**
     * Called when the clicks on the "Upload Image" button in this activity. Creates an intent to
     * go to the users gallery.
     *
     */
    public void onUploadClick(View view) {
        /*
         * Code on making an intent to the user's gallery
         * https://www.youtube.com/watch?v=e8x-nu9-_BM
         * Taken on 2018-03-17
         */
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media
                .EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, RESULT_LOAD_IMAGE);
    }

    /**
     * Called when the user clicks the "Confirm Upload" button. This button can only be clicked
     * after the user has selected an image from his gallery. Turns the image chosen into a byte
     * stream and sends this back to AddTaskActivity.
     *
     */
    public void onConfirmClick(View view) {
        /*
         * Code on how to properly send a bitmap object through an intent was taken from
         * https://stackoverflow.com/questions/11010386/passing-android-bitmap-data-within
         * -activity-using-intent-in-android
         * Taken on 2018-03-17
         */

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte [] byteArray = stream.toByteArray();
        /*
         * Code on checking the size of an image was taken from
         * https://stackoverflow.com/questions/29137003/how-to-check-image-size-less-then-100kb
         * -android
         * Taken on 2018-03-18
         */
        long imageLength = byteArray.length;
        if (imageLength <= 65535) {
            Intent returnImage = new Intent();
            returnImage.putExtra("image", byteArray);
            setResult(RESULT_OK, returnImage);
            finish();
        }
        else {
            Toast.makeText(this.getApplicationContext(), "Image file chosen is too big.", Toast
                    .LENGTH_LONG).show();
        }
    }

    /**
     * Called after the user exits his gallery by either selecting a photo or pressing the back
     * button. If he pressed the back button, do nothing. If the user selected a photo, create an
     * input stream and make an Bitmap object from this stream.
     *
     * @param requestCode Code created when creating intent for gallery
     * @param resultCode Code sent from called activity indicating if task was completed
     * @param data Information sent back from activity

     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_LOAD_IMAGE && data != null) {
                /*
                 * Code on retrieving an image from the gallery was taken from
                 * https://www.youtube.com/watch?v=_xIWkCJZCu0 and
                 * https://www.youtube.com/watch?v=e8x-nu9-_BM
                 * Taken on 2018-03-16
                 */
                Uri selectedImage = data.getData();

                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(selectedImage);
                    image = BitmapFactory.decodeStream(inputStream);
                    ImageView imageView = (ImageView) findViewById(R.id.addPhotoTaskImage);
                    imageView.setImageBitmap(image);
                    confirm.setEnabled(true);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this.getApplicationContext(), "Image not found", Toast
                            .LENGTH_LONG).show();
                }
            }
        }
    }
}
