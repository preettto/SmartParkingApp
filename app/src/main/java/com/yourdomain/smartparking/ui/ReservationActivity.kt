package com.yourdomain.smartparking.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yourdomain.smartparking.R
import com.yourdomain.smartparking.adapters.SlotAdapter
import com.yourdomain.smartparking.models.Slot
import com.yourdomain.smartparking.utils.FirebaseUtils

class ReservationActivity : AppCompatActivity() {
    private lateinit var slotAdapter: SlotAdapter
    private val slots = mutableListOf<Slot>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)

        val recyclerView = findViewById<RecyclerView>(R.id.slotRecyclerView)
        slotAdapter = SlotAdapter(slots) { slot -> reserveSlot(slot) }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = slotAdapter

        fetchSlots()
    }

    private fun fetchSlots() {
        FirebaseUtils.db.collection("slots")
            .addSnapshotListener { snapshot, _ ->
                slots.clear()
                snapshot?.forEach { doc ->
                    slots.add(doc.toObject(Slot::class.java))
                }
                slotAdapter.notifyDataSetChanged()
            }
    }

    private fun reserveSlot(slot: Slot) {
        val userId = FirebaseUtils.auth.currentUser?.uid ?: return
        FirebaseUtils.db.collection("slots").document(slot.id)
            .update(mapOf("status" to "reserved", "reservedBy" to userId))
            .addOnSuccessListener {
                Toast.makeText(this, "Slot reserved!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Reservation failed", Toast.LENGTH_SHORT).show()
            }
    }
} 