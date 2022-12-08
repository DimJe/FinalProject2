package org.techtown.finalproject2.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.techtown.finalproject2.API.Api
import org.techtown.finalproject2.Notification.NotificationBody
import org.techtown.finalproject2.Notification.NotificationRetrofit
import org.techtown.finalproject2.R
import org.techtown.finalproject2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val api : Api by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val keyHash = Utility.getKeyHash(this)
        Log.e("Key", "keyHash: ${keyHash}")

        /** KakoSDK init */
        KakaoSdk.init(this, this.getString(R.string.kakaoAppKey))

        initView()
    }

    private fun initView(){
        binding.loginButton.setOnClickListener {
            // 카카오계정으로 로그인 공통 callback 구성
            // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Toast.makeText(this, "카카오계정으로 로그인 실패 : ${error}", Toast.LENGTH_SHORT).show()
                    //setLogin(false)
                } else if (token != null) {
                    UserApiClient.instance.me { user, _ ->

                        CoroutineScope(Dispatchers.IO).launch {
                            val deferred : Deferred<Boolean> = async {
                                api.getUser(user?.kakaoAccount?.email!!)
                            }
                            val temp = deferred.await()
                            if(temp){
                                Log.d("태그", "checkUser: true")
                                Intent(this@MainActivity, MainSystem::class.java).run {
                                    startActivity(this)
                                }
                            }else{
                                Intent(this@MainActivity, RegisterUser::class.java).run{
                                    startActivity(this)
                                }
                            }
                        }

                    }
                }
            }

            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Toast.makeText(this, "카카오톡으로 로그인 실패 : ${error}",Toast.LENGTH_SHORT).show()

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                    } else if (token != null) {
                        UserApiClient.instance.me { user, _ ->

                            CoroutineScope(Dispatchers.IO).launch {
//                                val deferred : Deferred<Boolean> = async {
//                                    api.getUser(user?.kakaoAccount?.email!!)
//                                }
//                                val temp = deferred.await()
                                if(api.getUser(user?.kakaoAccount?.email!!)){
                                    Log.d("태그", "checkUser: true")

                                    Intent(this@MainActivity, MainSystem::class.java).run {
                                        startActivity(this)
                                    }
                                }else{
                                    Intent(this@MainActivity, RegisterUser::class.java).run{
                                        startActivity(this)
                                    }
                                }
                            }

                        }
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }
}