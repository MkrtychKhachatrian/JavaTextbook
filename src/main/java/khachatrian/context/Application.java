package khachatrian.context;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan(basePackages = {"khachatrian.controller","khachatrian.service","khachatrian.repository" })
@Import(HibernateConfCommon.class)
public class Application {

}
