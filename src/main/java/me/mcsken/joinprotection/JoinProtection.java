package me.mcsken.joinprotection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public final class JoinProtection extends JavaPlugin implements Listener {
    private static JoinProtection instance;
    private boolean enable;
    private int seconds;
    private boolean movement;
    private String messageStart;
    private String messageEnded;

    public static JoinProtection getInstance() {
        return instance;
    }


    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);

        this.enable = getConfig().getBoolean("Enable");
        this.seconds = getConfig().getInt("ProtectionTime");
        this.movement = getConfig().getBoolean("EnableMovement");
        this.messageStart = ChatColor.translateAlternateColorCodes('&',
                Objects.requireNonNull(getConfig().getString("Messages.CooldownStart"))
                        .replace("%time%", Integer.toString(this.seconds)));
        this.messageEnded = ChatColor.translateAlternateColorCodes('&',
                Objects.requireNonNull(getConfig().getString("Messages.CooldownEnded"))
                        .replace("%time%", Integer.toString(this.seconds)));

        try {
            if (!Objects.requireNonNull(getConfig().getString("Version")).equalsIgnoreCase("1.0")) {
                getLogger().warning("Please delete config.yml to receive newest updates!");
            }
        } catch (NullPointerException e) {
            getLogger().severe("Please delete config.yml to receive newest updates!");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        if (this.enable && player.hasPermission("joinProtection.on")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance(), () -> {
                player.sendMessage(JoinProtection.this.messageStart);
                player.setNoDamageTicks(JoinProtection.this.seconds * 20);
                if (!JoinProtection.this.movement) {
                    player.setWalkSpeed(0.0F);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 2147483647, 128, false, false));
                }
            });

            Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance(), () -> {
                player.sendMessage(JoinProtection.this.messageEnded);
                if (!JoinProtection.this.movement) {
                    player.setWalkSpeed(0.2F);
                    player.removePotionEffect(PotionEffectType.JUMP);
                }
            }, this.seconds * 20L);
        }
    }
}