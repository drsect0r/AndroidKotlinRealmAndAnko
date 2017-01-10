package com.donnfelker.kotlinmix

import android.app.Application

import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {
  override fun onCreate() {
    super.onCreate()

    //https://www.grc.com/passwords.htm
    val password = "02E3188B747777EEE95A0C099155AA0FBB849FE90007202DF59FB717A92CD420"

    val passwordByteArray = password.toByteArray()

    // Initialize Realm. Should only be done once when the application starts.
    Realm.init(this)
    val config = RealmConfiguration
      .Builder()
      .name("support")
      .encryptionKey(passwordByteArray)
      .build()
    Realm.setDefaultConfiguration(config)
    Realm.deleteRealm(config)
  }
}
