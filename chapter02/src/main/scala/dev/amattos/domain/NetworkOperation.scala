package dev.amattos.domain

object NetworkOperation:
  def createNewNetwork(router: Router, address: IP, name: String, cidr: Long): Either[RuntimeException, Router] =
    for
      router  <- Router.ensureNetworkAvailability(router, address)
      network <- Router.createNetwork(address, name, cidr)
    yield router.addNetworkToSwitch(network)
