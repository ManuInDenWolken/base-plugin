package ktfc.scoreboard;

import org.bukkit.entity.Player;

public interface ApplicableScoreboard {

  void initialize();

  void apply(Player player);
}
