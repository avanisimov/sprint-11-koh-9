package ru.practicum.sprint11koh9

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val uri = Uri.parse("https://myserver.com:5051/api/v1/path?text=android&take=1#last")

        Log.d(TAG, "uri.scheme ${uri.scheme}")
        Log.d(TAG, "uri.host ${uri.host}")
        Log.d(TAG, "uri.authority ${uri.authority}")
        Log.d(TAG, "uri.pathSegments ${uri.pathSegments}")
        Log.d(TAG, "uri.lastPathSegment ${uri.lastPathSegment}")
        Log.d(TAG, "uri.queryParameterNames ${uri.queryParameterNames}")
        Log.d(TAG, "uri.fragment ${uri.fragment}")
    }

    companion object {
        const val TAG = "SRPINT_11"
    }

}