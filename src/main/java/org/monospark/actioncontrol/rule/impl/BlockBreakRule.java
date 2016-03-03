package org.monospark.actioncontrol.rule.impl;

import java.util.Optional;

import org.monospark.actioncontrol.rule.ActionRule;
import org.monospark.actioncontrol.rule.filter.ActionFilterOption;
import org.monospark.actioncontrol.rule.filter.ActionFilterTemplate;
import org.monospark.spongematchers.type.MatcherType;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.item.inventory.ItemStack;

public final class BlockBreakRule extends ActionRule<ChangeBlockEvent.Break> {

    public BlockBreakRule() {
        super("breakBlock", ChangeBlockEvent.Break.class);
    }

    @Override
    protected ActionFilterTemplate createFilter() {
        return ActionFilterTemplate.builder()
                .addOption(new ActionFilterOption<BlockSnapshot, ChangeBlockEvent.Break>("blockIds",
                        MatcherType.BLOCK, e -> {
                            return e.getTransactions().get(0).getOriginal();
                        }))
                .addOption(new ActionFilterOption<Optional<ItemStack>, ChangeBlockEvent.Break>("toolIds",
                        MatcherType.optional(MatcherType.ITEM_STACK), (e) -> {
                                Optional<ItemStack> inHand = e.getCause().first(Player.class).get().getItemInHand();
                                return inHand;
                        })).build();
    }
}
