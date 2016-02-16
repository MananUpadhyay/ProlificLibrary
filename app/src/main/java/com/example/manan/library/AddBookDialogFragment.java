package com.example.manan.library;


import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;

public class AddBookDialogFragment extends DialogFragment {
    AddBookDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Book");
        builder.setCancelable(true);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.layout_add_book, null));

        builder.setPositiveButton(R.string.bookAdd, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int id) {
                mListener.onAddBookDialogPositiveClick(AddBookDialogFragment.this);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AddBookDialogFragment.this.getDialog().cancel();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (AddBookDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement AddBookDialogListener");
        }

    }

    public boolean validateForm(AddBookDialogFragment ad) {
        Dialog addBookDialog = ad.getDialog();
        TextInputLayout atil = (TextInputLayout) addBookDialog.findViewById(R.id.authorWrapper);
        EditText author = (EditText) addBookDialog.findViewById(R.id.author);
        String ta = author.getText().toString();

        TextInputLayout ttil = (TextInputLayout) addBookDialog.findViewById(R.id.titleWrapper);
        EditText title = (EditText) addBookDialog.findViewById(R.id.title);
        String tt = title.getText().toString();

        TextInputLayout ptil = (TextInputLayout) addBookDialog.findViewById(R.id.pubWrapper);
        EditText pub = (EditText) addBookDialog.findViewById(R.id.pub);
        String tp = pub.getText().toString();

        TextInputLayout ctil = (TextInputLayout) addBookDialog.findViewById(R.id.cateWrapper);
        EditText cat = (EditText) addBookDialog.findViewById(R.id.cat);
        String tc = cat.getText().toString();

        if (tc == null || tc.isEmpty()) {
//            cat.setError("Input Empty");
            ctil.setError("Input Empty");
            return false;
        }
        if (tp == null || tp.isEmpty()) {
//            pub.setError("Input Empty");
            ptil.setError("Input Empty");
            return false;
        }
        if (tt == null || tt.isEmpty()) {
//            title.setError("Input Empty");
            ttil.setError("Input Empty");
            return false;
        }
        if (ta == null || ta.isEmpty()) {
//            author.setError("Input Empty");
            atil.setError("Input Empty");
            return false;
        }
        return true;
    }

    public interface AddBookDialogListener {
        void onAddBookDialogPositiveClick(DialogFragment dialog);
    }

}
