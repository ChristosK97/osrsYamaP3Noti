@PluginDescriptor(name = "Yama Alert")
public class YamaPlugin extends Plugin
{
    @Inject private Client client;
    @Inject private OverlayManager overlayManager;
    @Inject private YamaPhaseOverlay overlay;

    private static final int YAMA_NPC_ID = 12345; // Replace with Yama's actual NPC ID
    private static final double HEALTH_THRESHOLD = 0.32;

    private NPC yamaNpc;

    @Override
    protected void startUp() throws Exception
    {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(overlay);
        yamaNpc = null;
    }

    @Subscribe
    public void onNpcSpawned(NpcSpawned event)
    {
        NPC npc = event.getNpc();
        if (npc.getId() == YAMA_NPC_ID)
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

    public boolean isPhaseThree()
    {
        // Replace with actual phase detection logic
        return yamaNpc != null && yamaNpc.getHealthRatio() > 0; // Placeholder
    }

    public boolean isHealthBelowThreshold()
    {
        if (yamaNpc == null) return false;
        int health = yamaNpc.getHealthRatio();
        int maxHealth = yamaNpc.getHealthScale(); // May need to calibrate this
        if (health <= 0 || maxHealth <= 0) return false;

        double percent = (double) health / maxHealth;
        return percent <= HEALTH_THRESHOLD;
    }
}
