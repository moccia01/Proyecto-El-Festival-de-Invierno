package torneo

import participante.{Equipo, Vikingo}
import participante.vikingos._
import torneo.postas.Posta
import torneo.reglas._
import types.types._

import scala.util.Try

case class Torneo[T](
                   postasDelTorneo: Postas,
                   competidores: List[T],
                   dragones: Dragones,
                   regla: Reglas[T]
                 ){

  def obtenerGanador(): Option[T] = {
    val vikingods = regla.flat(competidores)
    elegirGanador(postasDelTorneo.foldLeft(vikingods)(jugar)) //Renombrara zaraza

  }
  def jugar(vikingos: Vikingos, posta: Posta): Vikingos = {
    if(regla.quedanSuficientesParticipantes(vikingos)){ hacerUnaPosta(posta, vikingos)
    }else {vikingos}
  }

//Hace participar a los vikingos de la posta
  def hacerUnaPosta(posta: Posta, vikingos: Vikingos): Vikingos = {
    val participantes: Participantes = elegirDragones(posta, vikingos)

    regla.superanLaPosta(posta.ganadores(participantes))
  }
  def elegirGanador(vikingos: Vikingos):Option[T] = {
    regla.elegirGanador(vikingos)
  }
  def elegirDragones(posta: Posta, vikingos: Vikingos): Participantes =  {
    regla.elegirDragones(vikingos, dragones, posta)
  }
//  TODO Comente porque no compila
//  def agregarEquipo(equipo: Equipo):Torneo[T] = {
//    this.copy(regla = regla.asInstanceOf[PorEquipos].agregarEquipo(equipo))
//  }
}