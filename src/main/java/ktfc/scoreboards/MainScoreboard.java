package ktfc.scoreboards;

import com.google.common.base.Preconditions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;

public class MainScoreboard implements ApplicableScoreboard {

  private Scoreboard scoreboard;
  private final Configuration config;

  private MainScoreboard(Configuration config) {
    this.config = config;
  }

  public static MainScoreboard withConfiguration(FileConfiguration config) {
    Preconditions.checkNotNull(config);
    MainScoreboard scoreboard = new MainScoreboard(config);
    scoreboard.initialize();
    return scoreboard;
  }

  @Override
  public void initialize() {
    this.scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
    final String title = colorText(config.getString("scoreboard.title"));
    final List<String> scores = config.getStringList("scoreboard.lines");
    final Objective objective = scoreboard.registerNewObjective("sidebar_board", "dummy");
    objective.setDisplayName(title);
    objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    for (int scoreIndex = 0; scoreIndex < scores.size(); scoreIndex++) {
      String line = colorText(scores.get(scoreIndex));
      objective.getScore(line).setScore(scoreIndex);
    }
  }

  @Override
  public void apply(Player player) {
    player.setScoreboard(scoreboard);
  }

  private String colorText(String text) {
    return ChatColor.translateAlternateColorCodes('&', text);
  }
}
