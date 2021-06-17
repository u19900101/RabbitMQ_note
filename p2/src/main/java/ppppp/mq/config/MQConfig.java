package ppppp.mq.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

        @Bean
        public Queue queue() {
            return new Queue("j-quence");
        }

        @Bean
        public Queue fanoutQuence() {
            return new Queue("j-fanout-quence");
        }
        /**
         * 声明交换机,fanout 类型
         */
        @Bean
        public FanoutExchange fanoutExchange() {
            FanoutExchange fanoutExchange = new FanoutExchange("f_ex");
            return fanoutExchange;
        }
        /**
         * 将队列和交换机绑定
         *
         * 交换机和队列的名称与方法名一致
         */
        @Bean
        public Binding bindingFanoutExchange(Queue fanoutQuence, FanoutExchange fanoutExchange) {
            return BindingBuilder.bind(fanoutQuence).to(fanoutExchange);
        }


        @Bean
        public Queue directQuence1() {
            return new Queue("d-quence1");
        }
        @Bean
        public Queue directQuence2() {
            return new Queue("d-quence2");
        }
        /**
         * 声明交换机,direct 类型
         */
        @Bean
        public DirectExchange directExchange() {
            DirectExchange directExchange = new DirectExchange("r_ex");
            return directExchange;
        }
        /**
         * 将队列和交换机绑定
         */
        @Bean
        public Binding bindingDirectExchange(Queue directQuence1, DirectExchange directExchange) {
            return BindingBuilder.bind(directQuence1).to(directExchange).with("rk1");
        }

        @Bean
        public Binding bindingDirectExchange2(Queue directQuence2, DirectExchange directExchange) {
            return BindingBuilder.bind(directQuence2).to(directExchange).with("rk2");
        }
}
