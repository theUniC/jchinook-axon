package org.chinook.jchinook.infrastructure.delivery.spring.config

import org.axonframework.commandhandling.AsynchronousCommandBus
import org.axonframework.commandhandling.CommandBus
import org.axonframework.common.transaction.TransactionManager
import org.axonframework.config.Configurer
import org.axonframework.config.ConfigurerModule
import org.axonframework.messaging.interceptors.CorrelationDataInterceptor
import org.axonframework.metrics.GlobalMetricRegistry
import org.axonframework.tracing.SpanFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AxonConfig {
    @Bean
    fun asynchronousCommandBus(
        transactionManager: TransactionManager,
        metricRegistry: GlobalMetricRegistry,
        spanFactory: SpanFactory
    ): CommandBus = AsynchronousCommandBus.builder()
        .transactionManager(transactionManager)
        .messageMonitor(metricRegistry.registerCommandBus("commandBus"))
        .spanFactory(spanFactory)
        .build()

    @Bean
    fun commandBusCorrelationConfigurerModule(): ConfigurerModule {
        return ConfigurerModule { configurer: Configurer ->
            configurer.onInitialize { config ->
                config.commandBus().registerHandlerInterceptor(
                    CorrelationDataInterceptor(config.correlationDataProviders())
                )
            }
        }
    }
}
