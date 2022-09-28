package com.stonkdelay.features

import com.stonkdelay.BlockBreakEvent
import com.stonkdelay.BlockChangeEvent
import com.stonkdelay.ChunkUpdateEvent
import com.stonkdelay.StonkDelay
import com.stonkdelay.StonkDelay.Companion.mc
import com.stonkdelay.utils.Location
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.util.BlockPos
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.event.world.WorldEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.gameevent.TickEvent

object Delay {
    private val blocks = mutableMapOf<BlockPos, BlockData>()

    // Adds mined block to block map
    @SubscribeEvent
    fun onBlockBreak(event: BlockBreakEvent) {
        if (!Location.inSkyblock || !StonkDelay.config.settings.enabled) return
        val state = mc.theWorld.getBlockState(event.pos)
        blocks[event.pos] = BlockData(state, System.currentTimeMillis(), false)
    }

    // Queues and cancels blocks changed from PacketBlockChange and PacketMultiBlockChange
    @SubscribeEvent
    fun onBlockChange(event: BlockChangeEvent) {
        if (!Location.inSkyblock || !StonkDelay.config.settings.enabled) return
        blocks[event.pos]?.let {
            it.queued = true
            event.isCanceled = true
        }
    }

    // Cancel block changes from PacketChunkData
    @SubscribeEvent
    fun onChunkUpdate(event: ChunkUpdateEvent) {
        if (!Location.inSkyblock || !StonkDelay.config.settings.enabled) return
        val minX = event.packet.chunkX shl 4
        val minZ = event.packet.chunkZ shl 4
        val maxX = minX + 15
        val maxZ = minZ + 15

        blocks.forEach {
            if (it.key.x in minX..maxX && it.key.z in minZ..maxZ) {
                mc.theWorld.setBlockState(it.key, Blocks.air.defaultState)
            }
        }
    }

    // Resets expired queued blocks
    @SubscribeEvent
    fun onTick(event: TickEvent) {
        if (!Location.inSkyblock
            || !StonkDelay.config.settings.enabled
            || event.phase != TickEvent.Phase.START
        ) return
        val currentTime = System.currentTimeMillis()
        blocks.keys.removeAll {
            val blockData = blocks[it]!!
            val timeExisted = currentTime - blockData.time
            val shouldResetBlock = blockData.queued
                    && timeExisted >= StonkDelay.config.settings.delay
            if (shouldResetBlock) {
                mc.theWorld.setBlockState(it, blockData.state)
            }
            shouldResetBlock || (timeExisted >= 10000)
        }
    }

    // Stops tracking blocks when player right-clicks on adjacent block faces.
    @SubscribeEvent
    fun onPlayerInteract(event: PlayerInteractEvent) {
        if (!Location.inSkyblock
            || !StonkDelay.config.settings.enabled
            || event.action != PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK
        ) return
        val affectedPos = event.pos.offset(event.face) ?: return
        blocks.remove(affectedPos)
    }

    // Resets block list when world changes.
    @SubscribeEvent
    fun onWorldUnload(event: WorldEvent.Unload) {
        blocks.clear()
    }

    data class BlockData(val state: IBlockState, val time: Long, var queued: Boolean)
}