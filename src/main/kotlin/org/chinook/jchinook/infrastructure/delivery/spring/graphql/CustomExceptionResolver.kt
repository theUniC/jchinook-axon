package org.chinook.jchinook.infrastructure.delivery.spring.graphql

import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import graphql.schema.DataFetchingEnvironment
import org.axonframework.modelling.command.AggregateNotFoundException
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter
import org.springframework.graphql.execution.ErrorType
import org.springframework.stereotype.Component
import java.util.concurrent.ExecutionException

@Component
class CustomExceptionResolver : DataFetcherExceptionResolverAdapter() {
    override fun resolveToSingleError(ex: Throwable, env: DataFetchingEnvironment): GraphQLError? =
        when (ex) {
            is ExecutionException -> if (ex.cause != null) resolveToSingleError(ex.cause!!, env) else null
            is AggregateNotFoundException -> GraphqlErrorBuilder.newError()
                .errorType(ErrorType.NOT_FOUND)
                .message(ex.message)
                .path(env.executionStepInfo.path)
                .location(env.field.sourceLocation)
                .build()

            else -> GraphqlErrorBuilder.newError().errorType(ErrorType.INTERNAL_ERROR).message(ex.message).build()
        }
}
