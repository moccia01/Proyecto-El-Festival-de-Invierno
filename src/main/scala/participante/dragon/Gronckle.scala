package participante.dragon

import participante.Vikingo

import scala.Console.println

case class Gronckle(peso: Double, capacidad: Double, requisitosMontura: RequisitosMontura = RequisitoMonturaVacio) extends Dragon(){
  override val velocidadBase: Double = 30.0

  def danio: Double = peso * 5

  override def capacidadDeCarga: Double = capacidad.max(0)
}
