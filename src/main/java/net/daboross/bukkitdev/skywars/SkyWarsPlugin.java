/*
 * Copyright (C) 2013 Dabo Ross <http://www.daboross.net/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.skywars;

import java.io.IOException;
import java.util.logging.Level;
import net.daboross.bukkitdev.skywars.api.SkyWars;
import net.daboross.bukkitdev.skywars.events.GameEventDistributor;
import net.daboross.bukkitdev.skywars.game.CurrentGames;
import net.daboross.bukkitdev.skywars.game.GameHandler;
import net.daboross.bukkitdev.skywars.game.GameIDHandler;
import net.daboross.bukkitdev.skywars.game.GameQueue;
import net.daboross.bukkitdev.skywars.listeners.CommandListener;
import net.daboross.bukkitdev.skywars.listeners.DeathStorage;
import net.daboross.bukkitdev.skywars.game.reactors.GameBroadcaster;
import net.daboross.bukkitdev.skywars.listeners.PortalListener;
import net.daboross.bukkitdev.skywars.listeners.QuitListener;
import net.daboross.bukkitdev.skywars.game.reactors.ResetInventoryHealth;
import net.daboross.bukkitdev.skywars.listeners.SpawnListener;
import net.daboross.bukkitdev.skywars.storage.LocationStore;
import net.daboross.bukkitdev.skywars.world.SkyWorldHandler;
import net.daboross.bukkitdev.skywars.world.Statics;
import net.daboross.bukkitdev.skywars.world.WorldUnzipper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.mcstats.MetricsLite;

/**
 *
 * @author daboross
 */
public class SkyWarsPlugin extends JavaPlugin implements SkyWars {

    private LocationStore locationStore;
    private GameQueue gameQueue;
    private CurrentGames currentGames;
    private GameHandler gameHandler;
    private GameIDHandler idHandler;
    private SkyWorldHandler worldHandler;
    private DeathStorage deathStorage;
    private GameBroadcaster broadcaster;
    private ResetInventoryHealth resetInventoryHealth;
    private GameEventDistributor distributor;
    private boolean enabledCorrectly = false;

    @Override
    public void onEnable() {
        setupMetrics();
        WorldUnzipper.WorldUnzipResult unzipResult = new WorldUnzipper(this).doWorldUnzip();
        switch (unzipResult) {
            case ALREADY_THERE:
                getLogger().log(Level.INFO, "World already created. Assuming valid.");
                startPlugin();
                break;
            case ERROR:
                getLogger().log(Level.INFO, "Error creating world. Please delete " + Statics.BASE_WORLD_NAME + " and restart server.");
                break;
            case CREATED:
                getLogger().log(Level.INFO, "Created world, resuming plugin start.");
                startPlugin();
                break;
            default:
                getLogger().log(Level.INFO, "Invalid return for unzipResult.");
        }
    }

    private void startPlugin() {
        currentGames = new CurrentGames();
        idHandler = new GameIDHandler();
        worldHandler = new SkyWorldHandler();
        broadcaster = new GameBroadcaster();
        resetInventoryHealth = new ResetInventoryHealth();
        locationStore = new LocationStore(this);
        gameQueue = new GameQueue(this);
        gameHandler = new GameHandler(this);
        deathStorage = new DeathStorage(this);
        distributor = new GameEventDistributor(this);
        new BukkitRunnable() {
            @Override
            public void run() {
                worldHandler.create();
            }
        }.runTask(this);
        new PermissionHandler("skywars").setupPermissions();
        setupCommands();
        PluginManager pm = getServer().getPluginManager();
        registerListeners(pm, new SpawnListener(), deathStorage,
                new QuitListener(this), new PortalListener(this),
                new CommandListener(this));
        enabledCorrectly = true;
    }

    private void registerListeners(PluginManager pm, Listener... listeners) {
        for (Object l : listeners) {
            if (l instanceof Listener) {
                pm.registerEvents((Listener) l, this);
            }
        }
    }

    @Override
    public void onDisable() {
        if (enabledCorrectly) {
            locationStore.save();
            idHandler.saveAndUnload(this);
            getLogger().log(Level.INFO, "SkyWars disabled successfully");
        } else {
            getLogger().log(Level.INFO, "SkyWars not disabling due to not being enabled successfully.");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage("SkyWars did not start correctly. Check console for errors.");
        return true;
    }

    private void setupMetrics() {
        MetricsLite metrics;
        try {
            metrics = new MetricsLite(this);
        } catch (IOException ex) {
            getLogger().log(Level.WARNING, "Unable to create metrics: {0}", ex.toString());
            return;
        }
        metrics.start();
    }

    private void setupCommands() {
        PluginCommand main = getCommand("skywars");
        if (main != null) {
            main.setExecutor(new CommandBase(this).getExecutor());
        }
    }

    @Override
    public LocationStore getLocationStore() {
        return locationStore;
    }

    @Override
    public GameQueue getGameQueue() {
        return gameQueue;
    }

    @Override
    public CurrentGames getCurrentGameTracker() {
        return currentGames;
    }

    @Override
    public GameHandler getGameHandler() {
        return gameHandler;
    }

    @Override
    public GameIDHandler getIDHandler() {
        return idHandler;
    }

    @Override
    public DeathStorage getAttackerStorage() {
        return deathStorage;
    }

    public SkyWorldHandler getWorldHandler() {
        return worldHandler;
    }

    public GameBroadcaster getBroadcaster() {
        return broadcaster;
    }

    public ResetInventoryHealth getResetInventoryHealth() {
        return resetInventoryHealth;
    }

    public GameEventDistributor getDistributor() {
        return distributor;
    }
}
