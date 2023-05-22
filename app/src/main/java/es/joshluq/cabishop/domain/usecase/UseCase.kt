package es.joshluq.cabishop.domain.usecase

import kotlinx.coroutines.flow.Flow

interface UseCase<I, O> {

    operator fun invoke(params: I): Flow<Result<O>>

}