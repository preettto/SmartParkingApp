package com.yourdomain.smartparking.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yourdomain.smartparking.R
import com.yourdomain.smartparking.models.Reservation

class ReservationAdapter(private val reservations: List<Reservation>) : RecyclerView.Adapter<ReservationAdapter.ReservationViewHolder>() {
    inner class ReservationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reservationText: TextView = itemView.findViewById(R.id.reservationText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_reservation, parent, false)
        return ReservationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val reservation = reservations[position]
        holder.reservationText.text = "Reservation: ${reservation.id}, Slot: ${reservation.slotId}, Status: ${reservation.status}"
    }

    override fun getItemCount() = reservations.size
} 