package com.openclassrooms.realestatemanager.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.openclassrooms.realestatemanager.Activities.AddActivity;
import com.openclassrooms.realestatemanager.R;

public class DescriptionPictureDialog extends AppCompatDialogFragment implements View.OnClickListener {

    private Button takePicture;
    private Button selectPicture;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_description_picture_dialog, null);

        builder.setView(view)
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
        takePicture = view.findViewById(R.id.bt_gallery_picture_dialog_take_picture);
        selectPicture = view.findViewById(R.id.bt_gallery_picture_dialog_select_picture);

        takePicture.setOnClickListener(this);
        selectPicture.setOnClickListener(this);

        return builder.create();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_gallery_picture_dialog_take_picture:
                ((AddActivity)getActivity()).takePicture();
                dismiss();
                break;
            case R.id.bt_gallery_picture_dialog_select_picture:
                ((AddActivity)getActivity()).addPictureFromDevice();
                dismiss();
                break;
        }
    }
}
