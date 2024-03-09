package torneo.reglas
import participante.{Equipo, Vikingo}
import torneo.postas.Posta
import torneo.reglas.Reglas
import types.types._

case class PorEquipos(equipos: List[Equipo]) extends Reglas[Equipo]{

  override def elegirGanador(vikingosFinalistas: Vikingos): Option[Equipo] = {
    equipoGanador(vikingosFinalistas).obtenerEquipoOption(vikingosFinalistas)
  }
  def equipoGanador(vikingosFinalistas: Vikingos): Equipo = {
    equipos.maxBy(_.cuantosVikingosQuedan(vikingosFinalistas))
  }
  override def quedanSuficientesParticipantes(vikingos: Vikingos) = {
    equipos.count(_.sigueParticipando(vikingos)) > 1
  }

  override def flat(competidores: List[Equipo]): Vikingos = {
    competidores.flatMap(_.integrantes)
  }

  def agregarEquipo(equipo: Equipo): PorEquipos = {
    this.copy(equipos = equipos :+ equipo)
  }

}