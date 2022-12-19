package org.chinook.jchinook.infrastructure.delivery.spring.dtos

import java.util.UUID

data class CreateArtistInputDto(
    val id: UUID,
    val name: String
)
