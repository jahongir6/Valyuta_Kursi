package com.example.valyuta_ozim

import android.content.ContentValues.TAG
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.valyuta_ozim.adapter.UserAdapter
import com.example.valyuta_ozim.databinding.ActivityMainBinding
import com.example.valyuta_ozim.databinding.ItemDialogBinding
import com.example.valyuta_ozim.models.MyObject
import com.example.valyuta_ozim.models.Valyuta
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

class MainActivity : AppCompatActivity(), UserAdapter.RvClick {
    private lateinit var binding: ActivityMainBinding
    private lateinit var requestQueue: RequestQueue

    //    private lateinit var userAdapter: UserAdapter
//    private lateinit var list: List<Valyuta>
    private var url = "http://cbu.uz/uzc/arkhiv-kursov-valyut/json/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestQueue = Volley.newRequestQueue(this)
        getArrayGson()
    }

    fun getArrayGson() {
        VolleyLog.DEBUG = true //qanday ma'lumot kelayotganini Logda ko'rsatib turadi

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            object : Response.Listener<JSONArray> {
                override fun onResponse(response: JSONArray?) {

                    val type = object : TypeToken<List<Valyuta>>() {}.type
                    val list = Gson().fromJson<List<Valyuta>>(response.toString(), type)

                    MyObject.userAdapter =
                        UserAdapter(list as ArrayList<Valyuta>, this@MainActivity)
                    binding.rv.adapter = MyObject.userAdapter

                    Log.d(TAG, "onResponse : ${response.toString()}")
                }
            }, object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {
                    Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            })

        jsonArrayRequest.tag = "tag1" //tag berilyapti
        requestQueue.add(jsonArrayRequest)

    }

    override fun onClick(valyuta: Valyuta) {
        val dialog = BottomSheetDialog(binding.root.context)
        val bottomsheetDialogBinding = ItemDialogBinding.inflate(layoutInflater)
        bottomsheetDialogBinding.valyuta.text = "Valyuta: ${valyuta.CcyNm_UZ}"
        bottomsheetDialogBinding.qiymati.text = "Qiymati: ${valyuta.Rate}"
        bottomsheetDialogBinding.farqi.text = "Farqi: ${valyuta.Diff}"
        bottomsheetDialogBinding.sana.text = "Sana: ${valyuta.Date}"
        if (valyuta.Diff.toFloat() > 0) {
            bottomsheetDialogBinding.diffImg.setImageResource(R.drawable.ic_treding)
        } else if (valyuta.Diff.toFloat() < 0) {
            bottomsheetDialogBinding.diffImg.setImageResource(R.drawable.decrease)
        } else {
            bottomsheetDialogBinding.diffImg.setImageResource(R.drawable.ic_treding)
        }

        dialog.setContentView(bottomsheetDialogBinding.root)
        dialog.show()
    }

}


