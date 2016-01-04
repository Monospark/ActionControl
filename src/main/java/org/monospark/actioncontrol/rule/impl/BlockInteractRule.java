package org.monospark.actioncontrol.rule.impl;

import java.util.Optional;

import org.monospark.actioncontrol.matcher.MatcherType;
import org.monospark.actioncontrol.rule.ActionRule;
import org.monospark.actioncontrol.rule.filter.ActionFilterOption;
import org.monospark.actioncontrol.rule.filter.ActionFilterTemplate;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

public final class BlockInteractRule<E extends InteractBlockEvent> extends ActionRule<E> {

    public BlockInteractRule(String name, Class<E> eventClass) {
        super(name, eventClass);
    }

    @Override
    protected ActionFilterTemplate createFilter() {
        return ActionFilterTemplate.builder()
                .addOption(new ActionFilterOption<BlockSnapshot, InteractBlockEvent>("blockIds",
                        MatcherType.BLOCK, e -> e.getTargetBlock()))
                .addOption(new ActionFilterOption<ItemStackSnapshot, InteractBlockEvent>("itemIds",
                        MatcherType.ITEM_AND_HAND, (e) -> {
                                Optional<ItemStack> inHand = e.getCause().first(Player.class).get().getItemInHand();
                                return inHand.isPresent() ? inHand.get().createSnapshot() : null;
                        })).build();
    }
}