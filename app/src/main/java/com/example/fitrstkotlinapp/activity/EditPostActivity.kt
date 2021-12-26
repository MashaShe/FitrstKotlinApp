package com.example.fitrstkotlinapp.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.fitrstkotlinapp.R
import com.example.fitrstkotlinapp.databinding.ActivityNewPostBinding
import com.example.fitrstkotlinapp.dto.Post
import kotlinx.android.synthetic.main.activity_edit_post.*

class EditPostActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intentWithEditedText = Intent()
        val intentWithContent = getIntent()
        if (intentWithContent.hasExtra(Intent.EXTRA_TEXT)) {
            val editableContent = intentWithContent.getStringExtra(Intent.EXTRA_TEXT)
            binding.edit.setText(editableContent)
        }
        binding.edit.requestFocus()
        binding.ok.setOnClickListener {

            if (binding.edit.text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, intentWithEditedText)
                Toast.makeText(
                    this@EditPostActivity,
                    getString(R.string.error_empty_content),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener

            } else {
                val editedContent = binding.edit.text.toString()
                intentWithEditedText.putExtra(Intent.EXTRA_TEXT, editedContent)
                setResult(Activity.RESULT_OK, intentWithEditedText)
            }
            finish()
        }
    }
}



