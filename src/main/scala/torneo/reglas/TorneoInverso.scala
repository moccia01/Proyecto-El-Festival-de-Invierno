package torneo.reglas

import participante.Vikingo
import types.types._

import scala.util.Try

case class TorneoInverso() extends Reglas[Vikingo] {

  override def superanLaPosta(participantes: Vikingos): Vikingos = {
      participantes.takeRight(obtenerLaMitadDeLaLista(participantes)) //Es solo drop ya que elimina a la mitad que mejor le fue
  }

  override def elegirGanador(participantes: Vikingos): Option[Vikingo] = {
    participantes.reverse.headOption
  }
}
