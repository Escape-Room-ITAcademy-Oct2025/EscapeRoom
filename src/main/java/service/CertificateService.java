package service;

import dao.PlayerDaoImpl;
import dao.RoomDaoImpl;
import model.Player;
import model.Room;

import java.time.LocalDateTime;

/**
 * Genera certificados para los jugadores que completan una sala.
 */
public class CertificateService {

    private final PlayerDaoImpl playerDao;
    private final RoomDaoImpl roomDao;

    public CertificateService() {
        this.playerDao = new PlayerDaoImpl();
        this.roomDao = new RoomDaoImpl();
    }

    public String generateCertificate(int playerId, int roomId) {
        Player player = playerDao.findById(playerId);
        Room room = roomDao.findById(roomId);

        if (player == null || room == null) {
            return "❌ Error: Player or Room not found.";
        }

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
