package ktfc.scoreboard;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class MainScoreboard implements ApplicableScoreboard {

  private static final String ObjectiveName = "sidebar_board";

  private Scoreboard scoreboard;
  private final Configuration config;

  private MainScoreboard(Configuration config) {
    this.config = config;
  }

  public static MainScoreboard withConfiguration(FileConfiguration config) {
    Preconditions.checkNotNull(config);
    var scoreboard = new MainScoreboard(config);
    scoreboard.initialize();
    return scoreboard;
  }

  @Override
  public void initialize() {
    this.scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
    final var title = colorText(config.getString("scoreboard.title"));
    final var scores = config.getStringList("scoreboard.lines");
    final var objective = getOrRegisterObjective();
    objective.setDisplayName(title);
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    for (int lineIndex = 0; lineIndex < scores.size(); lineIndex++) {
      int score = scores.size() - lineIndex;
      var line = colorText(scores.get(lineIndex));
      objective.getScore(line).setScore(score);
    }
  }

  @Override
  public void apply(Player player) {
    player.setScoreboard(scoreboard);
  }

  private String colorText(String text) {
    return ChatColor.translateAlternateColorCodes('&', text);
  }

  private Objective getOrRegisterObjective() {
    var objective = this.scoreboard.getObjective(ObjectiveName);
    if (objective == null) {
      objective = this.scoreboard.registerNewObjective(ObjectiveName, "dummy");
    }
    return objective;
  }
}
