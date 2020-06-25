package io.github.tastac.dungeonsmod.common.capability;

/**
 * @author CoffeeCatRailway
 * Created: 22/06/2020
 */
public interface ISoulsHandler {

    int getSouls();

    void setSouls(int souls);

    void addSouls(int amount);

    void removeSouls(int amount);
}
