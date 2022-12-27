package org.chinook.jchinook.application.query

data class GetArtistsQuery(
    val offset: Int = 0,
    val limit: Int = 10,
)
