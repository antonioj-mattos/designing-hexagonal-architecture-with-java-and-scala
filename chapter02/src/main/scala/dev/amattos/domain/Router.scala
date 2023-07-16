package dev.amattos.domain

import java.util.UUID

import Router.RouterId
import Switch.SwitchId

enum RouterType:
  case EDGE, CORE

case class Router(routerId: RouterId, routerType: RouterType, switch: Switch):

  def addNetworkToSwitch(network: Network): Router =
    val updatedSwitch = switch.addNetwork(network)
    copy(switch = updatedSwitch)

  def networks: List[Network] = switch.networks

  override def toString: String =
    s"Router{id=$routerId, type=$routerType}"

object Router:

  opaque type RouterId = String
  object RouterId:

    /**
     * Reconstructs a `RouterId` from a string.
     */
    def apply(id: String): RouterId = id

    /**
     * Creates a new `RouterId` based on a `UUID`.
     */
    def newId(): RouterId = UUID.randomUUID().toString

  def filterRouterByType(routerType: RouterType): Router => Boolean =
    router => router.routerType == routerType

  def createNetwork(address: IP, name: String, cidr: Long): Either[RuntimeException, Network] =
    Network(address, name, cidr)

  /**
   * Ensures that the given `IP` address is available on the destination
   * `Router`.
   */
  def ensureNetworkAvailability(router: Router, address: IP): Either[RuntimeException, Router] =
    val addressInUse = router.networks.exists(_.address == address)
    if !addressInUse then Right(router)
    else Left(IllegalArgumentException("IP address is taken."))

object RouterSearch:

  def retrieveRouter(routers: List[Router], predicate: Router => Boolean): List[Router] =
    routers.filter(predicate)

enum SwitchType:
  case LAYER2, LAYER3

case class Switch(switchId: SwitchId, switchType: SwitchType, networks: List[Network], address: IP):
  def addNetwork(network: Network): Switch =
    copy(networks = network +: networks)

  override def toString: String =
    s"Switch{id=$switchId, type=$switchType, address=$address}"

object Switch:

  opaque type SwitchId = String
  object SwitchId:

    /**
     * Reconstructs a `SwitchId` from a string.
     */
    def apply(id: String): SwitchId = id

    /**
     * Creates a new `SwitchId` based on a `UUID`.
     */
    def newId(): SwitchId = UUID.randomUUID().toString

/**
 * A network with name, ip address and cidr.
 */
case class Network private (address: IP, name: String, cidr: Long):
  override def toString: String =
    s"Network{address=$address, name=$name, cidr=$cidr}"

object Network:
  def apply(address: IP, name: String, cidr: Long): Either[RuntimeException, Network] =
    if cidr < 1 || cidr > 32 then Left(IllegalArgumentException("Invalid CIDR value."))
    else Right(new Network(address, name, cidr))

  /**
   * It should only be used to reconstruct an already valid `Network`, for
   * example from a repository.
   */
  def unsafeApply(address: IP, name: String, cidr: Long): Network = new Network(address, name, cidr)

/**
 * IP protocol.
 */
enum Protocol:
  case IPV4, IPV6

/**
 * An `IP` address and its protocol.
 */
case class IP private (address: String, protocol: Protocol):
  override def toString: String =
    s"IP{address=$address, protocol=$protocol}"

object IP:
  def apply(address: String): Either[RuntimeException, IP] =
    if address.isEmpty() then Left(IllegalArgumentException("Invalid IP address value."))
    else
      val ip =
        if address.length <= 15 then IP(address, Protocol.IPV4)
        else IP(address, Protocol.IPV6)
      Right(ip)

  /**
   * It should only be used to reconstruct an already valid `IP`, for example
   * from a repository.
   */
  def unsafeApply(address: String, protocol: String): IP = new IP(address, Protocol.valueOf(protocol))