package torneo.reglas

import participante.Vikingo
import types.types._

case class Eliminacion(cantidadParticipantesAELiminar: Int) extends Reglas[Vikingo]() {

  override def superanLaPosta(participantes: Vikingos): Vikingos = {
    participantes.take(participantes.length - cantidadParticipantesAELiminar)
  }

}
