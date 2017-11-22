package id.technomotion.ui.privatechatcreation;

import android.annotation.SuppressLint;
        import android.app.DialogFragment;
        import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.EditText;

        import id.technomotion.R;

/**
 * Created by omayib on 05/11/17.
 */

@SuppressLint("ValidFragment")
public class ChatWithStrangerDialogFragment  extends DialogFragment {
    private EditText editText;
    private final id.technomotion.ui.privatechatcreation.ChatWithStrangerDialogFragment.onStrangerNameInputtedListener listener;

    @SuppressLint("ValidFragment")
    public ChatWithStrangerDialogFragment(id.technomotion.ui.privatechatcreation.ChatWithStrangerDialogFragment.onStrangerNameInputtedListener listener) {
        this.listener = listener;
    }


    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_stranger_name, container,
                false);
        getDialog().setTitle("Stranger name");
        this.editText = (EditText) rootView.findViewById(R.id.editText);
        Button buttonOk = (Button) rootView.findViewById(R.id.confirm_button);
        Button buttonCancel = (Button) rootView.findViewById(R.id.cancel_button);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText.getText().toString().isEmpty()){
                    listener.onStrangerNameInputted(editText.getText().toString());
                    dismiss();
                }
            }
        });
        return rootView;
    }

    public interface  onStrangerNameInputtedListener{
        void onStrangerNameInputted(String groupName);
    }

}
