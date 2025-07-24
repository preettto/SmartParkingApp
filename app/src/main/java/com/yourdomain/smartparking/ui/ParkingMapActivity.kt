package com.yourdomain.smartparking.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.yourdomain.smartparking.R
import com.yourdomain.smartparking.models.Slot
import com.yourdomain.smartparking.utils.FirebaseUtils

class ParkingMapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_map)

        val gridLayout = findViewById<GridLayout>(R.id.parkingGrid)
        gridLayout.rowCount = 5
        gridLayout.columnCount = 8
        fetchAndDisplaySlots(gridLayout)
    }

    private fun fetchAndDisplaySlots(gridLayout: GridLayout) {
        FirebaseUtils.db.collection("slots")
            .get()
            .addOnSuccessListener { snapshot ->
                gridLayout.removeAllViews()
                val slots = snapshot.documents.map { it.toObject(Slot::class.java)!! }
                for (slot in slots) {
                    val icon = ImageView(this)
                    val iconRes = when (slot.status) {
                        "available" -> R.drawable.ic_slot_available
                        "reserved" -> R.drawable.ic_slot_reserved
                        "occupied" -> R.drawable.ic_slot_occupied
                        else -> R.drawable.ic_car
                    }
                    icon.setImageResource(iconRes)
                    val params = GridLayout.LayoutParams()
                    params.width = 0
                    params.height = GridLayout.LayoutParams.WRAP_CONTENT
                    params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    icon.layoutParams = params
                    icon.setPadding(8, 8, 8, 8)
                    gridLayout.addView(icon)
                }
            }
    }
} 