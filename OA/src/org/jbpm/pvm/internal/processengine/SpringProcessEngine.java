package org.jbpm.pvm.internal.processengine;  
  
import java.util.Properties;  
  
import org.hibernate.cfg.Configuration;  
import org.springframework.context.ApplicationContext;  
import org.springframework.context.annotation.Bean;  
import org.springframework.context.annotation.DependsOn;  
import org.springframework.context.support.ClassPathXmlApplicationContext;  
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;  
  
import org.jbpm.api.ProcessEngine;  
import org.jbpm.internal.log.Log;  
import org.jbpm.pvm.internal.cfg.ConfigurationImpl;  
import org.jbpm.pvm.internal.env.EnvironmentImpl;  
import org.jbpm.pvm.internal.env.PvmEnvironment;  
import org.jbpm.pvm.internal.env.SpringContext;  
import org.jbpm.pvm.internal.wire.descriptor.ProvidedObjectDescriptor;  
  
/** 
 * this environment factory will see only the singleton beans. 
 *  
 * The created {@link SpringEnvironment}s will see the prototype beans and it will cache them. 
 *  
 * @author Andries Inze 
 */  
public class SpringProcessEngine extends ProcessEngineImpl {  
  
    private static final Log log = Log.getLog(SpringProcessEngine.class.getName());  
  
    private static final long serialVersionUID = 1L;  
  
    private ApplicationContext applicationContext;  
  
    public static ProcessEngine create(ConfigurationImpl configuration) {  
        SpringProcessEngine springProcessEngine = null;  
  
        ApplicationContext applicationContext = null;  
        if (configuration.isInstantiatedFromSpring()) {  
            applicationContext = (ApplicationContext) configuration.getApplicationContext();  
  
            springProcessEngine = new SpringProcessEngine();  
            springProcessEngine.applicationContext = applicationContext;  
            springProcessEngine.initializeProcessEngine(configuration);  
  
            LocalSessionFactoryBean localSessionFactoryBean = springProcessEngine.get(LocalSessionFactoryBean.class);  
            Configuration hibernateConfiguration = localSessionFactoryBean.getConfiguration();  
            springProcessEngine.processEngineWireContext.getWireDefinition().addDescriptor(new ProvidedObjectDescriptor(hibernateConfiguration, true));  
  
            springProcessEngine.checkDb(configuration);  
  
        } else {  
            String springCfg = (String) configuration.getProcessEngineWireContext().get("spring.cfg");  
            if (springCfg == null) {  
                springCfg = "applicationContext.xml";  
            }  
            applicationContext = new ClassPathXmlApplicationContext(springCfg);  
            springProcessEngine = (SpringProcessEngine) applicationContext.getBean("processEngine");  
        }  
  
        return springProcessEngine;  
    }  
  
    public EnvironmentImpl openEnvironment() {  
        PvmEnvironment environment = new PvmEnvironment(this);  
  
        if (log.isTraceEnabled())  
            log.trace("opening jbpm-spring" + environment);  
  
        environment.setContext(new SpringContext(applicationContext));  
  
        installAuthenticatedUserId(environment);  
        installProcessEngineContext(environment);  
        installTransactionContext(environment);  
  
        return environment;  
    }  
  
    @SuppressWarnings("unchecked")  
    @Override  
    public <T> T get(Class<T> type) {  
        T candidateComponent = super.get(type);  
  
        if (candidateComponent != null) {  
            return candidateComponent;  
        }  
  
        String[] names = applicationContext.getBeanNamesForType(type);  
  
        if (names.length >= 1) {  
  
            if (names.length > 1 && log.isWarnEnabled()) {  
                log.warn("Multiple beans for type " + type + " found. Returning the first result.");  
            }  
  
            return (T) applicationContext.getBean(names[0]);  
        }  
  
        return null;  
    }  
  
    @Override  
    public Object get(String key) {  
        if (applicationContext.containsBean(key)) {  
            return applicationContext.getBean(key);  
        }  
  
        return super.get(key);  
    }  
}  
