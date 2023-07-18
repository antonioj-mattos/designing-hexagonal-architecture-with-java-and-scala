package dev.amattos.infrastructure.adapters.output.file

import dev.amattos.application.RouterNetworkRepository

import dev.amattos.domain.Router
import dev.amattos.domain.Router.RouterId
import dev.amattos.domain.RouterType
import dev.amattos.domain.Switch
import dev.amattos.domain.Switch.SwitchId
import dev.amattos.domain.SwitchType
import dev.amattos.domain.IP

import dev.amattos.Task

object RouterNetworkInMemoryRepository extends RouterNetworkRepository:

  private var routers: List[Router] = List(
    Router(
      RouterId("7d4e874d-0474-4410-b343-6e43d7699e29"),
      RouterType.EDGE,
      Switch(
        SwitchId("88f02d6c-7194-4bfc-8e40-f52e048eb242"),
        SwitchType.LAYER3,
        List.empty,
        IP.unsafeApply("192.168.1.10", "IPV4")
      )
    )
  )

  final def forUpdate(routerId: RouterId)(operation: Router => Task[Router]): Task[Router] =
    synchronized:
      val router = routers.filter(_.routerId == routerId).head
      operation(router)

  final def persistRouter(router: Router): Task[Router] =
    synchronized:
      routers = routers.filter(_.routerId != router.routerId)
      routers = router +: routers
      Right(router)
