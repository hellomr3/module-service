package com.youpao.moduleservice.ui

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.youpao.moduleservice.R
import com.youpao.moduleservice.demo.IUserInfo
import com.youpao.service.ServiceManager
import com.yupao.service.ServiceInit

/**
 * @author guoqingshan
 * @date 2022/7/19/019
 * @description
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<TextView>(R.id.dd)?.let {
            it.setOnClickListener {
                ServiceInit

                ServiceManager.getServiceByInterface<IUserInfo>(IUserInfo::class.java)
                    ?.getUserName()?.let {
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}