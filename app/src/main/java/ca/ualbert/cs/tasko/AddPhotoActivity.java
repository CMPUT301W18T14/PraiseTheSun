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

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;

// https://stackoverflow.com/questions/19585815/select-multiple-images-from-android-gallery
// https://stackoverflow.com/questions/23426113/how-to-select-multiple-images-from-gallery-in-android

/**
 * Activity that adds a photo to a task. Is called by AddTaskActivity when the user presses the
 * "Add Photo" button from AddTaskActivity.
 *
 * @author tlafranc
 * @see AddTaskActivity
 */
public class AddPhotoActivity extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;
    private ArrayList<Bitmap> images;
    private ImageSwitcher switcher;
    private ImageView imageView;
    private TextView textView;
    private int numImages = 0;
    private int position = 0;
    private Context context;


    /**
     * Called when the activity is started.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        context = this;

        imageView = (ImageView) findViewById(R.id.addPhotoImageView);
        switcher = (ImageSwitcher) findViewById(R.id.addPhotoImageSwitcher);
        textView = (TextView) findViewById(R.id.addPhotoTextView);
        images = new ArrayList<Bitmap>();

        /*
         * Determine if there are photos already added. If so, initialize the images array and
         * display the first image
         */
        ArrayList<byte[]> imageBytes = (ArrayList<byte[]>) getIntent().getSerializableExtra
                ("photos");
        if (imageBytes != null) {
            numImages = imageBytes.size();
            if (numImages > 0) {
                for (int i = 0; i < numImages; i++) {
                    Bitmap image = BitmapFactory.decodeByteArray(imageBytes.get(i), 0,
                            imageBytes.get(i).length);
                    images.add(image);
                }
                imageView.setImageBitmap(images.get(0));
                textView.setText("Swipe to view other photos.\n Tap to delete a photo already " +
                        "chosen.\nViewing photo 1/" + Integer.toString(numImages));
            }
        }

        /*
         * I used these resources in order to create a swipeable view
         * https://stackoverflow.com/questions/15799839/motionevent-action-up-not-called
         * http://codetheory.in/android-ontouchevent-ontouchlistener-motionevent-to-detect-common-gestures/
         * https://developer.android.com/training/gestures/detector.html
         * accessed on 2018-03-28
         *
         */
        switcher.setOnTouchListener(new View.OnTouchListener() {
            private float initialX;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                //Log.i("Not Error", Integer.toString(numImages));
                if (numImages > 0) {
                    float finalX;
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            initialX = event.getX();
                            return true;
                        case MotionEvent.ACTION_UP:
                            finalX = event.getX();
                            if (initialX > finalX) {
                                if (position < (numImages - 1)) {
                                    position++;
                                    imageView.setImageBitmap(images.get(position));
                                    switcher.showNext();
                                    textView.setText("Swipe to view other photos.\n Tap to " +
                                            "delete a photo already chosen. Viewing photo "
                                            + Integer.toString(position + 1) + "/" + Integer
                                            .toString(numImages));
                                } else {
                                    Toast.makeText(getApplicationContext(), "No More Images To Swipe",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                            else if (initialX < finalX){
                                if (position > 0) {
                                    position--;
                                    imageView.setImageBitmap(images.get(position));
                                    switcher.showPrevious();
                                    textView.setText("Swipe to view other photos.\n Tap to " +
                                            "delete a photo already chosen. Viewing photo "
                                            + Integer.toString(position + 1) + "/" + Integer
                                            .toString(numImages));
                                } else {
                                    Toast.makeText(getApplicationContext(), "No More Images To Swipe",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                                builder1.setMessage("Are you sure you want to delete this " +
                                        "photo?");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Yes",
                                        new DialogInterface.OnClickListener() {
                                            @SuppressLint("SetTextI18n")
                                            public void onClick(DialogInterface dialog, int id) {
                                                images.remove(position);
                                                numImages -= 1;
                                                if (numImages > 0) {
                                                    if (position > 0) {
                                                        position--;
                                                    }
                                                    imageView.setImageBitmap(images.get(position));
                                                    switcher.showPrevious();
                                                    textView.setText("Swipe to view other photos.\n Tap to " +
                                                            "delete a photo already chosen. Viewing photo "
                                                            + Integer.toString(position + 1) + "/" + Integer
                                                            .toString(numImages));
                                                }
                                                else {
                                                    textView.setText("No images currently added.");
                                                    /*
                                                     * https://stackoverflow.com/questions/7242282/get-bitmap-information-from-bitmap-stored-in-drawable-folder
                                                     * Taken on 2018-04-02
                                                     */
                                                    Bitmap image = BitmapFactory.decodeResource
                                                            (getResources(), R.drawable.ic_menu_gallery);
                                                    imageView.setImageBitmap(image);
                                                }
                                            }
                                        });

                                builder1.setNegativeButton(
                                        "No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();
                            }
                            break;
                    }
                    return false;
                }
                else {
                    return true;
                }
            }
        });
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
        gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(gallery, RESULT_LOAD_IMAGE);
    }

    /**
     * Called when the user clicks the "Confirm Upload" button. This button can only be clicked
     * after the user has selected an image from his gallery. Turns the image chosen into a byte
     * stream and sends this back to AddTaskActivity.
     *
     */
    public void onConfirmClick(View view) {
        if (numImages > 10) {
            Toast.makeText(this, "Too many images chosen. Only 10 images allowed per task.",
                    Toast.LENGTH_LONG).show();
        }
        else {
            ArrayList<byte[]> imgBytes = new ArrayList<byte[]>();
            /*
             * Code on how to properly send a bitmap object through an intent was taken from
             * https://stackoverflow.com/questions/11010386/passing-android-bitmap-data-within
             * -activity-using-intent-in-android
             * Taken on 2018-03-17
             */
            for (int i = 0; i < numImages; i++) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                images.get(i).compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                /*
                  * Code on converting an image into a base64 string was taken from
                  * https://stackoverflow.com/questions/4830711/how-to-convert-a-image-into-base64-string
                  * accessed on 2018-03-25
                  */
                imgBytes.add(byteArray);
                Log.d("Not Error", Integer.toString(byteArray.length));
            }

            Intent returnImages = new Intent();
            returnImages.putExtra("photos", imgBytes);
            setResult(RESULT_OK, returnImages);
            finish();
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
    @SuppressLint("SetTextI18n")
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

                /*
                 * Code on taken multiple images from the gallery
                 * https://stackoverflow.com/questions/19585815/select-multiple-images-from-android-gallery
                 * accessed 2018-03-25
                 */
                if(data.getClipData() != null) {
                    int numNewImages = data.getClipData().getItemCount();
                    for (int i = 0; i < numNewImages; i++) {
                        Uri selectedImage = data.getClipData().getItemAt(i).getUri();
                        InputStream inputStream;
                        try {
                            inputStream = getContentResolver().openInputStream(selectedImage);
                            Bitmap image = BitmapFactory.decodeStream(inputStream);

                            image = getCorrectSizeBitmap(image);
                            Log.d("BITMAPS", ""+image.getByteCount());
                            images.add(image);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(this.getApplicationContext(), "Image not found", Toast
                                    .LENGTH_LONG).show();
                        }
                    }
                    numImages += numNewImages;
                    imageView.setImageBitmap(images.get(0));
                    position = 0;
                    textView.setText("Swipe to view other photos.\n Tap to delete a photo already " +
                            "chosen.\nViewing photo 1/" + Integer.toString(numImages));
                }
            }
        }
    }

    /**
     * Recursive method that determines the appropriate constraint for an image in order for the
     * image to be under 65535 bytes.
     *
     * @param image The image that may need to be constrained.
     * @param size The size of the constraint to be tested to see if it makes the image under
     *             65535 bytes.
     * @return The properly constrained image that has size less than 65535 bytes.
     */
    private Bitmap findSize(Bitmap image, double size) {
        /*
         * Code on checking the size of an image was taken from
         * https://stackoverflow.com/questions/29137003/how-to-check-image-size-less-then-100kb
         * -android
         * Taken on 2018-03-18
         */
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        long imageLength = stream.toByteArray().length;
        if (imageLength > 65535) {
            size = size * (1.0/2.0);
            image = Bitmap.createScaledBitmap(image, (int) Math.round(image.getWidth
                    () * size), (int) Math.round(image.getHeight() * size), false);
            return findSize(image, size);
        }
        else {
            return image;
        }
    }

    /**
     * getCorrectSizeBitmap uses the desired size and the width and height of the
     * current bitmap to calculate the desired width and height that will bring the
     * bitmap down to a size <= 65535
     * @param image Bitmap to scale
     * @return scaled bitmap if its size > 65535 or the original bitmap if its size <= 65535
     */
    private Bitmap getCorrectSizeBitmap(Bitmap image){
        int size = image.getByteCount();
        BigInteger gcd = BigInteger.valueOf(image.getWidth()).gcd(BigInteger.valueOf(image.getHeight()));
        if(size <= 65535){
            return image;
        }
        int widthMultiplier = image.getWidth() / gcd.intValue();
        int heightMultiplier = image
                .getHeight() / gcd
                .intValue();
        Long x = Math.round(Math.sqrt((65535/5)/(widthMultiplier * heightMultiplier)));

        if (x.intValue() != 0) {
            Log.d("BITMAPS", "" + size + " " + image.getDensity() + " " + image.getConfig().toString());
            return Bitmap.createScaledBitmap(image, widthMultiplier * x.intValue(),
                    heightMultiplier * x.intValue(), true);
        }
        else {
            return findSize(image, 1.0);
        }
    }
}
