package com.group.web.service;


import com.group.web.dto.ClubDto;
import com.group.web.models.Club;

import java.util.List;

public interface ClubService {
    List<ClubDto> findAllClubs();
    Club saveClub(ClubDto clubDto);

    ClubDto findClubById(Long clubId);
    void updateClub(ClubDto club);

    void delete(Long clubId);
    List<ClubDto> searchClubs(String query);
}