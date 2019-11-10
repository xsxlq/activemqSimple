package cn.xsxlq.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author wangjs6
 * @version 1.0
 * @Description:
 * @date: 2019/11/10 16:33
 */
public class Jmsproduce {
    private static final String ACTIVEMQ_URL = "tcp://localhost:61616";
    private static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {

        // 1. 创建连接工厂，使用默认用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        // 2. 由连接工厂创建连接
        Connection connection = activeMQConnectionFactory.createConnection();
        // 3. 启动
        connection.start();
        // 4. 创建会话(事务，签收)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        // 5. 创建目的地(以队列)
        Queue queue = session.createQueue(QUEUE_NAME);
        // 6. 创建消息生产者
        MessageProducer producer = session.createProducer(queue);
        // 7. 通过使用MessageProducer生产三条消息发送到MQ队列
        for(int i = 3;i < 6;i++){
            // 8.创建消息
            TextMessage textMessage = session.createTextMessage("msg:" + i);
            // 9. 发送
            producer.send(textMessage);
        }
        // 10. 关闭资源
        producer.close();
        session.close();
        connection.close();
        System.out.println("Msg send OK!");

    }
}
