package iammert.com.app

import android.content.Context
import android.widget.Toast

fun Context.alert(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()