package com.xiaoyang.netty.echo.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author WXY
 * @date 2018/8/31 17:24
 */
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //默认丢弃服务
        System.out.println("msg----" + msg);
        System.out.println(((ByteBuf) msg).capacity());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //引发异常关闭连接
        cause.printStackTrace();
        ctx.close();
    }

}
