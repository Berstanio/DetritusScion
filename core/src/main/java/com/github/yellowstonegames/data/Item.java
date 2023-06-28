package com.github.yellowstonegames.data;

import com.github.tommyettinger.ds.ObjectFloatOrderedMap;
import com.github.tommyettinger.textra.Font;
import com.github.yellowstonegames.core.DescriptiveColor;
import com.github.yellowstonegames.core.FullPalette;
import com.github.yellowstonegames.glyph.GlyphActor;
import com.github.yellowstonegames.glyph.GlyphGrid;
import com.github.yellowstonegames.grid.Coord;
import com.github.yellowstonegames.util.RNG;
import com.github.yellowstonegames.util.Text;

public class Item implements HasStats {
    public static final String ITEM_CHARS = Text.USABLE_SYMBOLS;
    public long glyph;
    public transient GlyphActor actor;
    public String name;

    public ObjectFloatOrderedMap<String> baseStats =
            new ObjectFloatOrderedMap<>(COMBAT_STATS.length + VITAL_STATS.length + 2);
    {
        baseStats.putAll(COMBAT_STATS, COMBAT_VALUES);
        baseStats.putAll(VITAL_STATS, VITAL_VALUES);
    }

    public ObjectFloatOrderedMap<String> stats = new ObjectFloatOrderedMap<>(baseStats);

    @Override
    public ObjectFloatOrderedMap<String> getBaseStats() {
        return baseStats;
    }

    @Override
    public ObjectFloatOrderedMap<String> getStats() {
        return stats;
    }

    public Item() {
        this(Font.applyColor(ITEM_CHARS.charAt(RNG.rng.nextInt(ITEM_CHARS.length())),
                DescriptiveColor.lerpColors(
                        RNG.rng.randomElement(FullPalette.COLOR_WHEEL_PALETTE_MID), // extension method, woo!
                        DescriptiveColor.SILVER, 0.4f)),
                null, null, null, null);

    }
    public Item(long representation, GlyphGrid gg, String name, Coord position, ObjectFloatOrderedMap<String> statReplacements) {
        glyph = representation;
        actor = new GlyphActor(glyph, gg == null ? null : gg.getFont());
        if(position != null)
            actor.setLocation(position);
        else
            actor.setVisible(false);
        if(statReplacements != null)
        {
            baseStats.putAll(statReplacements);
            stats.putAll(statReplacements);
        }
        if(name != null)
            this.name = name;
        else {
            this.name = Text.thesaurus.process("weapon`noun` of ancient_egyptian`gen`");
        }
    }

    @Override
    public String toString() {
        return (char)glyph + " " + name;
    }

}
