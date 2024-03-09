package participante

abstract class Participacion {
  def participar(vikingo: Vikingo, _hambre: Double): Vikingo

  def puedeParticipar(vikingo: Vikingo, _hambre: Double): Boolean
}
