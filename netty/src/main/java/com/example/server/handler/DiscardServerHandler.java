package com.example.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

/**
 * @author wangmingsen
 * @create 2018-10-23-下午8:08
 **/


//ChannelInboundHandlerAdapter实现自ChannelInboundHandler
//ChannelInboundHandler提供了不同的事件处理方法你可以重写
public class DiscardServerHandler extends SimpleChannelInboundHandler {

    //用于接收从客户端接收的信息

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        //Discard the received data silently
        //ByteBuf是一个引用计数对象实现ReferenceCounted，
        // 他就是在有对象引用的时候计数+1，无的时候计数-1，当为0对象释放内存
        ByteBuf in = (ByteBuf) msg;
        try {
            while (in.isReadable()) {
                System.out.println((char) in.readByte());
                System.out.flush();
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

        System.out.println(o);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
