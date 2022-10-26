package ktfc;

import ktfc.scoreboard.ApplicableScoreboard;
import ktfc.scoreboard.MainScoreboard;
import ktfc.scoreboard.JoinListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class BasePlugin extends JavaPlugin {

  @Override
  public void onEnable() {
    saveDefaultConfig();
    setupScoreboard();
  }

  @Override
  public void onDisable() {
  }

  private void setupScoreboard() {
    ApplicableScoreboard scoreboard = MainScoreboard.withConfiguration(getConfig());
    registerListener(JoinListener.withScoreboard(scoreboard));
  }

  private void registerListener(Listener listener) {
    Bukkit.getPluginManager().registerEvents(listener, this);
  }
}
