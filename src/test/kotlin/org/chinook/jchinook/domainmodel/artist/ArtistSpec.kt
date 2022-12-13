package org.chinook.jchinook.domainmodel.artist

import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.chinook.jchinook.application.command.CreateArtistCommand
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

private const val INVALID_ARTIST_NAME = ""
private const val VALID_ARTIST_NAME = "test"

object ArtistSpec : Spek({
    describe("Artist Aggregate") {
        val fixture: FixtureConfiguration<Artist> by memoized { AggregateTestFixture(Artist::class.java) }

        it("throws an exception when artist name is not valid") {
            fixture.givenNoPriorActivity()
                .`when`(CreateArtistCommand(INVALID_ARTIST_NAME))
                .expectException(InvalidArtistName::class.java)
        }

        it("creates a new artist successfully") {
            fixture.givenNoPriorActivity()
                .`when`(CreateArtistCommand(VALID_ARTIST_NAME))
                .expectSuccessfulHandlerExecution()
                .expectEvents(ArtistWasCreatedEvent(VALID_ARTIST_NAME))
        }
    }
})
