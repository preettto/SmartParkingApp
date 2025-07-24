package com.yourdomain.smartparking.ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yourdomain.smartparking.R
import com.yourdomain.smartparking.adapters.ReservationAdapter
import com.yourdomain.smartparking.adapters.SlotAdapter
import com.yourdomain.smartparking.models.Reservation
import com.yourdomain.smartparking.models.Slot
import com.yourdomain.smartparking.utils.FirebaseUtils

class AdminActivity : AppCompatActivity() {
    private lateinit var slotAdapter: SlotAdapter
    private lateinit var reservationAdapter: ReservationAdapter
    private val slots = mutableListOf<Slot>()
    private val reservations = mutableListOf<Reservation>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        val slotRecyclerView = findViewById<RecyclerView>(R.id.adminSlotRecyclerView)
        val reservationRecyclerView = findViewById<RecyclerView>(R.id.reservationRecyclerView)
        val revenueText = findViewById<TextView>(R.id.revenueText)
        val addSlotBtn = findViewById<Button>(R.id.addSlotBtn)

        slotAdapter = SlotAdapter(slots) { }
        slotRecyclerView.layoutManager = LinearLayoutManager(this)
        slotRecyclerView.adapter = slotAdapter

        reservationAdapter = ReservationAdapter(reservations)
        reservationRecyclerView.layoutManager = LinearLayoutManager(this)
        reservationRecyclerView.adapter = reservationAdapter

        fetchSlots()
        fetchReservations()
        fetchRevenue(revenueText)

        addSlotBtn.setOnClickListener {
            val newSlotId = (slots.size + 1).toString()
            val newSlot = Slot(id = newSlotId, status = "available", reservedBy = null, location = Pair(1, slots.size + 1))
            FirebaseUtils.db.collection("slots").document(newSlotId).set(newSlot)
        }
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

    private fun fetchReservations() {
        FirebaseUtils.db.collection("reservations")
            .addSnapshotListener { snapshot, _ ->
                reservations.clear()
                snapshot?.forEach { doc ->
                    reservations.add(doc.toObject(Reservation::class.java))
                }
                reservationAdapter.notifyDataSetChanged()
            }
    }

    private fun fetchRevenue(revenueText: TextView) {
        FirebaseUtils.db.collection("reservations")
            .whereEqualTo("status", "completed")
            .get()
            .addOnSuccessListener { snapshot ->
                var totalRevenue = 0
                for (doc in snapshot.documents) {
                    val start = doc.getLong("startTime") ?: 0L
                    val end = doc.getLong("endTime") ?: 0L
                    totalRevenue += calculateCharges(start, end)
                }
                revenueText.text = "Total Revenue: ₹$totalRevenue"
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to fetch revenue", Toast.LENGTH_SHORT).show()
            }
    }

    private fun calculateCharges(startTime: Long, endTime: Long): Int {
        val durationHours = ((endTime - startTime) / (1000 * 60 * 60)).toInt().coerceAtLeast(1)
        return durationHours * 30 // ₹30 per hour
    }

    override fun onResume() {
        super.onResume()
        // Add long click to remove slot
        slotAdapter = SlotAdapter(slots) {  }
        slotAdapter.setOnLongClickListener { slot ->
            FirebaseUtils.db.collection("slots").document(slot.id).delete()
        }
    }
} 