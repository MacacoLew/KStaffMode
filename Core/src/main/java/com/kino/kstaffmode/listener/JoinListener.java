package com.kino.kstaffmode.listener;

import com.kino.kstaffmode.KStaffMode;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

public class JoinListener implements Listener {

    private KStaffMode plugin;
    public JoinListener(KStaffMode plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(e.getPlayer().hasPermission("kstaffmode.data.read")){
            plugin.getPlayerDataManager().readData(e.getPlayer());
        }
        if(!e.getPlayer().hasPermission("kstaffmode.bypass.vanish")) {
            for (UUID uuid : plugin.getStaffModeManager().getVanished()) {
                e.getPlayer().hidePlayer(Bukkit.getPlayer(uuid));
            }
        }
    }
}
