package dev.amattos.infrastructure.adapters.input.cli

import dev.amattos.application.RouterNetworkUseCase
import dev.amattos.domain.Router
import dev.amattos.infrastructure.adapters.input.RouterNetworkAdapter

case class RouterNetworkCLIAdapter(routerNetworkUseCase: RouterNetworkUseCase) extends RouterNetworkAdapter:
  override def run: Router =
    val params = Map(
      "address"  -> "192.168.1.92",
      "routerId" -> "7d4e874d-0474-4410-b343-6e43d7699e29",
      "name"     -> "network-zero",
      "cidr"     -> "24"
    )

    addNetworkToRouter(params).toTry.get
