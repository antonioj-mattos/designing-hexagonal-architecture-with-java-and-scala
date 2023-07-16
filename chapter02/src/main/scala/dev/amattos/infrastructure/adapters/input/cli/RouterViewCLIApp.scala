package dev.amattos.infrastructure.adapters

import dev.amattos.application.usecases.RouterViewUseCase
import dev.amattos.application.usecases.RouterViewService
import dev.amattos.application.usecases.RouterViewRepository
import dev.amattos.infrastructure.adapters.output.file.RouterViewFileRepository

import dev.amattos.domain.Router
import dev.amattos.domain.RouterType

class RouterViewCLIApp:
  val routerView: RouterViewUseCase = RouterViewService(RouterViewFileRepository)

  def obtainRelatedRouters(routerType: String): List[Router] =
    routerView.getRouters(Router.filterRouterByType(RouterType.valueOf(routerType)))
