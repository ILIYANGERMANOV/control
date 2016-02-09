package custom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.csipsimple.ui.messages.ConversationsListFragment;
import com.csipsimple.ui.messages.MessageActivity;

import custom.extras.values.Constants;
import custom.extras.utils.MyUtils;


public class DialogHelper {

    public static void buildInitialConversationPasswordDialog(final Context context, final String text, final Bundle bundle,
                                                              final ConversationsListFragment mFragment) {

        LinearLayout mLayout = new LinearLayout(context);
        mLayout.setOrientation(LinearLayout.VERTICAL);

        final EditText mInputEditText = new EditText(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        mInputEditText.setLayoutParams(lp);
        mInputEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mInputEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());

        CheckBox mCheckBox = new CheckBox(context);
        mCheckBox.setLayoutParams(lp);
        mCheckBox.setText(Constants.SHOW_PASSWORD_TEXT);
        mCheckBox.setPadding(0, MyUtils.dpToPixels(context, 5), 0, 0);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mInputEditText.setTransformationMethod(null);
                } else {
                    mInputEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        mLayout.addView(mInputEditText);
        int dpAsPixels = MyUtils.dpToPixels(context, 5);
        mLayout.setPadding(dpAsPixels, dpAsPixels, dpAsPixels, dpAsPixels);
        mLayout.addView(mCheckBox);

        int leftTries = Constants.MAX_PASSWORD_TRIES - MyUtils.getPasswordTries(context);

        new AlertDialog.Builder(context)
                .setTitle(Constants.PASSWORD_DIALOG_TITLE + " (" + Integer.toString(leftTries) + ")")
                .setMessage(text)
                .setView(mLayout)
                .setPositiveButton(Constants.PASSWORD_DIALOG_POSITIVE, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyUtils.incrementPasswordTries(context);
                        Intent intent = new Intent(context, MessageActivity.class);
                        bundle.putString(Constants.PASSWORD_EXTRA, mInputEditText.getText().toString());
                        intent.putExtras(bundle);
                        mFragment.startActivityForResult(intent, Constants.INIT_CONVERSATION_RES_CODE);
                    }
                })
                .setNegativeButton(Constants.PASSWORD_DIALOG_NEGATIVE, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

        mInputEditText.requestFocus();
    }

}
