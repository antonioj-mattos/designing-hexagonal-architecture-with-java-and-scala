package dev.amattos.application

import dev.amattos.Task

import dev.amattos.domain.IP
import dev.amattos.domain.NetworkOperation
import dev.amattos.domain.Router
import dev.amattos.domain.Router.RouterId



trait RouterNetworkUseCase:

  def addNetworkToRouter(routerId: RouterId, address: IP, name: String, cidr: Long): Task[Router]

class RouterNetworkService(routerNetworkRepository: RouterNetworkRepository) extends RouterNetworkUseCase:

  final def addNetworkToRouter(routerId: RouterId, address: IP, name: String, cidr: Long): Task[Router] =
    routerNetworkRepository.forUpdate(routerId): router =>
      for
        updatedRouter <- NetworkOperation.createNewNetwork(router, address, name, cidr)
        _             <- routerNetworkRepository.persistRouter(updatedRouter)
      yield updatedRouter

trait RouterNetworkRepository:
  def forUpdate(routerId: RouterId)(operation: Router => Task[Router]): Task[Router]
  def persistRouter(router: Router): Task[Router]
