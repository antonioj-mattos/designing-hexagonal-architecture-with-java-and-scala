package dev.amattos.infrastructure.adapters.output.file

import dev.amattos.application.usecases.RouterViewRepository
import dev.amattos.domain.Router
import dev.amattos.domain.Router.RouterId
import dev.amattos.domain.RouterType

import scala.io.Source
import scala.util.Try
import scala.util.Using

object RouterViewFileRepository extends RouterViewRepository:

  final def fetchRouters(): List[Router] = readFromFile()

  private def readFromFile(): List[Router] =
    val routers = Using(Source.fromResource("routers.txt")) { source =>
      source.getLines.map(parseRouter).toList
    }
    routers.get

  private def parseRouter(line: String): Router =
    val routerEntry = line.split(";")
    val routerId    = routerEntry(0)
    val routerType  = routerEntry(1)
    Router(RouterId(routerId), RouterType.valueOf(routerType))
