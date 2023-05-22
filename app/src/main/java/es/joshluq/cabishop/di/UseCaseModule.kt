package es.joshluq.cabishop.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import es.joshluq.cabishop.domain.model.Cart
import es.joshluq.cabishop.domain.model.Product
import es.joshluq.cabishop.domain.usecase.AddProductUseCase
import es.joshluq.cabishop.domain.usecase.ClearProductsUseCase
import es.joshluq.cabishop.domain.usecase.GetCartUseCase
import es.joshluq.cabishop.domain.usecase.GetProductsUseCase
import es.joshluq.cabishop.domain.usecase.RemoveProductUseCase
import es.joshluq.cabishop.domain.usecase.UseCase
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {

    @Binds
    @Named("getProductsUseCase")
    abstract fun bindGetProductsUseCase(useCase: GetProductsUseCase): UseCase<Unit, List<Product>>

    @Binds
    @Named("getCartUseCase")
    abstract fun bindGetCartUseCase(useCase: GetCartUseCase): UseCase<Unit, Cart>

    @Binds
    @Named("addProductUseCase")
    abstract fun bindAddProductUseCase(useCase: AddProductUseCase): UseCase<AddProductUseCase.Params, Unit>

    @Binds
    @Named("removeProductUseCase")
    abstract fun bindRemoveProductUseCase(useCase: RemoveProductUseCase): UseCase<RemoveProductUseCase.Params, Unit>


    @Binds
    @Named("clearProductsUseCase")
    abstract fun bindClearProductsUseCase(useCase: ClearProductsUseCase): UseCase<Unit, Unit>
}