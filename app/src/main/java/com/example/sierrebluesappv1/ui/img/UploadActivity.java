        package com.example.sierrebluesappv1.ui.img;

        import android.content.ContentResolver;
        import android.content.Intent;
        import android.net.Uri;
        import android.os.Handler;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.webkit.MimeTypeMap;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.ProgressBar;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.sierrebluesappv1.R;
        import com.example.sierrebluesappv1.ui.MainActivity;
        import com.google.android.gms.tasks.Continuation;
        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnFailureListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.FirebaseApp;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.OnProgressListener;
        import com.google.firebase.storage.StorageReference;
        import com.google.firebase.storage.StorageTask;
        import com.google.firebase.storage.UploadTask;
        import com.squareup.picasso.Picasso;


        import java.net.URI;

        public class UploadActivity extends AppCompatActivity {

            private static final int IMAGE_REQUEST = 1 ;
            private Button mButtonChoose;
            private Button mButtonUpload;
            private TextView mTextViewShow;
            private EditText mEditTextFile;
            private ImageView mImageView;
            private ProgressBar mProgressBar;

            private Uri mUri ;

            private StorageReference mStorageRef;
            private DatabaseReference mDatabaseRef;
            private String TAG;

            private StorageTask mUploadTask;



            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_upload);

                mButtonChoose = findViewById(R.id.button_choose_image);
                mButtonUpload = findViewById(R.id.button_upload);
                mTextViewShow = findViewById(R.id.text_view_show_uploads);
                mEditTextFile = findViewById(R.id.edit_text_file_name);
                mImageView = findViewById(R.id.image_view);
                mProgressBar = findViewById(R.id.progress_bar);

                mStorageRef = FirebaseStorage.getInstance().getReference("images");
                mDatabaseRef = FirebaseDatabase.getInstance().getReference("images");


                mButtonChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fileChooser() ;

                    }
                });

                mButtonUpload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mUploadTask != null && mUploadTask.isInProgress()) {
                            Toast.makeText(UploadActivity.this, "Upload in Progress", Toast.LENGTH_SHORT).show();
                        } else {
                        uploadImg();

                        }

                    }
                });

                mTextViewShow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGalleryActivity();
                    }
                });

                }

            private void fileChooser() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, IMAGE_REQUEST);


            }

            @Override
            protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
                super.onActivityResult(requestCode, resultCode, data);

                if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() !=null) {
                    mUri = data.getData();

                    Picasso.with(this).load(mUri).into(mImageView);
                }
            }

            private String getFileExtension(Uri uri) {
                // Retrieve the file extension from the image
                ContentResolver cR = getContentResolver();
                MimeTypeMap mime = MimeTypeMap.getSingleton();
                return mime.getExtensionFromMimeType(cR.getType(uri));
            }

            private void uploadImg() {
                if (mUri != null) {
                    // When uploaded, the image will have as a name the time it has been uploaded plus the extension of the file
                    //FirebaseStorage storage = FirebaseStorage.getInstance();

                    //StorageReference gsRef = storage.getReferenceFromUrl("gs://sierre-blues-festival-v2.appspot.com");

                    //StorageReference imgRef = gsRef.child("images").child(System.currentTimeMillis() + "." + getFileExtension(mUri));
                   StorageReference imgRef = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mUri));

                 imgRef.putFile(mUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return mStorageRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                Log.e(TAG, "then:" + downloadUri.toString());

                                // Display the progress bar for 5 seconds
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mProgressBar.setProgress(0);
                                    }
                                }, 500);

                                Toast.makeText(UploadActivity.this, "Upload successful", Toast.LENGTH_LONG).show();

                                Upload upload = new Upload(mEditTextFile.getText().toString().trim(), downloadUri.toString());

                                mDatabaseRef.push().setValue(upload);
                            } else {
                                Toast.makeText(UploadActivity.this, "Upload failure:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
                }


            }

            private void openGalleryActivity() {
                Intent intent = new Intent(this, GalleryActivity.class);
                startActivity(intent);
            }
        }
