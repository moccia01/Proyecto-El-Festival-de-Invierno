package participante

import participante.dragon._
import torneo.postas._


case class Jinete(vikingo: Vikingo, var dragon: Dragon) extends Participante {
  def danio: Double = vikingo.danio + dragon.danio
  def velocidad: Double = (dragon.velocidad - vikingo.peso).max(0)
  def capacidadDeCarga: Double = dragon.capacidadDeCarga - vikingo.peso

  def puedeParticiparEn(posta: Posta): Boolean =
    vikingo.puedeParticipar(incrementoHambre)

  def participarEn(posta: Posta): Participante = {
    vikingo.participar(incrementoHambre)
    this
  }

  def incrementoHambre: Double = 5

}
