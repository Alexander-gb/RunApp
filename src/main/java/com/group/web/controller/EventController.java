package com.group.web.controller;


import com.group.web.dto.ClubDto;
import com.group.web.dto.EventDto;
import com.group.web.models.Event;
import com.group.web.models.UserEntity;
import com.group.web.security.SecurityUtil;
import com.group.web.service.EventService;
import com.group.web.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {
    private EventService eventService;
    private UserService userService;

    @Autowired
    public EventController(EventService eventService, UserService userService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    @GetMapping
    public String eventList(Model model) {
        UserEntity user = new UserEntity();
        List<EventDto> events = eventService.findAllEvents();
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("user", user);
        model.addAttribute("events", events);
        return "events-list";
    }
    @GetMapping("/{eventId}")
    public String viewEvent(@PathVariable("eventId") Long eventId, Model model) {
        UserEntity user = new UserEntity();
        EventDto eventDto = eventService.findByEventId(eventId);
        String username = SecurityUtil.getSessionUser();
        if(username != null) {
            user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        model.addAttribute("club", eventDto);
        model.addAttribute("user", user);
        model.addAttribute("event", eventDto);
        return "events-detail";

    }

    @GetMapping("/{clubId}/new")
    public String createEventForm(@PathVariable("clubId") Long clubId, Model model) {
        Event event = new Event();
        model.addAttribute("clubId", clubId);
        model.addAttribute("event", event);
        return "events-create";
    }
    @GetMapping("/{eventId}/edit")
    public String editEventForm(@PathVariable("eventId") Long eventId, Model model) {
        EventDto event = eventService.findByEventId(eventId);
        model.addAttribute("event", event);
        return "events-edit";
    }
    @PostMapping("/{clubId}")
    public String createEvent(@PathVariable("clubId") Long clubId, @ModelAttribute("event")EventDto eventDto,
                              BindingResult result,
                              Model model) {
        if(result.hasErrors()) {
            model.addAttribute("event", eventDto);
            return "clubs-create";
        }
        eventService.createEvent(clubId, eventDto);
        return "redirect:/clubs/" + clubId;
    }
    @PostMapping("/{eventId}/edit")
    public String updateEvent(@PathVariable("eventId") Long eventId,
                             @Valid @ModelAttribute("event") EventDto event,
                             BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("event", event);
            return "events-edit";
        }
        EventDto eventDto = eventService.findByEventId(eventId);
        event.setId(eventId);
        event.setClub(eventDto.getClub());
        eventService.updateEvent(event);
        return "redirect:/events";
    }
    @GetMapping("/{eventId}/delete")
    public String deleteEvent(@PathVariable("eventId") Long eventId) {
        eventService.deleteEvent(eventId);
        return "redirect:/events";
    }
}
