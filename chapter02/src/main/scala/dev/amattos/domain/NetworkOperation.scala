package dev.amattos.domain

import dev.amattos.Task

object NetworkOperation:
  def createNewNetwork(router: Router, address: IP, name: String, cidr: Long): Task[Router] =
    for
      router  <- Router.ensureNetworkAvailability(router, address)
      network <- Router.createNetwork(address, name, cidr)
    yield router.addNetworkToSwitch(network)

  def removeNetwork(router: Router, address: IP): Task[Router] =
    for router <- Router.ensureNetworkRegistered(router, address)
    yield router.removeNetworkFromSwitch(address)
