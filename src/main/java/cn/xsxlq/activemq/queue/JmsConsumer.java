package cn.xsxlq.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author wangjs6
 * @version 1.0
 * @Description:
 * @date: 2019/11/10 17:03
 */
public class JmsConsumer {
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
        // 6. 创建消费者
        MessageConsumer consumer = session.createConsumer(queue);
        // 7. 接收消息
        while (true){
            TextMessage textMessage = (TextMessage)consumer.receive();
            if(textMessage != null){
                System.out.println("consumer get :"+textMessage.getText());
            }else{
                break;
            }
        }
        // 8. 关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}
