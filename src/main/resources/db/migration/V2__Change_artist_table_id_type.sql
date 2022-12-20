DROP PROCEDURE IF EXISTS ChangeArtistTableIdType;

CREATE PROCEDURE ChangeArtistTableIdType()
BEGIN
    DECLARE done INT DEFAULT FALSE;
    DECLARE id INT;
    DECLARE uuid VARCHAR(36);
    DECLARE artists CURSOR FOR SELECT ArtistId FROM Artist;
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

    ALTER TABLE Artist ADD COLUMN ArtistUuid VARCHAR(36) AFTER ArtistId;
    ALTER TABLE Album ADD COLUMN ArtistUuid VARCHAR(36);

    OPEN artists;

    read_loop: LOOP
        FETCH artists INTO id;
        IF done THEN
            LEAVE read_loop;
        END IF;
        SET uuid = UUID();
        UPDATE Artist SET ArtistUuid = uuid WHERE ArtistId = id;
        UPDATE Album SET ArtistUuid = uuid WHERE ArtistId = id;
    END LOOP;

    CLOSE artists;

    SET foreign_key_checks = 0;
    ALTER TABLE Album DROP FOREIGN KEY FK_AlbumArtistId;
    ALTER TABLE Artist MODIFY ArtistId INT NOT NULL;
    ALTER TABLE Artist DROP PRIMARY KEY;
    ALTER TABLE Artist DROP COLUMN ArtistId;
    ALTER TABLE Album DROP COLUMN ArtistId;
    ALTER TABLE Artist CHANGE COLUMN ArtistUuid ArtistId VARCHAR(36) NOT NULL;
    ALTER TABLE Album CHANGE COLUMN ArtistUuid ArtistId VARCHAR(36) NOT NULL;
    ALTER TABLE Artist ADD PRIMARY KEY (ArtistId);
    ALTER TABLE Album ADD FOREIGN KEY FK_AlbumArtistId (ArtistId) REFERENCES Artist(ArtistId);
    SET foreign_key_checks = 1;
END;

CALL ChangeArtistTableIdType;

DROP PROCEDURE ChangeArtistTableIdType;