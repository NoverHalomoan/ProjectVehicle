package com.bengkel.backendBengkel.base.configurasi;

import java.util.List;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bengkel.backendBengkel.base.untilize.EnviromentGetData;
import com.bengkel.backendBengkel.base.untilize.MethodArgumentResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class WebConfiguration {

    private final MethodArgumentResolver methodArgumentResolver;

    private EnviromentGetData enviromentGetData;

    public WebConfiguration(MethodArgumentResolver methodArgumentResolver, EnviromentGetData enviromentGetData) {
        this.methodArgumentResolver = methodArgumentResolver;
        this.enviromentGetData = enviromentGetData;
    }

    @Bean
    public WebMvcConfigurer ConfigureWeb() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry register) {
                register.addMapping("/**")
                        .allowedOrigins("http://127.0.0.1:5500", "http://localhost:3030", "https://editor.swagger.io")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*");
            }

            @Override
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
                resolvers.add(methodArgumentResolver);
            }
        };
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl senderMail = new JavaMailSenderImpl();
        senderMail.setHost(enviromentGetData.getEmailHost());
        senderMail.setPort(enviromentGetData.getEmailPort());
        senderMail.setUsername(enviromentGetData.getUserNameEmail());
        senderMail.setPassword(enviromentGetData.getPasswordEmail());

        Properties props = senderMail.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return senderMail;
    }

    @Bean
    public JedisConnectionFactory jedisConnection() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(enviromentGetData.getHostRedis(), enviromentGetData.getPortRedis());
        JedisClientConfiguration clientConfig = JedisClientConfiguration.builder().usePooling().build();
        return new JedisConnectionFactory(config, clientConfig);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        //konfiguration ObjectMapper dengan dukungan JavaTimeModule
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<>(mapper, Object.class);

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        //for hash
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }

    //configuration redis
    // @Bean
    // public TemplateEngine templateEngine() {
    //     ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    //     templateResolver.setPrefix("templates/");
    //     templateResolver.setPrefix(".html");
    //     templateResolver.setTemplateMode("HTML");
    //     templateResolver.setCharacterEncoding("UTF-8");
    //     TemplateEngine enginee = new TemplateEngine();
    //     enginee.setTemplateResolver(templateResolver);
    //     return enginee;
    // }
}
