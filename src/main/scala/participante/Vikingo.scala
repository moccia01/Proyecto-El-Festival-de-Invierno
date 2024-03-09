package participante
import participante.dragon._
import participante.items._
import torneo.postas._

import scala.util.{ Try, Success, Failure}

class Vikingo (
                val peso: Double,
                val velocidadBase: Double,
                val barbarosidad: Double,
                val item: Item,
                var hambre: Double = 0
              ) extends Participante{

  override def danio: Double = {
    item match {
      case Arma(danio) => danio + barbarosidad
      case _ => barbarosidad
    }
  }

  override def velocidad: Double = velocidadBase

  override def capacidadDeCarga: Double = peso * 0.5 + barbarosidad * 2

  override def participarEn(posta: Posta): Vikingo = {
    participar(incrementoHambre(posta))
    this
  }

  override def puedeParticiparEn(posta: Posta): Boolean =
    puedeParticipar(incrementoHambre(posta))

  def montar(dragon: Dragon): Try[Jinete] = {
    if (dragon.puedeSerMontadoPor(this))
      Success[Jinete](Jinete(this, dragon))
    else
      Failure[Jinete](new RuntimeException(dragon + "no puede ser montado por " + this))
  }


  def participar(incHambre: Double): Unit = hambre += incHambre

  def puedeParticipar(incHambre: Double): Boolean = hambre + incHambre < 100

  def incrementoHambre(posta: Posta): Double = {
    posta match {
      case Pesca(_) => 5.0
      case Combate(_) => 10.0
      case Carrera(_, distancia) => distancia
    }
  }

}
