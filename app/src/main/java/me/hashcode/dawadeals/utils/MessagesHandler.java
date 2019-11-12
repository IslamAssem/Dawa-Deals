package me.hashcode.dawadeals.utils;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import me.hashcode.dawadeals.R;
import me.hashcode.dawadeals.ui.base.BaseActivity;
import me.hashcode.dawadeals.ui.base.BaseDialogFragment;

public class MessagesHandler {

    BaseActivity activity;
    public static final String HIDEDIALOG = "HIDEDIALOG";
    public MessageDialog messageDialog;
    Toast toast;

    public MessagesHandler(BaseActivity activity) {
        this.activity = activity;
    }


    /////////////////////////////////////////////////
    //message methods
    /////////////////////////////////////////////////

    public void showToast(@StringRes int res) {
        String message = Utils.getStringRes(res);
        activity.runOnUiThread(() -> {
            try {
                if (toast != null) {
                    toast.cancel();
                    toast = null;
                }
            } catch (Exception ignored) {
            }
            int length = Toast.LENGTH_SHORT;
            if (message.length() > 40)
                length = Toast.LENGTH_LONG;
            toast = Toast.makeText(activity, message, length);
            //getToast();
            toast.show();
        });
    }

    public void showToast(@StringRes int res,int length) {
        String message = Utils.getStringRes(res);
        activity.runOnUiThread(() -> {
            try {
                if (toast != null) {
                    toast.cancel();
                    toast = null;
                }
            } catch (Exception ignored) {
            }
            toast = Toast.makeText(activity, message, length);
            //getToast();
            toast.show();
        });
    }

    public void showToast(String message) {
        if(TextUtils.isEmpty(message))
            return;
        activity.runOnUiThread(() -> {
            try {
                if (toast != null) {
                    toast.cancel();
                    toast = null;
                }
            } catch (Exception ignored) {
            }
            int length = Toast.LENGTH_SHORT;
            if (message.length() > 40)
                length = Toast.LENGTH_LONG;
            toast = Toast.makeText(activity, message, length);
            //getToast();
            toast.show();
        });
    }
    public void showToast(String message,int length) {
        activity.runOnUiThread(() -> {
            try {
                if (toast != null) {
                    toast.cancel();
                    toast = null;
                }
            } catch (Exception ignored) {
            }
            toast = Toast.makeText(activity, message, length);
            //getToast();
            toast.show();
        });
    }
    /////////////////////////////////////////////////
    //message methods
    /////////////////////////////////////////////////

    public void showProgressBar(String message) {
        hideDialog();
        try {
            if (!TextUtils.isEmpty(message)&&!HIDEDIALOG.equalsIgnoreCase(message)) {
                messageDialog = MessageDialog.createProgressDialog(message);
                messageDialog.show(activity.getSupportFragmentManager(), MessageDialog.class.getName());
            }
        } catch (Exception ignored) {
        }

    }

    public void showProgressBar(@StringRes int message) {
        hideDialog();
        try {
            {
                messageDialog = MessageDialog.createProgressDialog(message);
                messageDialog.show(activity.getSupportFragmentManager(), MessageDialog.class.getName());
            }
        } catch (Exception ignored) {
        }

    }

    public void showProgressBar(@StringRes int message, boolean canCancel, MessageDialog.OnProgressChanged onProgressChanged) {
        hideDialog();
        try {
            {
                messageDialog = MessageDialog.createProgressDialog(message,canCancel);
                messageDialog.setCanCancel(canCancel);
                messageDialog.setOnProgressChanged(onProgressChanged);
                messageDialog.show(activity.getSupportFragmentManager(), MessageDialog.class.getName());
            }
        } catch (Exception ignored) {
        }

    }
    public void showProgressBar(@StringRes int message, boolean canCancel) {
        hideDialog();
        try {
            {
                messageDialog = MessageDialog.createProgressDialog(message,canCancel);
                messageDialog.setCanCancel(canCancel);
                messageDialog.show(activity.getSupportFragmentManager(), MessageDialog.class.getName());
            }
        } catch (Exception ignored) {
        }

    }

    public void hideDialog() {
        activity.runOnUiThread(() -> {
            try {
                if (messageDialog != null && !messageDialog.isHidden())
                    messageDialog.dismiss();
                messageDialog = null;
            } catch (Exception ignored) {
            }
            try {
                if (messageDialog != null && !messageDialog.isHidden())
                    messageDialog.dismiss();
                messageDialog = null;
            }catch (Exception ignored) {
                messageDialog.dismissAllowingStateLoss();
            }});

    }

    public void showMessageDialog(final String message) {
        if (TextUtils.isEmpty(message)) return;
        activity.runOnUiThread(() -> {

            hideDialog();
            messageDialog = MessageDialog.createMessageDialog(message, v -> {
                try {
                    hideDialog();
                } catch (Exception ignored) {

                }
            });
            showDialog();
        });
    }

    public void showMessageDialog(@StringRes final int message) {
        activity.runOnUiThread(() -> {

            hideDialog();
            messageDialog = MessageDialog.createMessageDialog(message, v -> {
                try {
                    hideDialog();
                } catch (Exception ignored) {

                }
            });
            showDialog();
        });
    }

    public void showMessageDialog(final String message, final View.OnClickListener onOkclick) {
        activity.runOnUiThread(() -> {

            hideDialog();
            messageDialog = MessageDialog.createMessageDialog(message, v -> {
                try {
                    hideDialog();
                    onOkclick.onClick(v);
                } catch (Exception ignored) {

                }
            });
            messageDialog.setCanCancel(false);
            showDialog();
        });
    }


    public void showMessageDialog(final String title, final String message) {
        activity.runOnUiThread(() -> {

            hideDialog();
            messageDialog = MessageDialog.createMessageDialog(title, message, v -> {
                try {
                    hideDialog();
                } catch (Exception ignored) {

                }
            });
            showDialog();
            messageDialog.setCanCancel(true);
        });
    }
    public void showMessageDialog(final String title, final String message, final View.OnClickListener onOkclick) {
        activity.runOnUiThread(() -> {

            hideDialog();
            messageDialog = MessageDialog.createMessageDialog(title, message, v -> {
                try {
                    hideDialog();
                    onOkclick.onClick(v);
                } catch (Exception ignored) {

                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideDialog();
                }
            });
            showDialog();
            messageDialog.setCanCancel(true);
        });
    }

    public void showMessageDialog(final String title, final String message, final View.OnClickListener onOkclick, final View.OnClickListener onCancelclick) {
        activity.runOnUiThread(() -> {

            hideDialog();
            messageDialog = MessageDialog.createMessageDialog(title, message, v -> {
                try {
                    hideDialog();
                    onOkclick.onClick(v);
                } catch (Exception ignored) {

                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideDialog();
                    onCancelclick.onClick(v);
                }
            });
            messageDialog.setCanCancel(true);
            showDialog();
        });
    }

    public void showMessageDialog(final String title, final String message, final View.OnClickListener onOkclick, final View.OnClickListener onCancelclick,boolean canCancel) {
        activity.runOnUiThread(() -> {

            hideDialog();
            messageDialog = MessageDialog.createMessageDialog(title, message, v -> {
                try {
                    hideDialog();
                    onOkclick.onClick(v);
                } catch (Exception ignored) {

                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideDialog();
                    onCancelclick.onClick(v);
                }
            });
            messageDialog.setCanCancel(canCancel);
            showDialog();
        });
    }


    public void showMessageDialog(@StringRes final int title,@StringRes final int message, final View.OnClickListener onOkclick, final View.OnClickListener onCancelclick) {
        activity.runOnUiThread(() -> {

            hideDialog();
            messageDialog = MessageDialog.createMessageDialog(Utils.getStringRes(title), Utils.getStringRes(message), v -> {
                try {
                    hideDialog();
                    onOkclick.onClick(v);
                } catch (Exception ignored) {

                }
            }, v -> {
                hideDialog();
                onCancelclick.onClick(v);
            });
            messageDialog.setCanCancel(true);
            showDialog();
        });
    }

    public void showMessageDialog(@StringRes final int ok,@StringRes final int cancel,@StringRes final int title,@StringRes final int message, final View.OnClickListener onOkclick, final View.OnClickListener onCancelclick,boolean canCancel) {
        activity.runOnUiThread(() -> {

            hideDialog();
            messageDialog = MessageDialog.createMessageDialog(Utils.getStringRes(title), Utils.getStringRes(message), v -> {
                try {
                    hideDialog();
                    onOkclick.onClick(v);
                } catch (Exception ignored) {

                }
            }, v -> {
                hideDialog();
                onCancelclick.onClick(v);
            });
            messageDialog.setOkText(ok);
            messageDialog.setCancelText(cancel);
            messageDialog.setCanCancel(canCancel);
            showDialog();
        });
    }
    public void showMessageDialog(@StringRes final int title,@StringRes final int message, final View.OnClickListener onOkclick, final View.OnClickListener onCancelclick,boolean canCancel,boolean autoHide) {
        activity.runOnUiThread(() -> {

            hideDialog();
            messageDialog = MessageDialog.createMessageDialog(Utils.getStringRes(title), Utils.getStringRes(message), v -> {
                try {
                    if (autoHide)
                    hideDialog();
                    onOkclick.onClick(v);
                } catch (Exception ignored) {

                }
            }, v -> {
                if (autoHide)
                hideDialog();
                if(onCancelclick!=null)
                onCancelclick.onClick(v);
            });
            if (onCancelclick == null)
                messageDialog.setOnCancelListener(null);
            messageDialog.setCanCancel(canCancel);
            showDialog();
        });
    }

    public void showMessageDialog(@StringRes final int title,
                                  @StringRes final int message,
                                  final View.OnClickListener onOkclick,
                                  final View.OnClickListener onCancelclick,
                                  boolean canCancel ) {
        activity.runOnUiThread(() -> {

            hideDialog();
            messageDialog = MessageDialog.createMessageDialog(Utils.getStringRes(title), Utils.getStringRes(message), v -> {
                try {
                    hideDialog();
                    onOkclick.onClick(v);
                } catch (Exception ignored) {

                }
            }, v -> {
                hideDialog();
                onCancelclick.onClick(v);
            });
            messageDialog.setCanCancel(canCancel);
            showDialog();
        });
    }

    void showDialog() {
        try {
            messageDialog.show(activity.getSupportFragmentManager(), "tag");
        } catch (Exception ignored) {
        }
    }

    public static class MessageDialog extends BaseDialogFragment {
        public abstract static class OnProgressChanged{
            TextView progress;
            public void onProgressChanged(String progresss){
                if(progress!=null)
                    progress.setText(progresss);
            }

            public void setProgress(TextView progress) {
                this.progress = progress;
            }
        }
        private View view;
        private CardView card;
        private View progressBar;
        private ScrollView scrollView;
        private TextView title;
        private TextView message;
        private TextView ok;
        private TextView cancel;
        private TextView progress;
        private String titleText,okText,cancelText;
        private String messageText;
        private View.OnClickListener onOkListener;
        private View.OnClickListener onCancelListener;
        private View divider;
        private boolean isProgress;
        private boolean exitOnDismiss=false;
        public OnProgressChanged onProgressChanged;

        public void setOnProgressChanged(OnProgressChanged onProgressChanged) {
            this.onProgressChanged = onProgressChanged;
        }

        public void setCanCancel(boolean cancelable) {
            this.cancelable = cancelable;
        }

        private boolean cancelable;
        public MessageDialog() {
            titleText = null;
            messageText = null;
            onCancelListener = null;
            onOkListener = null;
            isProgress = false;
            cancelable=true;
        }

        public static MessageDialog createMessageDialog(String titleText, String mesageText, View.OnClickListener onOkListener, View.OnClickListener onCancelListener) {
            MessageDialog dialog = new MessageDialog();
            dialog.titleText = titleText;
            dialog.messageText = mesageText;
            dialog.onOkListener = onOkListener;
            dialog.onCancelListener = onCancelListener;
            return dialog;
        }

        public static MessageDialog createMessageDialog(String mesageText, View.OnClickListener onOkListener, View.OnClickListener onCancelListener) {
            MessageDialog dialog = new MessageDialog();
            dialog.messageText = mesageText;
            dialog.onOkListener = onOkListener;
            dialog.onCancelListener = onCancelListener;
            return dialog;
        }

        public static MessageDialog createMessageDialog(String titleText, String messageText, View.OnClickListener onOkListener) {
            MessageDialog dialog = new MessageDialog();
            dialog.titleText = titleText;
            dialog.messageText = messageText;
            dialog.onOkListener = onOkListener;
            return dialog;
        }

        public static MessageDialog createMessageDialog(String messageText, View.OnClickListener onOkListener) {
            MessageDialog dialog = new MessageDialog();
            dialog.messageText = messageText;
            dialog.onOkListener = onOkListener;
            return dialog;
        }
        public static MessageDialog createMessageDialog(@StringRes int messageRes, View.OnClickListener onOkListener) {
            MessageDialog dialog = new MessageDialog();
            dialog.messageText = Utils.getStringRes(messageRes);
            dialog.onOkListener = onOkListener;
            return dialog;
        }

        //not working well, todo : will be fixed
        public static MessageDialog createProgressDialog() {
            MessageDialog dialog = new MessageDialog();
            dialog.isProgress = true;

            return dialog;
        }

        public static MessageDialog createProgressDialog(String progressMessage) {
            MessageDialog dialog = new MessageDialog();
            dialog.isProgress = true;
            dialog.messageText = progressMessage;
            dialog.cancelable=false;
            return dialog;
        }

        public static MessageDialog createProgressDialog(@StringRes int progressMessage) {
            MessageDialog dialog = new MessageDialog();
            dialog.isProgress = true;
            dialog.messageText = Utils.getStringRes(progressMessage);
            dialog.cancelable=false;
            return dialog;
        }
        public static MessageDialog createProgressDialog(@StringRes int progressMessage,boolean cancelable) {
            MessageDialog dialog = new MessageDialog();
            dialog.isProgress = true;
            dialog.messageText = Utils.getStringRes(progressMessage);
            dialog.cancelable=cancelable;
            return dialog;
        }
        public MessageDialog setExitOnDismiss(boolean exitOnDismiss){
            this.exitOnDismiss=exitOnDismiss;
            return this;
        }
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setStyle(DialogFragment.STYLE_NORMAL, R.style.customdialog);
        }

        @Override
        public void onStart() {
            super.onStart();
            if (getDialog().getWindow() != null){
                getDialog().getWindow()
                        .setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                                WindowManager.LayoutParams.WRAP_CONTENT);
                getDialog().getWindow().setDimAmount(204/255f);
            }
        }

        public void setOkText(String okText) {
            this.okText = okText;
        }

        public void setCancelText(String cancelText) {
            this.cancelText = cancelText;
        }

        public void setOkText(@StringRes int okText) {
            this.okText =Utils.getStringRes( okText);
        }

        public void setCancelText(@StringRes int cancelText) {
            this.cancelText = Utils.getStringRes( cancelText);
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.dialog, container, false);
            try {
                if (getDialog().getWindow() != null) {
                    getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                }
            } catch (Exception ignored) {
            }
            initViews();
            return view;
        }

        private boolean shouldDimiss(){
            if(!TextUtils.isEmpty(messageText))
                return false;
            if(!TextUtils.isEmpty(titleText))
                return false;
            if(onOkListener!=null)
                return false;
            return !isProgress;
        }
        private void initViews() {
            if(shouldDimiss())
                dismiss();
            title = view.findViewById(R.id.title);
            message = view.findViewById(R.id.message);
            ok = view.findViewById(R.id.ok);
            cancel = view.findViewById(R.id.cancel);
            divider = view.findViewById(R.id.divider);
            card = view.findViewById(R.id.dialog_card);
            progressBar = view.findViewById(R.id.progressBar);
            scrollView = view.findViewById(R.id.message_container);
            progress = view.findViewById(R.id.progress);
            if(!TextUtils.isEmpty(okText))
                ok.setText(okText);
            if(!TextUtils.isEmpty(cancelText))
                cancel.setText(cancelText);
            if (isProgress) {

                progressBar.setVisibility(View.VISIBLE);
                if (messageText == null) {
                    scrollView.setVisibility(View.GONE);
//                    card.setCardBackgroundColor(Utils.getColorRes(R.color.transparent));
                } else {
                    scrollView.setVisibility(View.VISIBLE);
                    message.setText(messageText);
//                    card.setCardBackgroundColor(Utils.getColorRes(R.color.dialog_background));
                }

                if(TextUtils.isEmpty(messageText)||TextUtils.isEmpty(titleText))
                    divider.setVisibility(View.GONE);
                if(onProgressChanged!=null){
                    progress.setVisibility(View.VISIBLE);
                    onProgressChanged.setProgress(progress);
                }
                else progress.setVisibility(View.GONE);
                setCancelable(cancelable);
                return;
            }
            progress.setVisibility(View.GONE);
            if (onOkListener != null) {
                ok.setVisibility(View.VISIBLE);
                ok.setOnClickListener(onOkListener);
            }
            if (onCancelListener != null) {
                cancel.setVisibility(View.VISIBLE);
                cancel.setOnClickListener(onCancelListener);
            }
            if (!TextUtils.isEmpty(titleText)) {
                title.setVisibility(View.VISIBLE);
                title.setText(titleText);
            }
            if (!TextUtils.isEmpty(messageText)) {
                if(messageText.toLowerCase().contains("unable to resolve host".toLowerCase()))
                    messageText=Utils.getStringRes(getContext(),R.string.connect_to_network);
                scrollView.setVisibility(View.VISIBLE);
                message.setText(messageText);
            }

            if(TextUtils.isEmpty(messageText)||TextUtils.isEmpty(titleText))
                divider.setVisibility(View.GONE);
            else divider.setVisibility(View.VISIBLE);
            setCancelable(cancelable);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();

        }

        @Override
        public void dismiss() {
            try {
                if (isProgress)
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                MessageDialog.super.dismiss();
                            } catch (Exception ignored) {
                            }
                        }}, 200);
                else super.dismiss();
            } catch (Exception ignored) {
                super.dismiss();
            }
        }

        public void setOnOkListener(View.OnClickListener onOkListener) {
            this.onOkListener = onOkListener;
        }

        public void setOnCancelListener(View.OnClickListener onCancelListener) {
            this.onCancelListener = onCancelListener;
        }

        public void setTitle(String title) {
            titleText = title;
        }

        public void setMessage(String message) {
            messageText = message;
        }
    }
}
