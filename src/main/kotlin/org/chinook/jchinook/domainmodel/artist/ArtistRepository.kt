package org.chinook.jchinook.domainmodel.artist

interface ArtistRepository {
    fun all(offset: Int = 0, limit: Int = 10): List<Artist>
}
