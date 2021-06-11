package org.josesilveiraa.manhunt.util;

import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    private final List<Component> lore = new ArrayList<>();
    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material, 1);
        this.meta = this.item.getItemMeta();
    }

    public ItemBuilder(Material material, int amount) {
        this.item = new ItemStack(material, amount);
        this.meta = this.item.getItemMeta();
    }

    public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int unsafeLevel) {
        this.item.addUnsafeEnchantment(enchantment, unsafeLevel);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        this.item.removeEnchantment(enchantment);
        return this;
    }

    public ItemBuilder setCompassTarget(Player player) {
        setCompassTarget(player.getLocation());
        return this;
    }

    public void setCompassTarget(Location location) {
        if(this.meta instanceof CompassMeta) {
            CompassMeta meta = (CompassMeta) this.meta;
            meta.setLodestone(location);
            this.item.setItemMeta(this.meta);
        }
    }

    public ItemBuilder name(String name) {
        this.meta.displayName(Component.text(name));
        this.item.setItemMeta(this.meta);
        return this;
    }

    public ItemBuilder flags(ItemFlag... itemFlags) {
        this.item.addItemFlags(itemFlags);
        return this;
    }

    public ItemBuilder lore(String... lore) {

        for(String s : lore) {
            this.lore.add(Component.text(s));
        }
        this.meta.lore(this.lore);
        this.item.setItemMeta(this.meta);
        return this;
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        this.item.addEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder setLeatherArmorColor(Color color) {
        try {
            LeatherArmorMeta meta = (LeatherArmorMeta) this.item.getItemMeta();
            meta.setColor(color);
        } catch (ClassCastException ignored) {}
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.item.setAmount(amount);
        return this;
    }

    public ItemStack build() {
        return this.item;
    }

}
