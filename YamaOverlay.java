package net.runelite.client.plugins.yamaalert;

import net.runelite.api.Client;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;

@Singleton
public class YamaOverlay extends Overlay
{
    private final YamaPlugin plugin;

    @Inject
    public YamaOverlay(YamaPlugin plugin)
    {
        this.plugin = plugin;
        setPosition(OverlayPosition.TOP_LEFT);
        setLayer(OverlayLayer.ALWAYS_ON_TOP);
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (plugin.isYamaBelowThreshold())
        {
            graphics.setColor(Color.RED);
            graphics.fillRect(10, 10, 200, 50);
            graphics.setColor(Color.WHITE);
            graphics.drawString("⚠️ Yama below 32% HP!", 20, 40);
        }
        return null;
    }
}
