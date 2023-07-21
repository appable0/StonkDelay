package com.stonkdelay

import net.minecraft.block.state.IBlockState
import net.minecraft.item.ItemStack
import net.minecraft.network.play.server.S21PacketChunkData
import net.minecraft.util.BlockPos
import net.minecraft.util.EnumFacing
import net.minecraftforge.fml.common.eventhandler.Cancelable
import net.minecraftforge.fml.common.eventhandler.Event

class BlockBreakEvent(val pos: BlockPos, val side: EnumFacing): Event()

@Cancelable
class BlockChangeEvent(val pos: BlockPos, val state: IBlockState): Event()

class ChunkUpdateEvent(val packet: S21PacketChunkData): Event()

class BlockPlaceEvent(val pos: BlockPos, val stack: ItemStack?): Event()