package me.mcsken.joinprotection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Objects;

public final class JoinProtection extends JavaPlugin implements Listener {
    private static JoinProtection instance;
    private boolean joinEnable;
    private int joinSeconds;
    private boolean joinMovement;
    private String joinMessageStart;
    private String joinMessageEnded;
    private boolean dimensionEnable;
    private int dimensionSeconds;
    private boolean dimensionMovement;
    private String dimensionMessageStart;
    private String dimensionMessageEnded;
    public static final int TICKS_PER_SECOND = 20;
    public static final float STANDARD_WALK_SPEED = 0.2F;

    public static JoinProtection getInstance() {
        return instance;
    }


    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);

        this.joinEnable = getConfig().getBoolean("joinEnableProtection");
        this.joinSeconds = getConfig().getInt("joinTimeProtection");
        this.joinMovement = getConfig().getBoolean("joinMovementAllow");
        this.joinMessageStart = ChatColor.translateAlternateColorCodes('&',
                Objects.requireNonNull(getConfig().getString("Messages.joinCooldownStart"))
                        .replace("%time%", Integer.toString(this.joinSeconds)));
        this.joinMessageEnded = ChatColor.translateAlternateColorCodes('&',
                Objects.requireNonNull(getConfig().getString("Messages.joinCooldownEnded"))
                        .replace("%time%", Integer.toString(this.joinSeconds)));

        this.dimensionEnable = getConfig().getBoolean("dimensionEnableProtection");
        this.dimensionSeconds = getConfig().getInt("dimensionTimeProtection");
        this.dimensionMovement = getConfig().getBoolean("dimensionMovementAllow");
        this.dimensionMessageStart = ChatColor.translateAlternateColorCodes('&',
                Objects.requireNonNull(getConfig().getString("Messages.dimensionCooldownStart"))
                        .replace("%time%", Integer.toString(this.dimensionSeconds)));
        this.dimensionMessageEnded = ChatColor.translateAlternateColorCodes('&',
                Objects.requireNonNull(getConfig().getString("Messages.dimensionCooldownEnded"))
                        .replace("%time%", Integer.toString(this.dimensionSeconds)));

        try {
            if (!Objects.requireNonNull(getConfig().getString("Version")).equalsIgnoreCase("0.1.1")) {
                getLogger().warning("Please delete config.yml to receive newest updates!");
            }
        } catch (NullPointerException e) {
            getLogger().severe("Please delete config.yml to receive newest updates!");
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        if (joinEnable && player.hasPermission("enableProtection.join") && !player.hasPermission("disableProtection.join")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance(), () -> {
                player.sendMessage(JoinProtection.this.joinMessageStart);
                player.setNoDamageTicks(JoinProtection.this.joinSeconds * TICKS_PER_SECOND);
                if (!JoinProtection.this.joinMovement) {
                    player.setWalkSpeed(0.0F);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 72000, 128, false, false, false));
                }
            });

            Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance(), () -> {
                player.sendMessage(JoinProtection.this.joinMessageEnded);
                if (!JoinProtection.this.joinMovement) {
                    player.setWalkSpeed(STANDARD_WALK_SPEED);
                    player.removePotionEffect(PotionEffectType.JUMP);
                }
            }, (long) this.joinSeconds * TICKS_PER_SECOND);
        }
    }
    @EventHandler
    public void onDimensionChange(PlayerChangedWorldEvent event) {
        final Player player = event.getPlayer();
        if (this.dimensionEnable && player.hasPermission("enableProtection.dimension") && !player.hasPermission("disableProtection.dimension")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance(), () -> {
                player.sendMessage(JoinProtection.this.dimensionMessageStart);
                player.setNoDamageTicks(JoinProtection.this.dimensionSeconds * TICKS_PER_SECOND);
                if (!JoinProtection.this.dimensionMovement) {
                    player.setWalkSpeed(0.0F);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 72000, 128, false, false, false));
                }
            });

            Bukkit.getScheduler().scheduleSyncDelayedTask(getInstance(), () -> {
                player.sendMessage(JoinProtection.this.dimensionMessageEnded);
                if (!JoinProtection.this.dimensionMovement) {
                    player.setWalkSpeed(STANDARD_WALK_SPEED);
                    player.removePotionEffect(PotionEffectType.JUMP);
                }
            }, (long) this.dimensionSeconds * TICKS_PER_SECOND);
        }
    }
}