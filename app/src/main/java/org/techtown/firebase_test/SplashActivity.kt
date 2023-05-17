package org.techtown.firebase_test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({ // 실행 딜레이 처리 해주는 메소드
            val intent = Intent(this, LoginActivity::class.java)
            // 다음 화면은 MainActivity으로 이동하라는 것
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        },DURATION)
        //여기서 DURATION은 딜레이 시간을 의미, 딜레이 시간을 3000으로 지정함.
    }
    companion object {
        private const val DURATION : Long = 3000 //런타임이 3000
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}