package com.android.sarvatra.activities

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.sarvatra.R
import com.android.sarvatra.database.AppDatabase
import com.android.sarvatra.database.journal.Journal
import com.android.sarvatra.databinding.ActivityJournalBinding
import com.android.sarvatra.ui.journal.CreateJournalBottomSheet
import com.android.sarvatra.adapter.JournalAdapter
import com.android.sarvatra.utils.PreferencesHelper
import com.android.sarvatra.utils.isNightTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JournalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJournalBinding
    private lateinit var prefsHelper: PreferencesHelper
    private lateinit var database: AppDatabase
    private lateinit var journalAdapter: JournalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize database and preferences
        database = AppDatabase.getDatabase(applicationContext)
        prefsHelper = PreferencesHelper(this)

        // Setup UI and RecyclerView
        setupUI()
        setupRecyclerView()

        // Button to create a new journal entry
        binding.writeStoryButton.setOnClickListener {
            val createJournalBottomSheet = CreateJournalBottomSheet(
                prefsHelper,
                dao = database.journalDao(),
                onSaveComplete = {
                    loadJournals() // Reload journals after creating one
                }
            )
            createJournalBottomSheet.show(supportFragmentManager, "CreateJournalBottomSheet")
        }
    }

    private fun setupUI() {
        val user = prefsHelper.getUserFromPreferences()
        if (isNightTime()) {
            binding.root.setBackgroundResource(R.drawable.night_background)
            binding.sunAnimation.visibility = View.INVISIBLE
            binding.nightAnimation.visibility = View.VISIBLE
        } else {
            binding.root.setBackgroundResource(R.drawable.app_background)
            binding.sunAnimation.visibility = View.VISIBLE
            binding.nightAnimation.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        // Initialize the adapter with an empty list
        journalAdapter = JournalAdapter(emptyList()) { journal ->
            // Handle journal item click (optional)
            showJournalDetails(journal)
        }

        // Set up the RecyclerView
        binding.journalRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.journalRecyclerView.adapter = journalAdapter

        // Load the journals from the database
        loadJournals()
    }

    private fun loadJournals() {
        CoroutineScope(Dispatchers.IO).launch {
            val journalList = database.journalDao().getAllJournals() // Fetch journals from DAO
            CoroutineScope(Dispatchers.Main).launch {
                journalAdapter.submitList(journalList) // Update the adapter with the data
            }
        }
    }

    private fun showJournalDetails(journal: Journal) {
        // Logic to handle journal click (e.g., open in full view)
        // Example:
        // You can use an Intent to open another activity to show the journal details
//        val intent = Intent(this, JournalDetailActivity::class.java)
//        intent.putExtra("journalId", journal.id)
//        startActivity(intent)
    }
}
