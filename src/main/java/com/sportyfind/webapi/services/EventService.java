package com.sportyfind.webapi.services;

import com.sportyfind.webapi.dtos.EventResDto;
import com.sportyfind.webapi.dtos.FieldBookingDto;
import com.sportyfind.webapi.dtos.GameMatchCreateResDto;
import com.sportyfind.webapi.dtos.TeamCreateResDto;
import com.sportyfind.webapi.enums.EVENT_TYPE;
import com.sportyfind.webapi.models.SearchGameMatchQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EventService {
    @Autowired
    BookingService bookingService;

    @Autowired
    GameService gameService;

    @Autowired
    TeamService teamService;

    public List<EventResDto> getAllEvent(Long userId) throws Exception {
        List<EventResDto> result = new ArrayList<>();

        // Get team list
        List<TeamCreateResDto> teams = TeamCreateResDto.fromEntities(teamService.getAllTeamsByUserId(userId));
        teams.stream().map(team -> {
            // Get game matched with team
            SearchGameMatchQuery query = new SearchGameMatchQuery();
            query.teamId = team.id;
            query.status = 1;
            List<GameMatchCreateResDto> games = gameService.searchGameMatch(query);
            return games.stream().map(game -> {
                // check if game has been added to result
                if (result.stream().anyMatch(event -> {
                    if (event.data instanceof GameMatchCreateResDto) {
                        return ((GameMatchCreateResDto) event.data).id == game.id;
                    }
                    return false;
                })) {
                    return null;
                }

                // Add game event
                EventResDto event = new EventResDto();
                switch (game.gameType) {
                    case 0:
                        event.title = "Đá kèo - " + game.booking.fieldName;
                        break;
                    case 1:
                        event.title = "Đá nội bộ - " + game.booking.fieldName;
                    default:
                        break;
                }
                event.type = EVENT_TYPE.GAME;
                event.data = game.booking;
                return event;
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }).forEach(result::addAll);


        // Get booking event
        List<FieldBookingDto> bookings = bookingService.getBookingByCustomerId(userId);
        bookings.stream().map(booking -> {
            EventResDto event = new EventResDto();
            event.title = booking.fieldName;
            event.type = EVENT_TYPE.BOOKING;
            event.data = booking;
            return event;
        }).filter(b -> {
            // check if booking has been added to result
            return result.stream().noneMatch(event -> {
                if (event.data instanceof FieldBookingDto) {
                    return ((FieldBookingDto) event.data).bookingId == ((FieldBookingDto) b.data).bookingId;
                }
                return false;
            });
        }).forEach(result::add);

        // Combine and sort
        return result;
    }
}
