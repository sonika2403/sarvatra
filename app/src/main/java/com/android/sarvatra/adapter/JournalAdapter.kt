package com.android.sarvatra.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.sarvatra.database.journal.Journal
import com.android.sarvatra.databinding.ItemJournalEntryBinding
import com.android.sarvatra.utils.getTimeAgo

class JournalAdapter(
    private var journals: List<Journal>, // Initial list of journals
    private val onClick: (Journal) -> Unit // Callback for when an item is clicked
) : RecyclerView.Adapter<JournalAdapter.JournalViewHolder>() {

    private val expandedPositions = mutableSetOf<Int>() // Set to track expanded item positions

    // ViewHolder for individual journal items
    class JournalViewHolder(private val binding: ItemJournalEntryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            journal: Journal,
            isExpanded: Boolean,
            toggleExpand: (Int) -> Unit,
            onClick: (Journal) -> Unit
        ) {
            binding.journalTitle.text = journal.title
            binding.journalTimestamp.text = getTimeAgo((journal.timeCreated).toLong())
            binding.journalDescription.text =
                if (isExpanded) journal.body else journal.body.take(100)

            binding.journalDescription.maxLines = if (isExpanded) Int.MAX_VALUE else 3

            binding.cardView.setOnClickListener { toggleExpand(adapterPosition) }

            binding.root.setOnClickListener { onClick(journal) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
        val binding = ItemJournalEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JournalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
        val journal = journals[position]
        val isExpanded = expandedPositions.contains(position)

        holder.bind(
            journal,
            isExpanded,
            { pos ->
                // Toggle expanded state
                if (expandedPositions.contains(pos)) {
                    expandedPositions.remove(pos)
                } else {
                    expandedPositions.add(pos)
                }
                notifyItemChanged(pos) // Update only the changed item
            },
            onClick
        )
    }

    override fun getItemCount(): Int = journals.size // Return the size of the journal list

    // Manually update the journal list and notify adapter
    fun submitList(newJournals: List<Journal>) {
        journals = newJournals
        notifyDataSetChanged() // Notify that the data has changed
    }
}
