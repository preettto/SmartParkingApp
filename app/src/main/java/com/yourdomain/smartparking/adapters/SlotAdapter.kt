package com.yourdomain.smartparking.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yourdomain.smartparking.models.Slot
import com.yourdomain.smartparking.R

class SlotAdapter(
    private val slots: List<Slot>,
    private val onSlotClick: (Slot) -> Unit
) : RecyclerView.Adapter<SlotAdapter.SlotViewHolder>() {
    private var onLongClick: ((Slot) -> Unit)? = null

    inner class SlotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val slotText: TextView = itemView.findViewById(R.id.slotText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlotViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_slot, parent, false)
        return SlotViewHolder(view)
    }

    override fun onBindViewHolder(holder: SlotViewHolder, position: Int) {
        val slot = slots[position]
        holder.slotText.text = "Slot ${slot.id} - ${slot.status}"
        val iconRes = when (slot.status) {
            "available" -> R.drawable.ic_slot_available
            "reserved" -> R.drawable.ic_slot_reserved
            "occupied" -> R.drawable.ic_slot_occupied
            else -> R.drawable.ic_car
        }
        holder.itemView.findViewById<ImageView>(R.id.slotIcon).setImageResource(iconRes)
        holder.itemView.setOnClickListener { onSlotClick(slot) }
        holder.itemView.setOnLongClickListener {
            onLongClick?.invoke(slot)
            true
        }
    }

    override fun getItemCount() = slots.size

    fun setOnLongClickListener(listener: (Slot) -> Unit) {
        onLongClick = listener
    }
} 