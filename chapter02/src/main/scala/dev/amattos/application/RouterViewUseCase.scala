package dev.amattos.application.usecases

import dev.amattos.domain.Router
import dev.amattos.domain.RouterSearch

trait RouterViewUseCase:
  def getRouters(filter: Router => Boolean): List[Router]

class RouterViewService(routerViewRepository: RouterViewRepository) extends RouterViewUseCase:
  final def getRouters(filter: Router => Boolean): List[Router] =
    val routers = routerViewRepository.fetchRouters()
    RouterSearch.retrieveRouter(routers, filter)

trait RouterViewRepository:
  def fetchRouters(): List[Router]
