package com.emre.skydata.utils;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmreUtils {

    public static NBTTagCompound getNBT (Entity entity) {

        net.minecraft.server.v1_16_R3.Entity nmsEntity = ((CraftEntity)entity).getHandle();

        NBTTagCompound nbt = new NBTTagCompound();

        nmsEntity.d(nbt);

        return nbt;
    }

    public static void setNBT (Entity entity, NBTTagCompound nbt) {

        net.minecraft.server.v1_16_R3.Entity nmsEntity = ((CraftEntity)entity).getHandle();

        // One of these should work, there are no other methods
        nmsEntity.d(nbt.clone());

        ((CraftEntity)entity).setHandle(nmsEntity);
    }

    public static Map<String, Double> sortByValue(Map<String, Double> unsortMap, final boolean order)
    {
        List<Map.Entry<String, Double>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        list.sort((o1, o2) -> order ? o1.getValue().compareTo(o2.getValue()) == 0
                ? o1.getKey().compareTo(o2.getKey())
                : o1.getValue().compareTo(o2.getValue()) : o2.getValue().compareTo(o1.getValue()) == 0
                ? o2.getKey().compareTo(o1.getKey())
                : o2.getValue().compareTo(o1.getValue()));
        return list.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

    }
}
