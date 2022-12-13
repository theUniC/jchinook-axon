package org.chinook.jchinook.domainmodel.artist

data class ArtistNameWasChanged(
    val artistId: Int,
    val newArtistName: String
)
