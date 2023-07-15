package dev.amattos.domain

import Router.RouterId

enum RouterType:
  case EDGE, CORE

case class Router(id: RouterId, routerType: RouterType):
  override def toString(): String =
    s"Router{routerType=$routerType, routerId=$id}"

object Router:

  opaque type RouterId = String
  object RouterId:
    def apply(id: String): RouterId = id

  def filterRouterByType(routerType: RouterType): Router => Boolean =
    router => router.routerType == routerType

  def retrieveRouter(routers: List[Router], predicate: Router => Boolean): List[Router] =
    routers.filter(predicate)
