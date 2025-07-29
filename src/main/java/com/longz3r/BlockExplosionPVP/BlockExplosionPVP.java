package com.longz3r.BlockExplosionPVP;

import org.bukkit.damage.DamageType;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.minecart.ExplosiveMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class BlockExplosionPVP extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("BlockExplosionPVP enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("BlockExplosionPVP disabled.");
    }

    @EventHandler(ignoreCancelled = true)
    public void onCrystalDamage(EntityDamageByEntityEvent event) {
        if ((event.getDamager() instanceof EnderCrystal || event.getDamager() instanceof ExplosiveMinecart) && event.getEntity() instanceof org.bukkit.entity.Player) {
            getLogger().info("Cancelled damage from " + event.getDamager().getType() + " on " + event.getEntity().getName());
            event.setDamage(0.0);
        }
    }


    @EventHandler(ignoreCancelled = true)
    public void onBlockExplosionDamage(EntityDamageByBlockEvent event) {
        DamageType damageType = event.getDamageSource().getDamageType();
        if (damageType.equals(DamageType.BAD_RESPAWN_POINT) && event.getEntity() instanceof org.bukkit.entity.Player)  {
            event.setDamage(0.0);
            getLogger().info("Cancelled Bed/Respawn Anchor explosion damage on" + event.getEntity().getName());
        }
    }
}