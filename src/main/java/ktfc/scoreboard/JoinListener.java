package ktfc.scoreboard;

import com.google.common.base.Preconditions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

  private ApplicableScoreboard scoreboard;

  private JoinListener(ApplicableScoreboard scoreboard) {
    this.scoreboard = scoreboard;
  }

  public static JoinListener withScoreboard(ApplicableScoreboard scoreboard) {
    Preconditions.checkNotNull(scoreboard);
    return new JoinListener(scoreboard);
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    var player = event.getPlayer();
    scoreboard.apply(player);
  }
}
