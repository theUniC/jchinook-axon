package org.chinook.jchinook.domainmodel.artist

import org.axonframework.test.aggregate.AggregateTestFixture
import org.axonframework.test.aggregate.FixtureConfiguration
import org.chinook.jchinook.application.command.ChangeArtistNameCommand
import org.chinook.jchinook.application.command.CreateArtistCommand
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.util.UUID

private const val INVALID_ARTIST_NAME = ""
private const val VALID_ARTIST_NAME = "test"

object ArtistSpec : Spek({
    describe("Artist Aggregate") {
        val fixture: FixtureConfiguration<Artist> by memoized { AggregateTestFixture(Artist::class.java) }

        describe("Creation") {
            it("throws an exception when artist name is not valid") {
                fixture.givenNoPriorActivity()
                    .`when`(CreateArtistCommand(UUID.randomUUID(), INVALID_ARTIST_NAME))
                    .expectException(InvalidArtistName::class.java)
            }

            it("creates a new artist successfully") {
                val uuid = UUID.randomUUID()
                fixture.givenNoPriorActivity()
                    .`when`(CreateArtistCommand(uuid, VALID_ARTIST_NAME))
                    .expectSuccessfulHandlerExecution()
                    .expectEvents(ArtistWasCreatedEvent(uuid, VALID_ARTIST_NAME))
            }
        }

        describe("Changes name") {
            it("throws an exception when artist name is not valid when changing artist name") {
                val uuid = UUID.randomUUID()
                fixture.givenCommands(CreateArtistCommand(uuid, VALID_ARTIST_NAME))
                    .`when`(ChangeArtistNameCommand(uuid, INVALID_ARTIST_NAME))
                    .expectException(InvalidArtistName::class.java)
            }

            it("changes artist name successfully") {
                val uuid = UUID.randomUUID()
                fixture.givenCommands(CreateArtistCommand(uuid, VALID_ARTIST_NAME))
                    .`when`(ChangeArtistNameCommand(uuid, "Changed Artist Name"))
                    .expectEvents(ArtistNameWasChanged(uuid, "Changed Artist Name"))
            }
        }
    }
})
