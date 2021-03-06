package com.kino.kstaffmode.listener;

import com.kino.kore.utils.messages.MessageUtils;
import com.kino.kstaffmode.managers.files.PlayerDataManager;
import com.kino.kstaffmode.managers.staffmode.StaffModeManager;
import fr.minuskube.netherboard.Netherboard;
import fr.minuskube.netherboard.bukkit.BPlayerBoard;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@AllArgsConstructor
public class LeaveListener implements Listener {

    private StaffModeManager staffModeManager;
    private FileConfiguration messages;
    private FileConfiguration config;
    private PlayerDataManager playerDataManager;

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        if(staffModeManager.isFrozen(e.getPlayer())) {
            for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                if(p.hasPermission("kstaffmode.freezedisconnect")) {
                    MessageUtils.sendMessage(p, messages.getString("frozenDisconnect").replace("<player>", e.getPlayer().getName()));
                }
            }
            staffModeManager.getFrozen().remove(e.getPlayer().getUniqueId());
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), config.getString("frozenDisconnect").replace("<player>", e.getPlayer().getName()));
        }
        BPlayerBoard board = Netherboard.instance().getBoard(e.getPlayer());
        if(board !=null && board.equals(staffModeManager.getBoard())) {
            board.delete();
            staffModeManager.resetScoreboard(e.getPlayer());
        }
        if(e.getPlayer().hasPermission("kstaffmode.data.save")){
            playerDataManager.savePlayerData(e.getPlayer());
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent e){
        BPlayerBoard board = Netherboard.instance().getBoard(e.getPlayer());
        if(board !=null && board.equals(staffModeManager.getBoard())) {
            board.delete();
            staffModeManager.resetScoreboard(e.getPlayer());
        }
        if(e.getPlayer().hasPermission("kstaffmode.data.save")){
            playerDataManager.savePlayerData(e.getPlayer());
        }

        if(staffModeManager.isFrozen(e.getPlayer())) {
            for(Player p : Bukkit.getServer().getOnlinePlayers()) {
                if(p.hasPermission("kstaffmode.freezedisconnect")) {
                    MessageUtils.sendMessage(p, messages.getString("frozenDisconnect").replace("<player>", e.getPlayer().getName()));
                }
            }
            staffModeManager.getFrozen().remove(e.getPlayer().getUniqueId());
            Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), config.getString("frozenDisconnect").replace("<player>", e.getPlayer().getName()));
        }
    }
}
