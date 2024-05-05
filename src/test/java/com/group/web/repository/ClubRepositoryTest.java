package com.group.web.repository;

import com.group.web.models.Club;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest


public class ClubRepositoryTest {
    @Autowired
    private ClubRepository clubRepository;

    @Test
    public void ClubRepository_SaveAll_ReturnedSavedData() {
        //Arrange
        Club club = Club.builder().title("Running together club").build();

        //Act
        Club savedClub = clubRepository.save(club);

        //Assert
        Assertions.assertThat(savedClub).isNotNull();
        Assertions.assertThat(savedClub.getId()).isGreaterThan(0);

    }
}
