package com.octahedron.ui.card


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.octahedron.ui.helper.AlbumArt
import com.octahedron.ui.helper.formatHms
import org.jetbrains.skia.Bitmap
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun LastImageCard(
    lastImageName: String?,
    lastImageAt: Instant?,
    bitmap: Bitmap?,
    artist: String? = null,
    album: String? = null,
    durationMs: Long? = null,
    modifier: Modifier = Modifier,
    onOpen: (() -> Unit)? = null
) {
    val titleText = "Last recorded track"
    val emptyText = "No image sent yet"
    val untitled = "Untitled"

    val metaSep = " — "
    val tailSep = " · "
    val datePattern = "dd/MM HH:mm:ss"
    val formatter = remember(datePattern) {
        DateTimeFormatter.ofPattern(datePattern).withZone(ZoneId.systemDefault())
    }

    val noTrack = lastImageName == null &&
            artist.isNullOrBlank() &&
            album.isNullOrBlank() &&
            bitmap == null

    ElevatedCard(modifier) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = titleText,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            if (!noTrack && lastImageName == null && lastImageAt == null && bitmap == null) {
                Text(
                    emptyText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .let { m -> if (onOpen != null) m.clickable { onOpen() } else m },
                        contentAlignment = Alignment.Center
                    ) {
                        AlbumArt(
                            bitmap = bitmap,
                            sizeDp = 72,
                        )
                    }

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        val title = if (noTrack) {
                            "Octahedron"
                        } else {
                            lastImageName ?: untitled
                        }
                        Text(
                            text = title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleSmall
                        )

                        val meta = if (noTrack) {
                            null
                        } else {
                            listOfNotNull(
                                artist?.takeIf { it.isNotBlank() },
                                album?.takeIf { it.isNotBlank() }
                            ).joinToString(metaSep).ifBlank { null }
                        }

                        meta?.let {
                            Text(
                                text = it,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        val duration = if (noTrack) {
                            "4:44"
                        } else {
                            durationMs?.let { formatHms(it) }
                        }

                        val whenTxt = formatter.format(
                            if (noTrack) Instant.now() else (lastImageAt ?: Instant.now())
                        )

                        val tail = listOfNotNull(duration, whenTxt)
                            .joinToString(tailSep)
                            .ifBlank { null }

                        tail?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}