package dev.amattos.infrastructure.adapters.input

import dev.amattos.application.RouterNetworkUseCase

import dev.amattos.domain.Router
import dev.amattos.domain.Router.RouterId
import dev.amattos.domain.IP

import dev.amattos.Task

trait RouterNetworkAdapter:
  protected def routerNetworkUseCase: RouterNetworkUseCase

  def run: Router

  private[adapters] def addNetworkToRouter(params: Map[String, String]): Task[Router] =
    IP(params("address")).flatMap: address =>
      val routerId = RouterId(params("routerId"))
      val name     = params("name")
      val cidr     = params("cidr").toLong
      routerNetworkUseCase.addNetworkToRouter(routerId, address, name, cidr)
