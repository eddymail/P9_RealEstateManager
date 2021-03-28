package com.openclassrooms.realestatemanager.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.openclassrooms.realestatemanager.Activities.AddActivity;
import com.openclassrooms.realestatemanager.R;

public class GalleryPictureDialog extends AppCompatDialogFragment implements View.OnClickListener {

    private Button takePicture;
    private Button selectPicture;
    private EditText description;
    private DialogListener dialogListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_gallery_picture_dialog, null);

        builder.setView(view);
        builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String pictureDescription = description.getText().toString();
                dialogListener.applyDescription(pictureDescription);
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        takePicture = view.findViewById(R.id.bt_description_dialog_take_picture);
        selectPicture = view.findViewById(R.id.bt_description_dialog_select_picture);
        description = view.findViewById(R.id.et_gallerydialog_select_picturedialog_select_picture);

        takePicture.setOnClickListener(this);
        selectPicture.setOnClickListener(this);

        return builder.create();
    }

    public interface DialogListener {
        void applyDescription(String pictureDescription);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            dialogListener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DialogListener");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_description_dialog_take_picture:
                //TODO gallery picture
                ((AddActivity) getActivity()).takePicture();
                
                break;
            case R.id.bt_description_dialog_select_picture:
                //TODO gallery picture
                ((AddActivity) getActivity()).addPictureFromDevice();

                break;
        }
    }
}
