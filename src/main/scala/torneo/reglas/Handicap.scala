package torneo.reglas

import participante.{Participante, Vikingo}
import torneo.postas.Posta
import types.types.{Dragones, Participantes, Vikingos}

case class Handicap() extends Reglas[Vikingo]() {

  override def elegirDragones(vikingos: Vikingos, dragones: Dragones, posta: Posta): Participantes = {

    //    invertir orden de vikingos
    val vikingosOrdenInverso: Vikingos = vikingos.reverse

    //    llamar a super con vikingos en orden inverso
    super.elegirDragones(vikingosOrdenInverso, dragones, posta)
  }
}
