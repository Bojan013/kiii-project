package mk.ukim.finki.wp.kol2023.g1.repository;

import mk.ukim.finki.wp.kol2023.g1.model.Player;
import mk.ukim.finki.wp.kol2023.g1.model.PlayerPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> getPlayersByPointsPerGameLessThanAndPosition(Double pointsPerGame, PlayerPosition position);

    List<Player> getPlayersByPointsPerGameLessThan(Double pointsPerGame);

    List<Player> getPlayersByPosition(PlayerPosition position);
}
