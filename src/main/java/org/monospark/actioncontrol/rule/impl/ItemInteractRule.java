package org.monospark.actioncontrol.rule.impl;

import java.util.Optional;

import org.monospark.actioncontrol.rule.ActionRule;
import org.monospark.actioncontrol.rule.filter.ActionFilterOption;
import org.monospark.actioncontrol.rule.filter.ActionFilterTemplate;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.item.inventory.ItemStack;

public class ItemInteractRule extends ActionRule<InteractBlockEvent.Secondary> {

    public ItemInteractRule() {
        super("interact", InteractBlockEvent.Secondary.class);
    }

    @Override
    protected boolean acceptsEvent(InteractBlockEvent.Secondary event) {
        return true;
    }

    @Override
    protected ActionFilterTemplate createFilter() {
        return ActionFilterTemplate.builder()
                .addOption(new ActionFilterOption<Optional<ItemStack>, InteractBlockEvent>("item",
                        MatcherType.optional(MatcherType.ITEM_STACK), (e) -> {
                                Optional<ItemStack> inHand = e.getCause().first(Player.class).get().getItemInHand();
                                return inHand;
                        })).build();
    }

}
