package es.joshluq.cabishop.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.joshluq.cabishop.data.repository.CartDataRepository
import es.joshluq.cabishop.data.repository.ProductDataRepository
import es.joshluq.cabishop.domain.repository.CartRepository
import es.joshluq.cabishop.domain.repository.ProductRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindProductRepository(repository: ProductDataRepository): ProductRepository

    @Singleton
    @Binds
    abstract fun bindCartDataRepository(repository: CartDataRepository): CartRepository

}