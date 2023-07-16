package dev.amattos

import dev.amattos.infrastructure.adapters.RouterViewCLIApp

@main
def App: Unit =
  val cli        = new RouterViewCLIApp()
  val routerType = "EDGE"
  println(cli.obtainRelatedRouters(routerType))
