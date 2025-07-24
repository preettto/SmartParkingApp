package com.Preet.SmartPark.adapters

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.Preet.SmartPark.models.Slot

@Composable
fun SlotGrid(
    slots: List<Slot>,
    onSlotClick: (Slot) -> Unit,
    onSlotLongClick: ((Slot) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(slots) { slot ->
            SlotItem(
                slot = slot,
                onClick = { onSlotClick(slot) },
                onLongClick = { onSlotLongClick?.invoke(slot) }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SlotItem(
    slot: Slot,
    onClick: () -> Unit,
    onLongClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when (slot.status) {
        "available" -> Color.Green.copy(alpha = 0.3f)
        "reserved" -> Color.Yellow.copy(alpha = 0.3f)
        "occupied" -> Color.Red.copy(alpha = 0.3f)
        else -> Color.Gray.copy(alpha = 0.3f)
    }

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            ),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = "Parking slot",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Slot ${slot.id} - ${slot.status}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}