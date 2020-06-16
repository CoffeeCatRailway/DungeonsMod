package io.github.tastac.dungeonsmod.common.item;

import io.github.tastac.dungeonsmod.common.entity.ShieldingTotemEntity;

import java.awt.*;

/**
 * @author CoffeeCatRailway
 * Created: 11/06/2020
 */
public class ShieldingTotem extends TotemArtifact {

    public ShieldingTotem(Properties prop) {
        super(prop, 5f, 25f, 4f, new Color(0xffb100), ShieldingTotemEntity::new);
        this.showDuration(true);
    }
}
