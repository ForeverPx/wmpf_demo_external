package com.tencent.wmpf.demo.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import com.tencent.luggage.demo.wxapi.Constants
import com.tencent.luggage.demo.wxapi.Constants.APP_ID
import com.tencent.wmpf.demo.Api
import com.tencent.wmpf.demo.R
import com.tencent.wmpf.demo.RequestsRepo
import com.tencent.wmpf.demo.utils.InvokeTokenHelper
import com.tencent.wxapi.test.OpenSdkTestUtil
import io.reactivex.schedulers.Schedulers

class DocumentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_document)

        findViewById<Button>(R.id.btn_activate_device).setOnClickListener {
            Api.activateDevice(Constants.PRODUCT_ID, Constants.KEY_VERSION,
                    Constants.DEVICE_ID, Constants.SIGNATURE, Constants.APP_ID)
                    .subscribe({
                        Log.i(TAG, "success: ${it.baseResponse.ret} ${it.baseResponse.errMsg} ")
                        Log.i(TAG, "success: ${it.invokeToken} ")
                        InvokeTokenHelper.initInvokeToken(this, it.invokeToken)
                    }, {
                        Log.e(TAG, "error: $it")
                    })
        }

        findViewById<Button>(R.id.btn_preload_time).setOnClickListener {
            Api.preloadRuntime()
                    .subscribe({
                        Log.i(TAG, "success: ${it.baseResponse.ret} ${it.baseResponse.errMsg}")
                    }, {
                        Log.e(TAG, "error: $it")
                    })
        }

        findViewById<Button>(R.id.btn_authorize).setOnClickListener {
            OpenSdkTestUtil.getSDKTicket(Constants.APP_ID, Constants.APP_SECRET)
                    .subscribeOn(Schedulers.io())
                    .flatMap {
                        Api.authorize(Constants.APP_ID, it, "snsapi_userinfo,snsapi_runtime_apk")
                    }
                    .subscribe({
                        Log.i(TAG, "success: ${it.baseResponse.ret} ${it.baseResponse.errMsg}")
                    }, {
                        Log.e(TAG, "error: $it")
                    })
        }

        findViewById<Button>(R.id.btn_authorize_no_login).setOnClickListener {
            OpenSdkTestUtil.getSDKTicket(Constants.APP_ID, Constants.APP_SECRET)
                    .subscribeOn(Schedulers.io())
                    .flatMap {
                        Api.authorizeNoLogin(Constants.APP_ID, it, "snsapi_userinfo,snsapi_runtime_apk")
                    }
                    .subscribe({
                        Log.i(TAG, "success: ${it.baseResponse.ret} ${it.baseResponse.errMsg}")
                    }, {
                        Log.e(TAG, "error: $it")
                    })
        }

        findViewById<Button>(R.id.btn_launch_wxa_app).setOnClickListener {
            Api.launchWxaApp("wxe5f52902cf4de896", "")
                    .subscribe({
                        Log.i(TAG, "success: ${it.baseResponse.ret} ${it.baseResponse.errMsg}")
                    }, {
                        Log.e(TAG, "error: $it")
                    })
        }

        findViewById<Button>(R.id.btn_launch_wxa_app_by_scan).setOnClickListener {
            Api.launchWxaAppByScan("xxx")
                    .subscribe({
                        Log.i(TAG, "success: ${it.baseResponse.ret} ${it.baseResponse.errMsg}")
                    }, {
                        Log.e(TAG, "error: $it")
                    })
        }

        findViewById<Button>(R.id.btn_close_wxa_app).setOnClickListener {
            Api.closeWxaApp("wxe5f52902cf4de896")
                    .subscribe({
                        Log.i(TAG, "success: ${it.baseResponse.ret} ${it.baseResponse.errMsg}")
                    }, {
                        Log.e(TAG, "error: $it")
                    })
        }

        findViewById<Button>(R.id.btn_manage_music).setOnClickListener {
            Api.manageBackgroundMusic()
                    .subscribe({
                        Log.i(TAG, "success: ${it.baseResponse.ret} ${it.baseResponse.errMsg}")
                    }, {
                        Log.e(TAG, "error: $it")
                    })
        }

        findViewById<Button>(R.id.btn_notify_manage_music).setOnClickListener {
            Api.notifyBackgroundMusic()
                    .subscribe({
                        /**
                         * {@see com.tencent.wmpf.cli.task.IPCInvokerTask_NotifyBackgroundMusic}
                         * val START = 1
                         * val RESUME = 2
                         * val PAUSE = 3
                         * val STOP = 4
                         * val COMPLETE = 5
                         * val ERROR = 6
                         **/
                        Log.i(TAG, "success: ${it.baseResponse.ret} ${it.baseResponse.errMsg} state:${it.state}")
                    }, {
                        Log.e(TAG, "error: $it")
                    })
        }

        findViewById<Button>(R.id.btn_de_authorize).setOnClickListener {
            Api.deauthorize()
                    .subscribe({
                        Log.i(TAG, "success: ${it.baseResponse.ret} ${it.baseResponse.errMsg}")
                    }, {
                        Log.e(TAG, "error: $it")
                    })
        }

        findViewById<Button>(R.id.btn_push_msg_quick_start).setOnClickListener { _ ->
            val intent = Intent(this, PushMsgQuickStartActivity::class.java)
            startActivity(intent)

            Api.listeningPushMsg().subscribe({
                Log.i(TAG, "success: ${it.baseResponse.ret} ${it.baseResponse.errMsg}")
                Log.d(TAG, "push msg body: ${it.msgBody}")
            }, {
                Log.e(TAG, "error: $it")
            })
        }

        findViewById<Button>(R.id.btn_get_device_info_activity).setOnClickListener {
            Intent(this, GetDeviceInfoActivity::class.java).also { intent ->
                startActivity(intent)
            }
        }
    }

    companion object {
        const val TAG = "DocumentActivity"
    }

}