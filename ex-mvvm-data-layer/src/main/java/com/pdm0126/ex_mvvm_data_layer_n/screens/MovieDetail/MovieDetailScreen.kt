package com.pdm0126.ex_mvvm_data_layer_n.screens.MovieDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.pdm0126.ex_mvvm_data_layer_n.dummy.dummyMovies
import com.pdm0126.ex_mvvm_data_layer_n.components.AppScaffold
import com.pdm0126.ex_mvvm_data_layer_n.dummy.dummyMovies

@Composable
fun MovieDetailScreen(
  movieId: Int,
  navigateBack: () -> Unit
) {
  val movie = dummyMovies.find { it.id == movieId }

  AppScaffold(
    title = movie?.title ?: "Detail",
    navigationIcon = {
      IconButton(onClick = navigateBack) {
        Icon(
          imageVector = Icons.AutoMirrored.Filled.ArrowBack,
          contentDescription = "Back"
        )
      }
    }
  ) { padding ->
    movie?.let {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(padding)
          .verticalScroll(rememberScrollState())
      ) {
        AsyncImage(
          model = it.backdropUrl,
          contentDescription = it.title,
          modifier = Modifier
            .fillMaxWidth()
            .height(220.dp),
          contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = it.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = it.originalTitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Rating: ${"%.2f".format(it.voteAverage)}  -  ${it.releaseDate}",
            style = MaterialTheme.typography.bodyMedium
          )
          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "Popularidad: ${"%.1f".format(it.popularity)}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
          Spacer(modifier = Modifier.height(16.dp))
          Text(
            text = "Sinopsis",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = it.overview,
            style = MaterialTheme.typography.bodyMedium
          )
        }
      }
    }
  }
}