package com.github.akozubperez.math.arch.logging;

import com.github.akozubperez.math.arch.common.ReflectionUtil;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LoggingAdvisor extends AbstractPointcutAdvisor {

    private static final long serialVersionUID = 1L;

    private static final Advice ADVICE = new LoggingMethodValidationInterceptor();
    private static Pointcut pointcut;

    private transient ApplicationContext applicationContext;

    @AllArgsConstructor
    private static class LoggingMethodMatcherPointcut extends StaticMethodMatcherPointcut {

        private List<String> defaultPackages;

        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            boolean result = false;
            Optional<Logging> opt = ReflectionUtil.getAnnotation(method, targetClass, Logging.class);
            if (opt.isPresent()) {
                result = !opt.get().disabled();
            } else if (method.getDeclaringClass().getPackage() != null) {
                result = interfaceMatches(method);
            }
            return result;
        }

        private boolean interfaceMatches(Method method) {
            String candidatePackageName = method.getDeclaringClass().getPackage().getName();
            if (candidatePackageName != null) {
                for (String packageBase : defaultPackages) {
                    if (candidatePackageName.startsWith(packageBase + ".") || candidatePackageName.equals(packageBase)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    private static synchronized Pointcut getPointcutOrBuild(List<String> defaultPackages) {
        if (pointcut == null) {
            pointcut = new LoggingMethodMatcherPointcut(defaultPackages);
        }
        return pointcut;
    }

    @Override
    public Pointcut getPointcut() {
        return getPointcutOrBuild(AutoConfigurationPackages.get(applicationContext));
    }

    @Override
    public Advice getAdvice() {
        return ADVICE;
    }   
}
