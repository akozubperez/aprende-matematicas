package com.github.akozubperez.math.arch.logging;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class LoggingMethodValidationInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        Logging loggingRule = mi.getMethod().getAnnotation(Logging.class);
        Logger logger = LoggerFactory.getLogger(mi.getThis().getClass());
        boolean logErrors = false;
        boolean logParameters = true;
        boolean logReturns = true;
        String methodName = mi.getMethod().getName();
        if (loggingRule != null) {
            logErrors = loggingRule.logErrors();
            logParameters = loggingRule.logParameters();
            logReturns = loggingRule.logReturns();
        }
        try {
            int numParameters = mi.getMethod().getParameterCount();
            logger.debug("{} start", methodName);
            if (logParameters && numParameters > 0) {
                logger.trace("{} start ({})", methodName, mi.getArguments());
            }
            long time = System.currentTimeMillis();
            Object result = mi.proceed();
            time = System.currentTimeMillis() - time;
            if (logReturns && mi.getMethod().getReturnType() != void.class) {
                logger.trace("{} returns: {}", methodName, result);
            }
            logger.debug("{} finish in {} ms", methodName, time);
            return result;
        } catch (Exception e) {
            if (logErrors) {
                logger.error(methodName + " throws exception", e);
            }
            throw e;
        }
    }
}

