package com.longz3r.BlockExplosionPVP;

import org.bukkit.damage.DamageType;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public final class BlockExplosionPVP extends JavaPlugin implements Listener {
    private FileConfiguration config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("BlockExplosionPVP enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("BlockExplosionPVP disabled.");
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof org.bukkit.entity.Player) {
            getLogger().warning(event.getDamageSource().getCausingEntity().getName());
            if ((event.getDamager() instanceof EnderCrystal)) {
                

                if (config.getBoolean("EndCrystalDamageCausingPlayer", false)) {
                    if (!(event.getDamageSource().getCausingEntity().getName() == event.getEntity().getName())) {
                        // If the EnderCrystal is not caused by the player, apply the damage multiplier
                        event.setDamage(event.getDamage() * config.getDouble("EndCrystalDamageMultiplier", 0));
                        if (config.getBoolean("LogCancelledDamageToConsole", true)) {
                            getLogger().info(event.getDamage() * config.getDouble("EndCrystalDamageMultiplier", 0) + " damage from " + event.getDamager().getType() + " was applied on " + event.getEntity().getName());
                        }
                    } else {
                        getLogger().info(event.getEntity().getName() + " caused the EnderCrystal explosion, original damage applied.");
                    }
                } else {
                    event.setDamage(event.getDamage() * config.getDouble("EndCrystalDamageMultiplier", 0));
                    if (config.getBoolean("LogCancelledDamageToConsole", true)) {
                        getLogger().info(event.getDamage() * config.getDouble("EndCrystalDamageMultiplier", 0) + " damage from " + event.getDamager().getType() + " was applied on " + event.getEntity().getName());
                    }
                }

            } else if ((event.getDamager() instanceof ExplosiveMinecart)) {
                event.setDamage(event.getDamage() * config.getDouble("TNTMinecartDamageMultiplier", 0));
                if (config.getBoolean("LogCancelledDamageToConsole", true)) {
                    getLogger().info(event.getDamage() * config.getDouble("TNTMinecartDamageMultiplier", 0) + " damage from " + event.getDamager().getType() + " was applied on " + event.getEntity().getName());
                }
            }
        }
    }


    @EventHandler(ignoreCancelled = true)
    public void onBlockExplosionDamage(EntityDamageByBlockEvent event) {
        DamageType damageType = event.getDamageSource().getDamageType();
        if (damageType.equals(DamageType.BAD_RESPAWN_POINT) && event.getEntity() instanceof org.bukkit.entity.Player)  {
            event.setDamage(event.getDamage() * config.getDouble("BlockExplosionDamageMultiplier", 0));
            if (config.getBoolean("LogCancelledDamageToConsole", true)) {
                getLogger().info(event.getDamage() * config.getDouble("BlockExplosionDamageMultiplier", 0) + " Bed/Respawn Anchor explosion damage from was applied on " + event.getEntity().getName());
            }
        }
    }
}