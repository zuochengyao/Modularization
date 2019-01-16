package com.modularization.socket;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.modularization.common.base.BaseActivity;
import com.modularization.common.util.Log;

import java.lang.ref.WeakReference;

public class SocketClientActivity extends BaseActivity implements View.OnClickListener
{
    private Button mSocketConnectBtn;
    private View mSocketMessageView;
    private Button mSocketMessageSendBtn;
    private EditText mSocketMessageText;

    private ClientSocket mClientSocket;
    private SocketHandler mSocketHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_client);
        doInitView();
        mSocketHandler = new SocketHandler(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.socket_message_send:
            {
                final String message = mSocketMessageText.getText().toString().trim();
                if (!TextUtils.isEmpty(message))
                    mClientSocket.send(message);
                mSocketMessageText.setText(null);
                break;
            }
            case R.id.socket_connect_btn:
            {
                mClientSocket = new ClientSocket("10.155.2.171", 9898, mSocketHandler);
                mClientSocket.connect();
                break;
            }
        }
    }

    private void doInitView()
    {
        mSocketConnectBtn = $(R.id.socket_connect_btn);
        mSocketConnectBtn.setOnClickListener(this);
        mSocketMessageView = $(R.id.socket_message_panel);
        mSocketMessageText = $(R.id.socket_message_text);
        mSocketMessageSendBtn = $(R.id.socket_message_send);
        mSocketMessageSendBtn.setOnClickListener(this);
    }

    private static class SocketHandler extends Handler
    {
        private WeakReference<SocketClientActivity> mActivity;

        SocketHandler(SocketClientActivity activity)
        {
            this.mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg)
        {
            SocketClientActivity activity = mActivity.get();
            if (activity != null)
            {
                switch (msg.what)
                {
                    case ClientSocket.MESSAGE_WHAT_CONNECT:
                    {
                        if ((boolean) msg.obj)
                        {
                            activity.mSocketConnectBtn.setVisibility(View.GONE);
                            activity.mSocketMessageView.setVisibility(View.VISIBLE);
                            activity.mClientSocket.receive();
                        }
                        break;
                    }
                    case ClientSocket.MESSAGE_WHAT_RECEIVE:
                    {
                        Log.i(activity.TAG, "Receive: " + msg.obj.toString());
                        break;
                    }
                }
            }
        }
    }
}
