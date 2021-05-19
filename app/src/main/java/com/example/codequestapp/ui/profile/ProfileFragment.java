package com.example.codequestapp.ui.profile;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.codequestapp.R;
import com.example.codequestapp.models.User;
import com.example.codequestapp.utils.AppContext;
import com.example.codequestapp.utils.CapturePhoto;
import com.example.codequestapp.viewmodels.ProfileViewModel;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.TimeZone;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private MaterialDatePicker picker;

    private File image;

    private TextInputLayout birthdayContainer;
    private TextInputEditText birthdayTxt;

    private TextInputLayout usernameContainer;
    private TextInputEditText usernameTxt;

    private TextInputLayout emailContainer;
    private TextInputEditText emailTxt;

    private TextInputLayout fullNameContainer;
    private TextInputEditText fullNameTxt;

    protected View mView;

    private ImageView profileImg;
    private ImageView calendarIcon;

    private Button btn;

    Integer REQUEST_CAMERA = 22, SELECT_FILE = 0, READ_FILE = 34;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(Calendar.YEAR, 1900);
        long today = MaterialDatePicker.todayInUtcMilliseconds();
        long startYear = calendar.getTimeInMillis();

        CalendarConstraints constraints = new CalendarConstraints.Builder()
                .setStart(startYear)
                .setEnd(today)
                .build();
        picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setCalendarConstraints(constraints)
                .build();

        picker.addOnPositiveButtonClickListener(selection -> {
//                SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
            birthdayTxt.setText(picker.getHeaderText());
        });
        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.init();
        profileViewModel.getData().observe(this, user -> {
            if (user != null) {
                usernameTxt.setText(user.getUsername());
                birthdayTxt.setText(user.getBirthday());
                fullNameTxt.setText(user.getFullName());
                emailTxt.setText(user.getEmail());
                Picasso.with(getContext()).load(user.getImgPath()).into(profileImg);
            }
        });

        profileViewModel.getUpdateMessage().observe(this, responseMessage -> {
            if (responseMessage != null) {
                if (responseMessage.isSuccess())
                    Snackbar.make(mView, responseMessage.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });

        profileViewModel.getPicUpdateMessage().observe(this, responseMessage -> {
            image.delete();
            if (responseMessage != null) {
                if (responseMessage.isSuccess())
                    Snackbar.make(mView, responseMessage.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        calendarIcon = view.findViewById(R.id.calendarIconProfile);
        birthdayContainer = view.findViewById(R.id.birthdayFieldProfile);
        birthdayTxt = view.findViewById(R.id.birthdayFieldProfileText);

        emailContainer = view.findViewById(R.id.emailFieldProfile);
        emailTxt = view.findViewById(R.id.emailFieldProfileText);

        fullNameContainer = view.findViewById(R.id.nameFieldProfile);
        fullNameTxt = view.findViewById(R.id.nameFieldProfileText);

        usernameContainer = view.findViewById(R.id.usernameProfileField);
        usernameTxt = view.findViewById(R.id.usernameFieldProfileText);

        profileImg = view.findViewById(R.id.profilePic);
        mView = view;

        usernameTxt.setEnabled(false);

        btn = (Button) view.findViewById(R.id.updateProfileBtn);
        btn.setOnClickListener(v -> updateProfile());
        profileViewModel.getProfileInfo();

        calendarIcon.setOnClickListener(v -> picker.show(getActivity().getSupportFragmentManager(), "tag"));
        birthdayTxt.setOnClickListener(v -> picker.show(getActivity().getSupportFragmentManager(), "tag"));
        profileImg.setOnClickListener(v-> {
            final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
            AlertDialog builder = new AlertDialog.Builder(getContext()).create();
            builder.setTitle("Add Image");

            builder.setButton(AlertDialog.BUTTON_POSITIVE, "Camera",
                    (dialog, which) -> {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
                    });
            builder.setButton(AlertDialog.BUTTON_NEUTRAL, "Gallery",
                    (dialog, which) -> {

                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_FILE);
                    });
            builder.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", (dialog, which) -> dialog.dismiss());
            builder.show();
        });
        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == REQUEST_CAMERA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_CAMERA);
            } else {
                Toast.makeText(AppContext.getContext(), "Permission denied to access camera.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == READ_FILE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECT_FILE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
            profileImg.setImageBitmap(selectedImage);
            System.out.println("-------------- saving");
            CapturePhoto.insertImage(selectedImage, "Profile Image", "Code Quest profile image");
            image = createFileFromBitmap(selectedImage);
            profileViewModel.updateProfilePic(image);

        } else if (requestCode == SELECT_FILE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            if (selectedImage != null) {
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                if (cursor != null) {
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);

                    image = new File(picturePath);
                    Uri imageUri = Uri.fromFile(image);
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(AppContext.getContext().getContentResolver(), imageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    bitmap = getResizedBitmap(bitmap,400,400);

                    File file = createFileFromBitmap(bitmap);
                    profileImg.setImageBitmap(bitmap);
                    cursor.close();

                    profileViewModel.updateProfilePic(file);
                }
            }
        }
    }

    private void updateProfile() {
        User user = new User();
        user.setBirthday(birthdayTxt.getText().toString());
        user.setEmail(emailTxt.getText().toString());
        user.setFullName(fullNameTxt.getText().toString());

        profileViewModel.updateProfileInfo(user);
    }

    private File createFileFromBitmap(Bitmap bitmap)  {
        //create a file to write bitmap data
// Assume block needs to be inside a Try/Catch block.
        File directory = AppContext.getContext().getDir("temp", Context.MODE_PRIVATE);
        File file = new File(directory, "temp.jpg");
        OutputStream fOut = null;
        Integer counter = 0;
        try {
            fOut = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 50, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            fOut.flush(); // Not really required
            fOut.close(); // do not forget to close the stream
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return file;

    }

    private Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, true);
        bm.recycle();
        return resizedBitmap;
    }
}