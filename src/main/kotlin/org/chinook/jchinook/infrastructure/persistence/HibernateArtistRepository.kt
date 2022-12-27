package org.chinook.jchinook.infrastructure.persistence

import org.chinook.jchinook.domainmodel.artist.Artist
import org.chinook.jchinook.domainmodel.artist.ArtistRepository
import org.springframework.stereotype.Component
import javax.persistence.EntityManager

@Component
class HibernateArtistRepository(val em: EntityManager) : ArtistRepository {
    override fun all(offset: Int, limit: Int): List<Artist> {
        return em
            .createQuery(
                """
                SELECT a
                FROM Artist a
                """.trimIndent(),
                Artist::class.java
            )
            .setMaxResults(limit)
            .setFirstResult(offset)
            .resultList
    }
}
