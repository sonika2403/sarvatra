package com.android.sarvatra.ui.journal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.android.sarvatra.R
import com.android.sarvatra.database.journal.Journal
import com.android.sarvatra.database.journal.JournalDao
import com.android.sarvatra.utils.PreferencesHelper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateJournalBottomSheet(
    private var prefsHelper: PreferencesHelper,
    private val dao: JournalDao, // Pass DAO instance to the bottom sheet
    private val onSaveComplete: () -> Unit // Callback after saving
) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.layout_bottom_sheet_create_journal, container, false)

        val etTitle: EditText = view.findViewById(R.id.et_title)
        val etBody: EditText = view.findViewById(R.id.et_body)
        val btnSave: Button = view.findViewById(R.id.btn_save_journal)

       val user =prefsHelper.getUserFromPreferences()
        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val body = etBody.text.toString().trim()

            if (title.isNotEmpty() && body.isNotEmpty()) {
                val newJournal = Journal(
                    title = title,
                    body = body,
                    username = user?.username ?:"" , // Replace with actual user data
                    email = user?.email ?: "" // Replace with actual user data
                )

                CoroutineScope(Dispatchers.IO).launch {
                    dao.insertJournal(newJournal)

                    CoroutineScope(Dispatchers.Main).launch {
                        onSaveComplete()
                        dismiss()
                    }
                }
            } else {
                etTitle.error = "Title is required"
                etBody.error = "Body is required"
            }
        }

        return view
    }
}
