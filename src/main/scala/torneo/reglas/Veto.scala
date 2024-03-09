package torneo.reglas

import participante.{Jinete, Participante, Vikingo}
import participante.dragon.Dragon
import torneo.postas.Posta
import types.types._

case class Veto(condicionVeto: CondicionDragon) extends Reglas[Vikingo] {

  override def elegirDragones(vikingos: Vikingos, dragones: Dragones, posta: Posta): Participantes = {

//    filtrar dragones que cumplen la condicion del Veto
//    val dragonesDisponibles: Dragones = dragones.filter(condicionVeto)

//    llamar a super con dragones filtrados
    super.elegirDragones(vikingos, dragonesDisponibles(dragones), posta)

  }

  def dragonesDisponibles(dragones: Dragones): Dragones = dragones.filter(condicionVeto)
}
