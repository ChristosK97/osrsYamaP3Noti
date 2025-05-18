package net.runelite.client.plugins.yamaalert;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.events.NpcSpawned;
import net.runelite.api.events.NpcDespawned;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

@PluginDescriptor(name = "Yama Alert")
public class YamaPlugin extends Plugin
{
    @Inject private Client client;
    @Inject private OverlayManager overlayManager;
    @Inject private YamaOverlay overlay;

    private static final int YAMA_PHASE3_NPC_ID = 14176;
    private static final double HEALTH_THRESHOLD = 0.32;

    private NPC yamaNpc;

    @Override
    protected void startUp()
    {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown()
    {
        overlayManager.remove(overlay);
        yamaNpc = null;
    }

    @Subscribe
    public void onNpcSpawned(NpcSpawned event)
    {
        NPC npc = event.getNpc();
        if (npc.getId() == YAMA_PHASE3_NPC_ID)
        {
            yamaNpc = npc;
        }
    }

    @Subscribe
    public void onNpcDespawned(NpcDespawned event)
    {
        if (event.getNpc() == yamaNpc)
        {
            yamaNpc = null;
        }
    }

    public boolean isYamaBelowThreshold()
    {
        if (yamaNpc == null) return false;

        int health = yamaNpc.getHealthRatio();
        int maxHealth = yamaNpc.getHealthScale(); 

        if (health <= 0 || maxHealth <= 0) return false;

        double hpPercent = (double) health / maxHealth;
        return hpPercent <= HEALTH_THRESHOLD;
    }
}
