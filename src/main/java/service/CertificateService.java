package service;

import dao.PlayerDaoImpl;
import dao.RoomDaoImpl;
import model.Player;
import model.Room;

import java.time.LocalDateTime;
import java.util.Optional;

public class CertificateService {

    private final PlayerDaoImpl playerDao;
    private final RoomDaoImpl roomDao;

    public CertificateService() {
        this.playerDao = new PlayerDaoImpl();
        this.roomDao = new RoomDaoImpl();
    }

    public String generateCertificate(int playerId, int roomId) {
        Optional<Player> playerOpt = playerDao.findById(playerId);
        Optional<Room> roomOpt = roomDao.findById(roomId);

        if (playerOpt.isEmpty() || roomOpt.isEmpty()) {
            return "❌ Error: Player or Room not found.";
        }

        Player player = playerOpt.get();
        Room room = roomOpt.get();

        return """
                🎉 ESCAPE ROOM CERTIFICATE 🎉
                Player: %s
                Room: %s
                Difficulty: %s
                Completion Date: %s
                Congratulations for completing the challenge!
                """.formatted(
                player.getName(),
                room.getName(),
                room.getDifficulty(),
                LocalDateTime.now().toString()
        );
    }
}
