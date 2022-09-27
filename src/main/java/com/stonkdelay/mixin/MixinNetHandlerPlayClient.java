package com.stonkdelay.mixin;

import com.stonkdelay.ChunkUpdateEvent;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {
    @Inject(method = "handleChunkData", at = @At("TAIL"))
    private void onChunkDataUpdate(S21PacketChunkData packetIn, CallbackInfo ci) {
        MinecraftForge.EVENT_BUS.post(new ChunkUpdateEvent(packetIn));
    }
}
