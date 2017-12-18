package chapter13;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 代码清单 13-7 LogEventHandler
 *
 * @author <a href="mailto:norman.maurer@gmail.com">Norman Maurer</a>
 */
//扩展 SimpleChannelInboundHandler 以处理 LogEvent 消息
    @Slf4j
public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
        Throwable cause) throws Exception {
        //当异常发生时，打印栈跟踪信息，并关闭对应的 Channel
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, LogEvent event) throws Exception {
        log.info("read ...");
        //创建 StringBuilder，并且构建输出的字符串
        StringBuilder builder = new StringBuilder();
        builder.append(event.getReceivedTimestamp());
        builder.append(" [");
        builder.append(event.getSource().toString());
        builder.append("] [");
        builder.append(event.getLogfile());
        builder.append("] : ");
        builder.append(event.getMsg());
        //打印 LogEvent 的数据
        log.info(builder.toString());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("acc");
    }
}
