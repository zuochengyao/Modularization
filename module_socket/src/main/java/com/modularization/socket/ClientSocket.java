package com.modularization.socket;

import android.os.Handler;

import com.modularization.common.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 左程耀
 * 处理客户端socket
 */
class ClientSocket
{
    private static final String TAG = "ClientSocket";
    public static final int MESSAGE_WHAT_CONNECT = 1;
    public static final int MESSAGE_WHAT_RECEIVE = 2;

    // 主线程Handler，用于处理服务器消息显示
    private Handler mMainHandler;
    private SocketAddress mSocketAddress;
    // 线程池，用于管理socket
    private ExecutorService mSocketThreadPool;
    // 客户端Socket
    private Socket mSocket;
    // 输入流
    private InputStream mInStream;
    private InputStreamReader mInStreamReader;
    private BufferedReader mReader;
    // 输出流
    private OutputStream mOutStream;
    private OutputStreamWriter mOutStreamWriter;
    private BufferedWriter mWriter;

    public ClientSocket(String ip, int port, Handler handler)
    {
        this.mMainHandler = handler;
        mSocketAddress = new InetSocketAddress(ip, port);
        this.mSocket = new Socket();
        mSocketThreadPool = Executors.newCachedThreadPool();
    }

    public void connect()
    {
        mSocketThreadPool.execute(() -> {
            try
            {
                mSocket.connect(mSocketAddress);
                if (isConnected())
                {
                    mInStream = mSocket.getInputStream();
                    mInStreamReader = new InputStreamReader(mInStream);
                    mReader = new BufferedReader(mInStreamReader);
                    mOutStream = mSocket.getOutputStream();
                    mOutStreamWriter = new OutputStreamWriter(mOutStream);
                    mWriter = new BufferedWriter(mOutStreamWriter);
                }
                mMainHandler.obtainMessage(MESSAGE_WHAT_CONNECT, mSocket.isConnected()).sendToTarget();
                Log.i(TAG, "Socket connected: " + mSocket.isConnected());
            }
            catch (IOException e)
            {
                mMainHandler.obtainMessage(MESSAGE_WHAT_CONNECT, mSocket.isConnected()).sendToTarget();
                Log.i(TAG, "Socket connected: " + mSocket.isConnected() + ", error: " + e.getMessage());
            }
        });
    }

    public void receive()
    {
        mSocketThreadPool.execute(() -> {
            try
            {
                String message;
                boolean flag = true;
                while (flag)
                {
                    if (isConnected())
                    {
                        message = mReader.readLine();
                        mMainHandler.obtainMessage(MESSAGE_WHAT_RECEIVE, message).sendToTarget();
                        if (message.equalsIgnoreCase("bye"))
                            flag = false;
                    }
                    else
                        flag = false;
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }

    public void send(final String message)
    {
        mSocketThreadPool.execute(() -> {
            try
            {
                mWriter.write(message + "\r\n");
                mWriter.flush();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        });
    }

    public boolean isConnected()
    {
        return mSocket.isConnected();
    }

    public void disconnect()
    {
        try
        {
            mWriter.close();
            mReader.close();
            mSocket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
