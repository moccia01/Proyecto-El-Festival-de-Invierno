package participante

import participante.dragon.Dragon
import torneo.postas.Posta


abstract class Participante {
  def danio: Double
  def velocidad: Double
  def capacidadDeCarga: Double
  def participarEn(posta: Posta): Participante
  def puedeParticiparEn(posta: Posta): Boolean

  //Mensajes polimorficos para eviat repetir logica
  def vikingod(): Vikingo = {
    this match {
      case Jinete(vikingo, _) => vikingo
      case vikingo: Vikingo => vikingo
    }
  }
  def drake(): Dragon = {
    this match{
    case Jinete(_, drag) => drag
    case _ => null
  }}
}
