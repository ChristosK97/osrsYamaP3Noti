@Singleton
public class YamaPhaseOverlay extends Overlay
{
    private final YamaPlugin plugin;
    private final Client client;

    @Inject
    public YamaPhaseOverlay(YamaPlugin plugin, Client client)
    {
        this.plugin = plugin;
        this.client = client;
        setPosition(OverlayPosition.TOP_LEFT);
        setLayer(OverlayLayer.ALWAYS_ON_TOP);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (plugin.isPhaseThree() && plugin.isHealthBelowThreshold())
        {
            graphics.setColor(Color.RED);
            graphics.fillRect(10, 10, 200, 50);
            graphics.setColor(Color.WHITE);
            graphics.drawString("Yama phase 3 - WATCH OUT!", 20, 40);
        }
        return null;
    }
}
