package participante

import types.types.Vikingos

case class Equipo(integrantes: Vikingos) {

  def obtenerEquipoOption(vikingos: Vikingos): Option[Equipo] = {
    if(cuantosVikingosQuedan(vikingos) == 0) None else Some(this)
  }
  def cuantosVikingosQuedan(vikingos: Vikingos):Int = {
    integrantes.count(i => vikingos.contains(i))
  }

  def sigueParticipando(vikingos: Vikingos): Boolean = integrantes.exists(vikingos.contains(_))
}
