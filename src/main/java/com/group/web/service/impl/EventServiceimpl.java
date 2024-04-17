package com.group.web.service.impl;

import com.group.web.dto.EventDto;
import com.group.web.models.Club;
import com.group.web.models.Event;
import com.group.web.repository.ClubRepository;
import com.group.web.repository.EventRepository;
import com.group.web.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.group.web.mapper.EventMapper.mapToEvent;
import static com.group.web.mapper.EventMapper.mapToEventDto;

@Service
public class EventServiceimpl implements EventService {
    private EventRepository eventRepository;
    private ClubRepository clubRepository;

    @Autowired

    public EventServiceimpl(EventRepository eventRepository, ClubRepository clubRepository) {
        this.eventRepository = eventRepository;
        this.clubRepository = clubRepository;
    }

    @Override
    public void createEvent(Long clubId, EventDto eventDto) {
        Club club = clubRepository.findById(clubId).get();
        Event event = mapToEvent(eventDto);
        event.setClub(club);
        eventRepository.save(event);
    }


    @Override
    public List<EventDto> findAllEvents() {
        List<Event> events  = eventRepository.findAll();
        return events.stream().map(event -> mapToEventDto(event)).collect(Collectors.toList());
    }

    @Override
    public EventDto findByEventId(Long eventId) {
        Event event = eventRepository.findById(eventId).get();
        return mapToEventDto(event);
    }
    @Override
    public void updateEvent(EventDto eventDto) {
        Event event = mapToEvent(eventDto);
        eventRepository.save(event);
    }

    @Override
    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    } {
    }

}
