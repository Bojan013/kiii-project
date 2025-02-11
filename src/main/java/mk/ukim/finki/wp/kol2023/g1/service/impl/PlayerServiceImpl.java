package mk.ukim.finki.wp.kol2023.g1.service.impl;

import mk.ukim.finki.wp.kol2023.g1.model.Player;
import mk.ukim.finki.wp.kol2023.g1.model.PlayerPosition;
import mk.ukim.finki.wp.kol2023.g1.model.Team;
import mk.ukim.finki.wp.kol2023.g1.model.exceptions.InvalidPlayerIdException;
import mk.ukim.finki.wp.kol2023.g1.model.exceptions.InvalidTeamIdException;
import mk.ukim.finki.wp.kol2023.g1.repository.PlayerRepository;
import mk.ukim.finki.wp.kol2023.g1.repository.TeamRepository;
import mk.ukim.finki.wp.kol2023.g1.service.PlayerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public List<Player> listAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player findById(Long id) {
        return playerRepository.findById(id).orElseThrow(InvalidPlayerIdException::new);
    }

    @Override
    public Player create(String name, String bio, Double pointsPerGame, PlayerPosition position, Long team) {
        return playerRepository.save(new Player(name, bio, pointsPerGame, position, teamRepository.findById(team).orElseThrow(InvalidTeamIdException::new)));
    }

    @Override
    public Player update(Long id, String name, String bio, Double pointsPerGame, PlayerPosition position, Long team) {
        Team t = teamRepository.findById(team).orElseThrow(InvalidTeamIdException::new);
        Player p = playerRepository.findById(id).orElseThrow(InvalidPlayerIdException::new);
        p.setName(name);
        p.setBio(bio);
        p.setPointsPerGame(pointsPerGame);
        p.setPosition(position);
        p.setTeam(t);
        return playerRepository.save(p);
    }

    @Override
    public Player delete(Long id) {
        Player p = playerRepository.findById(id).orElseThrow(InvalidPlayerIdException::new);
        playerRepository.delete(p);
        return p;
    }

    @Override
    public Player vote(Long id) {
        Player p = playerRepository.findById(id).orElseThrow(InvalidPlayerIdException::new);
        p.setVotes(p.getVotes() + 1);
        return playerRepository.save(p);
    }

    @Override
    public List<Player> listPlayersWithPointsLessThanAndPosition(Double pointsPerGame, PlayerPosition position) {
        if (pointsPerGame != null && position != null) {
            return playerRepository.getPlayersByPointsPerGameLessThanAndPosition(pointsPerGame, position);
        }

        if (pointsPerGame != null) {
            return playerRepository.getPlayersByPointsPerGameLessThan(pointsPerGame);
        }

        if (position != null) {
            return playerRepository.getPlayersByPosition(position);
        }

        return playerRepository.findAll();
    }
}
