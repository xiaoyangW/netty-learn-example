package com.xiaoyang.netty.echo.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;

/**
 * @author WXY
 * @date 2018/11/14 20:37
 */
@ChannelHandler.Sharable
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        final ByteBuf time = ctx.alloc().buffer(4);
        long l = System.currentTimeMillis() / 1000L + 2208988800L;
        System.out.println(l);
        time.writeInt((int) l);
        final ChannelFuture f = ctx.writeAndFlush(time);
        f.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) {
                assert f == future;
                ctx.close();
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
