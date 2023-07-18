package dev.amattos

import dev.amattos.application.RouterNetworkUseCase
import dev.amattos.application.RouterNetworkService

import dev.amattos.infrastructure.adapters.input.RouterNetworkAdapter
import dev.amattos.infrastructure.adapters.input.cli.RouterNetworkCLIAdapter
import dev.amattos.infrastructure.adapters.output.file.RouterNetworkInMemoryRepository

@main
def App: Unit =
  val cli: RouterNetworkAdapter = RouterNetworkCLIAdapter(
    RouterNetworkService(RouterNetworkInMemoryRepository)
  )
  println(cli.run)
